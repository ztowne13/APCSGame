package me.zm.apcsgame.level;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.utils.FileUtils;
import me.zm.apcsgame.utils.GraphicUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/12/16.
 */
public class Level
{
	Game game;

	String levelId;
	BufferedImage levelBaseWindow;

	Point[] points;

	String levelName;
	Point spawnPoint;

	int width, height;

	public Level(Game game, String levelId, int width, int height)
	{
		this.game = game;
		this.levelId = levelId;
		this.width = width;
		this.height = height;
	}

	/**
	 * Loads the level's Buffered image.
	 */
	public void load()
	{
		levelBaseWindow = GraphicUtils.loadImage("levels/" + levelId + ".png");
	}

	/**
	 * Load the level's settings such as spawn point.
	 * File format:
	 * level name
	 * spawn (x coord, y coord)
	 */
	public void loadSettings()
	{
		ArrayList<String> loadedSettings = FileUtils.loadFileByLine("levels/" + levelId + "settings.txt");

		levelName = loadedSettings.get(0);

		String[] unParsedSpawn = loadedSettings.get(1).replaceAll("\\s+","").split(",");
		spawnPoint = new Point(Integer.parseInt(unParsedSpawn[0]), Integer.parseInt(unParsedSpawn[1]));
	}

	/**
	 * Loads in all the points of the level edges
	 * File format:
	 * x coord, y coord
	 * x coord, y coord
	 * etc.
	 */
	public void loadLevelBounds()
	{
		ArrayList<String> loadedPoints = FileUtils.loadFileByLine("levels/" + levelId + "bounds.txt");

		points = new Point[loadedPoints.size()];

		for(int i = 0; i < points.length; i++)
		{
			String s = loadedPoints.get(i);
			String[] boundPoints = s.replaceAll("\\s+","").split(",");
			Point boundaryPoint = new Point(Integer.parseInt(boundPoints[0]), Integer.parseInt(boundPoints[1]));
			points[i] = boundaryPoint;
		}
	}

	/**
	 * Loads all non-background tiles
	 */
	public void loadDynamicTiles()
	{

	}

	public void render(Graphics graphics)
	{
		graphics.drawImage(levelBaseWindow, -(int)game.getGameCamera().getxOffset(), -(int)game.getGameCamera().getyOffset(), null);
	}

	public void tick()
	{

	}

	/**
	 * Detects if an entity is outside of the location bounds.
	 * @param ent The entity to check
	 * @return True if the point is outside, false if it is inside.
	 */
	public boolean isEntityOutsideBounds(Entity ent)
	{
		int[] xPoints = new int[points.length];
		int[] yPoints = new int[points.length];

		for(int i = 0; i < points.length; i++)
		{
			xPoints[i] = points[i].x - (int)(game.getGameCamera().getxOffset()) - (ent.getWidth()/2);
			yPoints[i] = points[i].y - (int)(game.getGameCamera().getyOffset()) - (ent.getHeight());
		}

		Polygon polygon = new Polygon(xPoints, yPoints, points.length);

		return !polygon.contains(ent.getX() - game.getGameCamera().getxOffset(), ent.getY() - game.getGameCamera().getyOffset());
	}

	public Point getSpawnPoint()
	{
		return spawnPoint;
	}

	public void setSpawnPoint(Point spawnPoint)
	{
		this.spawnPoint = spawnPoint;
	}
}
