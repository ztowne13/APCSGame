package me.zm.apcsgame;

import me.zm.apcsgame.entity.Entity;
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
	private GameState gameState;
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
		//THIS IS ALL TEST CODE BELOW

		this.gameCamera = new GameCamera(this, 0, 0);

		this.currentLevel = new Level(this, "level1");
		currentLevel.load();
		currentLevel.loadSettings();
		currentLevel.loadLevelBounds();

		this.display = new Display("test", getWidth(), getHeight());
	}

	/**
	 * Updates all game functions to the next tick.
	 */
	private void tick()
	{
		render();
	}

	/**
	 * Re-renders all the graphics.
	 */
	private void render()
	{
		getCurrentLevel().render(display.getFrame().getGraphics());
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
				tick();
			}
		}
	}

	/**
	 * Initializes the thread.
	 */
	private synchronized void start()
	{
		if(!gameState.equals(GameState.RUNNING))
		{
			gameState = GameState.RUNNING;
			this.thread = new Thread();
			start();
		}
	}

	/**
	 * Ends the thread.
	 */
	private synchronized void stop()
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
