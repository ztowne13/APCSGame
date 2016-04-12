package me.zm.apcsgame;

import me.zm.apcsgame.input.KeyInputListener;

/**
 * Created by ztowne13 on 4/8/16.
 *
 * Main game class
 */
public class Game implements Runnable
{
	private Thread thread;

	private KeyInputListener keyInputListener;

	private GameState gameState;

	public Game()
	{
		this.keyInputListener = new KeyInputListener();
		this.gameState = GameState.STARTUP;
	}

	/**
	 * Sets up all aspects of the game.
	 */
	private void initialize()
	{

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
}
