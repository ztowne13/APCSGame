package me.zm.apcsgame;

import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.level.GameCamera;
import me.zm.apcsgame.level.Level;
import me.zm.apcsgame.saves.GameSave;

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

	private KeyInputListener keyInputListener;
	private ArrayList<Entity> entities = new ArrayList<>();

	private int width, height;

	private Level currentLevel;
	private GameState gameState = GameState.STARTUP;
	private GameSave gameSave;

	GameCamera gameCamera;

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

		this.gameCamera = new GameCamera(this, 0, 0);

		this.gameState = GameState.RUNNING;

		this.currentLevel = new Level(this, "level1", width, height);
		currentLevel.load();
		currentLevel.loadSettings();
		currentLevel.loadLevelBounds();

		Player p = new Player(this, "TestCharacter1", currentLevel.getSpawnPoint().x, currentLevel.getSpawnPoint().y, 50, 50, 1);

		//getGameCamera().centerOnEntity(p);

		entities.add(p);

		this.display = new Display("test", getWidth(), getHeight());
		display.getFrame().addKeyListener(keyInputListener);
	}

	/**
	 * Updates all game functions to the next tick.
	 */
	private void tick()
	{
		render();

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
		getCurrentLevel().render(display.getCanvas().getGraphics());

		for(Entity ent : entities)
		{
			ent.draw(display.getCanvas().getGraphics());
		}
	}

	/**
	 * Runs the game.
	 */
	@Override
	public void run()
	{
		initialize();

		int ticks = 0;

		double nanosPerTick = 1000000000 / GameSettings.FPS;
		long lastTime = System.nanoTime();

		while(gameState.equals(gameState.RUNNING))
		{
			if(lastTime + nanosPerTick < System.nanoTime())
			{
				ticks++;
				lastTime = System.nanoTime();
				tick();
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
}
