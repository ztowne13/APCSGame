package me.zm.apcsgame;

import me.zm.apcsgame.displays.MousePointer;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.breakables.Tile;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.input.MouseEventListener;
import me.zm.apcsgame.level.GameCamera;
import me.zm.apcsgame.level.Level;
import me.zm.apcsgame.saves.GameSave;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/8/16.
 *
 * Main game class
 */
public class Game implements Runnable
{
	private Thread thread;
	private Display display;
	private BufferStrategy bs;
	Graphics g;

	int ticksAlive = 0;

	private KeyInputListener keyInputListener;
	private MouseEventListener mouseEventListener;
	private ArrayList<Entity> entities = new ArrayList<>();
	Player player;

	// This is purely for test purposes to have the game draw hit boxes and other polygonal shapes that detect locations and such.
	private ArrayList<Polygon> toDisplayPolygons = new ArrayList<>();

	private int width, height;

	private Level currentLevel;
	private GameState gameState = GameState.STARTUP;
	private GameSave gameSave;

	GameCamera gameCamera;
	MousePointer mousePointer;

	public Game(int width, int height)
	{
		this.width = width;
		this.height = height;

		this.keyInputListener = new KeyInputListener(this);
		this.mouseEventListener = new MouseEventListener(this);
		this.gameState = GameState.STARTUP;
	}

	/**
	 * Sets up all aspects of the game.
	 */
	private void initialize()
	{
		//THIS IS ALL TEST CODE BELOW AND IS NOT NECCESSARILY GOING TO BE USED LATER

		this.gameState = GameState.RUNNING;

		this.currentLevel = new Level(this, "level1", width, height);
		currentLevel.load();
		currentLevel.loadSettings();
		currentLevel.loadLevelBounds();
		currentLevel.loadDynamicTiles();

		//this.gameCamera = new GameCamera(this, currentLevel.getSpawnPoint().x, currentLevel.getSpawnPoint().y);
		this.gameCamera = new GameCamera(this, 0, 0);

		this.player = new Player(this, "TestCharacter1", currentLevel.getSpawnPoint().x, currentLevel.getSpawnPoint().y, 50, 50, 2);
		entities.add(0, player);
		getGameCamera().centerOnEntity(player);

		this.display = new Display("test", getWidth(), getHeight());
		display.getFrame().addKeyListener(keyInputListener);
		display.getFrame().addMouseListener(mouseEventListener);
		display.getCanvas().addMouseListener(mouseEventListener);

		this.mousePointer = new MousePointer(this);

	}

	/**
	 * Updates all game functions to the next tick.
	 */
	private void tick()
	{
		mousePointer.tick();

		for(Entity ent : entities)
		{
			ent.tick();
		}

		getCurrentLevel().tick();
	}

	/**
	 * Re-renders all the graphics.
	 */
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();

		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);

		// Where to write the render code

		getCurrentLevel().render(g);

		// Renders the tiles that will be beneath the player_walk
		getCurrentLevel().renderTiles(player, g, true);

		// Renders all non tiles and the player_walk
		player.draw(g);
		for(Entity ent : entities)
		{
			if(!(ent instanceof Tile))
			{
				ent.draw(g);
			}
		}

		// Renders the tiles that will be above the player_walk (if the player_walk exists, otherwise they're already rendered)
		if(!(player == null))
		{
			getCurrentLevel().renderTiles(player, g, false);
		}

		// Test code that will not actually be included in final production
		g.setColor(Color.BLACK);
		for(Polygon polygon : toDisplayPolygons)
		{
			g.fillPolygon(polygon);
		}

		// End writing render code

		bs.show();
		g.dispose();
	}

	/**
	 * Runs the game using some baseline delta timing things I found online.
	 */
	@Override
	public void run()
	{
		initialize();

		double timePerTick = 1000000000 / GameSettings.FPS;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while(gameState.equals(GameState.RUNNING))
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1)
			{
				tick();
				render();
				ticks++;
				ticksAlive++;
				delta--;
			}

			if(timer >= 1000000000)
			{
				//System.out.println("Ticks and frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
	}

	/**
	 * Initializes the thread.
	 */
	public synchronized void start()
	{
		if(!gameState.equals(GameState.RUNNING))
		{
			gameState = GameState.RUNNING;
			this.thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Ends the thread.
	 */
	public synchronized void stop()
	{
		if(gameState.equals(GameState.RUNNING))
		{
			gameState = GameState.STOPPED;
			try
			{
				thread.join();
			}
			catch(Exception exc)
			{

			}
		}
	}

	public KeyInputListener getKeyInputListener()
	{
		return keyInputListener;
	}

	public void setKeyInputListener(KeyInputListener keyInputListener)
	{
		this.keyInputListener = keyInputListener;
	}

	public Level getCurrentLevel()
	{
		return currentLevel;
	}

	public void setCurrentLevel(Level currentLevel)
	{
		this.currentLevel = currentLevel;
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public GameCamera getGameCamera()
	{
		return gameCamera;
	}

	public void setGameCamera(GameCamera gameCamera)
	{
		this.gameCamera = gameCamera;
	}

	public Display getDisplay()
	{
		return display;
	}

	public void setDisplay(Display display)
	{
		this.display = display;
	}

	public ArrayList<Polygon> getToDisplayPolygons()
	{
		return toDisplayPolygons;
	}

	public void setToDisplayPolygons(ArrayList<Polygon> toDisplayPolygons)
	{
		this.toDisplayPolygons = toDisplayPolygons;
	}

	public int getTicksAlive()
	{
		return ticksAlive;
	}

	public void setTicksAlive(int ticksAlive)
	{
		this.ticksAlive = ticksAlive;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public MousePointer getMousePointer()
	{
		return mousePointer;
	}

	public void setMousePointer(MousePointer mousePointer)
	{
		this.mousePointer = mousePointer;
	}
}
