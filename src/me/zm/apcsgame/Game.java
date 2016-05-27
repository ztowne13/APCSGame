package me.zm.apcsgame;

import me.zm.apcsgame.displays.MousePointer;
import me.zm.apcsgame.displays.effects.GraphicEffect;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.input.MouseEventListener;
import me.zm.apcsgame.level.Level;
import me.zm.apcsgame.saves.GameSave;
import me.zm.apcsgame.tick.GameStateTick;
import me.zm.apcsgame.tick.LevelGSTick;
import me.zm.apcsgame.tick.StartupGSTick;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;

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

	// This is purely for test purposes to have the game draw hit boxes and other polygonal shapes that detect locations and such.
	private ArrayList<Polygon> toDisplayPolygons = new ArrayList<Polygon>();
	private HashMap<String,GraphicEffect> graphicEffects = new HashMap<String,GraphicEffect>();

	private int width, height, lastWidth, lastHeight;
	private double playSpeed = 100;

	private Level currentLevel;
	private GameState gameState = GameState.STOPPED;
	private GameStateTick gameStateTick;
	private GameSave gameSave;

	MousePointer mousePointer;

	public Game(int width, int height)
	{
		this.width = width; this.lastWidth = width;
		this.height = height; this.lastHeight = height;

		this.keyInputListener = new KeyInputListener(this);
		this.mouseEventListener = new MouseEventListener(this);
	}

	/**
	 * Sets up all aspects of the game.
	 */
	private void initialize()
	{
		this.display = new Display("test", getWidth(), getHeight());
		display.getFrame().addKeyListener(keyInputListener);
		display.getFrame().addMouseListener(mouseEventListener);
		display.getFrame().addMouseMotionListener(mouseEventListener);
		display.getCanvas().addMouseMotionListener(mouseEventListener);
		display.getCanvas().addMouseListener(mouseEventListener);

		this.mousePointer = new MousePointer(this);

		// Load into the first thing
		Level testLevel1 = new Level(this, "Forest-1", 0, 0, null);
		testLevel1.loadAll(true, true, true);


	}

	/**
	 * Updates all game functions to the next tick.
	 */
	private void tick()
	{
		// Manages width / height update.
		setWidth(getDisplay().getFrame().getWidth());
		setHeight(getDisplay().getFrame().getHeight());

		if((lastWidth != width || lastHeight != height) && getGameState().equals(GameState.IN_LEVEL))
		{
			if(getCurrentLevel().isHasFinishedLoading())
			{
				getCurrentLevel().getGameCamera().centerOnEntity(getCurrentLevel().getPlayer());
			}
		}

		lastWidth = getWidth();
		lastHeight = getHeight();

		mousePointer.tick();

		gameStateTick.tick();
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

		gameStateTick.draw(g);

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

		while(!gameState.equals(GameState.STOPPED))
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
		if(gameState.equals(GameState.STOPPED))
		{
			setGameState(GameState.STARTUP);
			this.thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Ends the thread.
	 */
	public synchronized void stop()
	{
		if(!gameState.equals(GameState.STOPPED))
		{
			setGameState(GameState.STOPPED);
			try
			{
				thread.join();
			}
			catch(Exception exc)
			{

			}
		}
	}

	public void setGameState(GameState gameState)
	{
		this.gameState = gameState;

		switch (gameState)
		{
			case IN_LEVEL:
				gameStateTick = new LevelGSTick(this);
				break;
			case STARTUP:
				gameStateTick = new StartupGSTick(this);
				break;
		}
	}

	public GameState getGameState()
	{
		return gameState;
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

	public MousePointer getMousePointer()
	{
		return mousePointer;
	}

	public void setMousePointer(MousePointer mousePointer)
	{
		this.mousePointer = mousePointer;
	}

	public MouseEventListener getMouseEventListener()
	{
		return mouseEventListener;
	}

	public void setMouseEventListener(MouseEventListener mouseEventListener)
	{
		this.mouseEventListener = mouseEventListener;
	}

	public HashMap<String, GraphicEffect> getGraphicEffects()
	{
		return graphicEffects;
	}

	public void setGraphicEffects(HashMap<String, GraphicEffect> graphicEffects)
	{
		this.graphicEffects = graphicEffects;
	}

	public double getPlaySpeed()
	{
		return playSpeed;
	}

	public void setPlaySpeed(double playSpeed)
	{
		this.playSpeed = playSpeed;
	}

	public GameStateTick getGameStateTick()
	{
		return gameStateTick;
	}

	public void setGameStateTick(GameStateTick gameStateTick)
	{
		this.gameStateTick = gameStateTick;
	}
}
