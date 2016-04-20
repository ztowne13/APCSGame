package me.zm.apcsgame;

import me.zm.apcsgame.displays.MousePointer;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.input.KeyInputListener;
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

	private KeyInputListener keyInputListener;
	private ArrayList<Entity> entities = new ArrayList<>();

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

		this.keyInputListener = new KeyInputListener();
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

		Player p = new Player(this, "TestCharacter1", currentLevel.getSpawnPoint().x, currentLevel.getSpawnPoint().y, 50, 50, 2);

		getGameCamera().centerOnEntity(p);

		entities.add(p);

		this.display = new Display("test", getWidth(), getHeight());
		display.getFrame().addKeyListener(keyInputListener);

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

		// Renders the tiles that will be beneath the player
		getCurrentLevel().renderTiles(entities.get(0), g, true);

		for(Entity ent : entities)
		{
			ent.draw(g);
		}

		// Renders the tiles that will be above the player
		getCurrentLevel().renderTiles(entities.get(0), g, false);

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
				delta--;
			}

			if(timer >= 1000000000)
			{
				System.out.println("Ticks and frames: " + ticks);
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

	public GameState getGameState()
	{
		return gameState;
	}

	public void setGameState(GameState gameState)
	{
		this.gameState = gameState;
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
}
