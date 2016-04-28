package me.zm.apcsgame.level;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.breakables.BreakableTile;
import me.zm.apcsgame.entity.breakables.Tile;
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

	ArrayList<String> loadedSettings;

	public Level(Game game, String levelId, int width, int height)
	{
		this.game = game;
		this.levelId = levelId;
		this.width = width;
		this.height = height;

		loadedSettings = FileUtils.loadFileByLine("levels/" + levelId + "settings.txt");
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
	 * settings {
	 * level name
	 * x coord, y coord - Spawn coordinates
	 * }
	 */
	public void loadSettings()
	{
		int startingSettingsLine = findStartingConfigLine("settings {");

		levelName = loadedSettings.get(startingSettingsLine);

		String[] unParsedSpawn = loadedSettings.get(startingSettingsLine + 1).replaceAll("\\s+", "").split(",");
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
		int startingBoundsLine = findStartingConfigLine("level bounds {");

		ArrayList<Point> tempPoints = new ArrayList<Point>();

		for(int i = startingBoundsLine; i < loadedSettings.size(); i++)
		{
			String s = loadedSettings.get(i);

			// Checks to see if it has hit the end of the configuration section.
			if(s.replaceAll("\\s+","").equalsIgnoreCase("}"))
			{
				break;
			}

			String[] boundPoints = s.replaceAll("\\s+","").split(",");
			Point boundaryPoint = new Point(Integer.parseInt(boundPoints[0]), Integer.parseInt(boundPoints[1]));
			tempPoints.add(boundaryPoint);
		}

		points = tempPoints.toArray(new Point[0]);
	}

	/**
	 * Loads all non-background tiles
	 */
	public void loadDynamicTiles()
	{
		int startingBoundsLine = findStartingConfigLine("breakable tiles {");

		for(int i = startingBoundsLine; i < loadedSettings.size(); i++)
		{
			String s = loadedSettings.get(i);

			// Checks to see if it has hit the end of the configuration section.
			if(s.replaceAll("\\s+","").equalsIgnoreCase("}"))
			{
				break;
			}

			String[] parsedTiles = s.replaceAll("\\s+","").split(",");
			BreakableTile breakableTile = new BreakableTile(game, Integer.parseInt(parsedTiles[1]), Integer.parseInt(parsedTiles[2]), 0, 0, BlockType.valueOf(parsedTiles[0].toUpperCase()));
			game.getEntities().add(breakableTile);
		}
	}

	/**
	 * Finds the starting line for a certain section of the config
	 * @param line The line that identifies the start of a particular section
	 * @return The first line that contains config in this section
	 */
	public int findStartingConfigLine(String line)
	{
		line = line.replaceAll("\\s+","");
		for(int i = 0; i < loadedSettings.size(); i++)
		{
			if(loadedSettings.get(i).replaceAll("\\s+","").equalsIgnoreCase(line))
			{
				return i + 1;
			}
		}
		return loadedSettings.size();
	}

	public void render(Graphics graphics)
	{
		graphics.drawImage(levelBaseWindow, -(int)game.getGameCamera().getxOffset(), -(int)game.getGameCamera().getyOffset(), null);
	}

	/**
	 * Renders all of the tiles based off of the player to determine whether to render it before or after the player is rendered.
	 * @param player
	 * @param graphics
	 */
	public void renderTiles(Entity player, Graphics graphics, boolean before)
	{
		for(Entity tile : game.getEntities())
		{
			if(tile instanceof Tile)
			{
				if ((player.renderBefore(tile) && before) || (!player.renderBefore(tile) && !before))
				{
					tile.draw(graphics);
				}
			}
		}
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

		return !polygon.contains(ent.getLocation().getX() - game.getGameCamera().getxOffset(), ent.getLocation().getY() - game.getGameCamera().getyOffset());
	}

	public Point getSpawnPoint()
	{
		return spawnPoint;
	}

	public void setSpawnPoint(Point spawnPoint)
	{
		this.spawnPoint = spawnPoint;
	}

	public Point[] getPoints()
	{
		return points;
	}

	public void setPoints(Point[] points)
	{
		this.points = points;
	}
}
