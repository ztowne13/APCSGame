package me.zm.apcsgame.level;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.displays.Background;
import me.zm.apcsgame.displays.overlays.Overlay;
import me.zm.apcsgame.displays.overlays.OverlayType;
import me.zm.apcsgame.displays.overlays.SnowOverlay;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.tiles.StaticTile;
import me.zm.apcsgame.entity.tiles.Tile;
import me.zm.apcsgame.locations.events.EventLocation;
import me.zm.apcsgame.locations.events.EventLocationType;
import me.zm.apcsgame.locations.events.NewLevelEventLocation;
import me.zm.apcsgame.sounds.Sound;
import me.zm.apcsgame.utils.FileUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
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
	double levelImageScale;

	ArrayList<EventLocation> eventLocations = new ArrayList<EventLocation>();
	Point[] points;
	String levelName;
	Point spawnPoint;

	Background background;
	Overlay overlay;

	AudioInputStream levelSongStream;
	Clip levelSong;

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

	public void loadAll()
	{
		loadSettings();
		loadImage();
		loadLevelBounds();
		loadDynamicTiles();
		loadEventLocations();
	}

	/**
	 * Loads the level's Buffered image.
	 */
	public void loadImage()
	{
		levelBaseWindow = FileUtils.loadImage("levels/" + levelId + ".png", levelImageScale);
	}

	/**
	 * Load the level's settings such as spawn point.
	 * File format:
	 * settings {
	 * level name
	 * song name
	 * overlay name
	 * level image scale
	 * background name,background offset
	 * x coord, y coord - Spawn coordinates
	 * }
	 */
	public void loadSettings()
	{
		int startingSettingsLine = findStartingConfigLine(GameSettings.start_settings);

		levelName = loadedSettings.get(startingSettingsLine);

		String[] unParsedSpawn = loadedSettings.get(startingSettingsLine + 1).replaceAll("\\s+", "").split(",");
		spawnPoint = new Point(Integer.parseInt(unParsedSpawn[0]), Integer.parseInt(unParsedSpawn[1]));

		levelSongStream = Sound.valueOf(loadedSettings.get(startingSettingsLine + 2).replaceAll("\\s+", "").toUpperCase()).getSoundClip();

		OverlayType overlayType = OverlayType.valueOf(loadedSettings.get(startingSettingsLine + 3).toUpperCase());
		if(overlayType == OverlayType.SNOW)
		{
			overlay = new SnowOverlay(game, 500);
		}

		levelImageScale = Double.parseDouble((loadedSettings.get(startingSettingsLine + 4) + "").replaceAll("\\s+", ""));

		String[] backgroundArgs = loadedSettings.get(startingSettingsLine + 5).replaceAll("\\s+", "").split(",");
		background = new Background(game, backgroundArgs[0], Integer.parseInt(backgroundArgs[1]), Double.parseDouble(backgroundArgs[2]));
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
		int startingBoundsLine = findStartingConfigLine(GameSettings.start_levelBounds);

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
		int startingBoundsLine = findStartingConfigLine(GameSettings.start_breakableTiles);

		for(int i = startingBoundsLine; i < loadedSettings.size(); i++)
		{
			String s = loadedSettings.get(i);

			// Checks to see if it has hit the end of the configuration section.
			if(s.replaceAll("\\s+","").equalsIgnoreCase("}"))
			{
				break;
			}

			String[] parsedTiles = s.replaceAll("\\s+","").split(",");
			StaticTile staticTile = new StaticTile(game, Integer.parseInt(parsedTiles[1]), Integer.parseInt(parsedTiles[2]), 0, 0, BlockType.valueOf(parsedTiles[0].toUpperCase()));
			game.getEntities().add(staticTile);
		}
	}

	/**
	 * Loads all of the event locations such as new level areas
	 */
	public void loadEventLocations()
	{
		int startingBoundsLine = findStartingConfigLine(GameSettings.start_eventLocations);

		for(int i = startingBoundsLine; i < loadedSettings.size(); i++)
		{
			String s = loadedSettings.get(i);

			if(s.replaceAll("\\s+","").equalsIgnoreCase("}"))
			{
				break;
			}

			String[] args = s.replaceAll("\\s+", "").split(",");

			EventLocationType eventLocationType = EventLocationType.valueOf(args[0].toUpperCase());

			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int radius = Integer.parseInt(args[3]);

			switch(eventLocationType)
			{
				case NEW_LEVEL:
					new NewLevelEventLocation(game, x, y, radius, args[4]);
					break;
			}
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
		background.render(graphics);
		graphics.drawImage(levelBaseWindow, -(int)game.getGameCamera().getxOffset(), -(int)game.getGameCamera().getyOffset(), null);
	}

	public void renderOverlay(Graphics graphics)
	{
		if(overlay != null)
		{
			// Also ticks the snowflakes to improve performance
			overlay.tick();
			overlay.render(graphics);
		}
	}

	/**
	 * Renders all of the tiles based off of the player_walk to determine whether to render it before or after the player_walk is rendered.
	 * @param player
	 * @param graphics
	 */
	public void renderTiles(Entity player, Graphics graphics, boolean before)
	{
		for(Entity tile : game.getEntities())
		{
			if(tile instanceof Tile)
			{
				if(!(player == null))
				{
					if ((player.renderBefore(tile) && before) || (!player.renderBefore(tile) && !before))
					{
						tile.draw(graphics);
					}
				}
				else
				{
					tile.draw(graphics);
				}
			}
		}
	}

	public void tick()
	{
		if(levelSong == null || !levelSong.isRunning())
		{
			try
			{
				/*
				* Just got annoyed listening to the music, will re-add later.
				*
				levelSongStream.reset();

				levelSong = AudioSystem.getClip();
				levelSong.open(levelSongStream);
				levelSong.start();*/
			}
			catch(Exception exc)
			{
				exc.printStackTrace();
			}
		}
	}

	public void endLevel()
	{
		levelSong.stop();
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
