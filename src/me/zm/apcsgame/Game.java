package me.zm.apcsgame;

import me.zm.apcsgame.displays.MousePointer;
import me.zm.apcsgame.displays.effects.GraphicEffect;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.input.MouseEventListener;
import me.zm.apcsgame.level.Level;
import me.zm.apcsgame.saves.GameSave;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

	private int width, height;
	private double playSpeed = 100;

	private Level currentLevel;
	private HashMap<String,Level> loadedLevels = new HashMap<String,Level>();
	private GameState gameState = GameState.STARTUP;
	private GameSave gameSave;

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
		this.gameState = GameState.RUNNING;

		loadAllLevels();

		this.currentLevel = getLoadedLevels().get("RealLevel1-Unfinished");
		currentLevel.loadAll(true);

		this.display = new Display("test", getWidth(), getHeight());
		display.getFrame().addKeyListener(keyInputListener);
		display.getFrame().addMouseListener(mouseEventListener);
		display.getFrame().addMouseMotionListener(mouseEventListener);
		display.getCanvas().addMouseMotionListener(mouseEventListener);
		display.getCanvas().addMouseListener(mouseEventListener);

		this.mousePointer = new MousePointer(this);

	}

	private void loadAllLevels()
	{
		for(String s : new String[]{"RealLevel1-Unfinished"})
		{
			loadedLevels.put(s, new Level(this, s, 0, 0));
		}
	}

	/**
	 * Updates all game functions to the next tick.
	 */
	private void tick()
	{
		mousePointer.tick();

		Set<String> clonedList = new HashSet<String>();
		clonedList.addAll(getGraphicEffects().keySet());
		for(String graphicEffect : clonedList)
		{
			GraphicEffect effect = getGraphicEffects().get(graphicEffect);
			if(!effect.tick())
			{
				graphicEffects.remove(graphicEffect);
			}
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
		getCurrentLevel().renderOverlay(g);

		for(GraphicEffect graphicEffect : graphicEffects.values())
		{
			graphicEffect.draw(g);
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

	public HashMap<String, Level> getLoadedLevels()
	{
		return loadedLevels;
	}

	public void setLoadedLevels(HashMap<String, Level> loadedLevels)
	{
		this.loadedLevels = loadedLevels;
	}

	public double getPlaySpeed()
	{
		return playSpeed;
	}

	public void setPlaySpeed(double playSpeed)
	{
		this.playSpeed = playSpeed;
	}


}
