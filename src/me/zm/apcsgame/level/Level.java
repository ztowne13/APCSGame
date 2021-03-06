package me.zm.apcsgame.level;

import com.badlogic.gdx.math.Vector2;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.GameState;
import me.zm.apcsgame.displays.Background;
import me.zm.apcsgame.displays.effects.FadeEffect;
import me.zm.apcsgame.displays.menus.HUD;
import me.zm.apcsgame.displays.menus.PauseMenu;
import me.zm.apcsgame.displays.overlays.GoldOverlay;
import me.zm.apcsgame.displays.overlays.Overlay;
import me.zm.apcsgame.displays.overlays.OverlayType;
import me.zm.apcsgame.displays.overlays.SnowOverlay;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.CreatureType;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.entity.tiles.StaticTile;
import me.zm.apcsgame.entity.tiles.Tile;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.locations.events.EventLocation;
import me.zm.apcsgame.locations.events.EventLocationType;
import me.zm.apcsgame.locations.events.NewLevelEventLocation;
import me.zm.apcsgame.sounds.Sound;
import me.zm.apcsgame.utils.FileUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
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
	AudioInputStream walkSound;

	GameCamera gameCamera;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	ArrayList<EventLocation> eventLocations = new ArrayList<EventLocation>();

	Point[] points;
	String levelName;
	Point spawnPoint;
	boolean endedLevel = false;
	boolean startedSound = false;

	HUD hud;
	Player player;
	Background background;
	Overlay overlay;
	OverlayType overlayType;
	PauseMenu pauseMenu;

	AudioInputStream levelSongStream;
	Clip levelSong;

	int width, height;

	ArrayList<String> loadedSettings;
	boolean hasFinishedLoading = false;
	boolean finished = false;

	public Level(Game game, String levelId, int width, int height, Player player)
	{
		this.game = game;
		this.levelId = levelId;
		this.width = width;
		this.height = height;
		this.player = player;

		loadedSettings = FileUtils.loadFileByLine("levels/" + levelId + "-settings.txt");
	}

	public void tick()
	{
		if(hasFinishedLoading)
		{
			if ((levelSong == null || !levelSong.isRunning()) && !endedLevel && !startedSound)
			{
				try
				{
					/*
					* Just got annoyed listening to the music, will re-add later.
					*/
					//levelSongStream.reset();
					startedSound = true;
					levelSong = AudioSystem.getClip();
					levelSong.open(levelSongStream);
					levelSong.start();
				}
				catch (Exception exc)
				{
					exc.printStackTrace();
				}
			}

			getGameCamera().tick();

			// Items that are supposed to tick at the game's percentage speed

			if(game.getTicksAlive() % (101 - game.getPlaySpeed()) == 0)
			{
				getPlayer().tick();

				for (Entity ent : (ArrayList<Entity>) entities.clone())
				{
					ent.tick();
				}
			}
		}
	}

	public void loadAll(boolean fadeOut, boolean setAsCurrent, boolean modifyToInLevel)
	{
		loadAll(fadeOut, setAsCurrent, modifyToInLevel, -1, -1);
	}

	public void loadAll(boolean fadeOut, boolean setAsCurrent, boolean modifyToInLevel, int forceXSpawn, int forceYSpawn)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				long start = System.currentTimeMillis();
				long last = start;

				loadSettings();
				System.out.println("(" + (System.currentTimeMillis() - start) + ") Loaded level '" + levelName + "' settings in " + (System.currentTimeMillis() - last) + " ms");
				last = System.currentTimeMillis();

				loadImage();
				System.out.println("(" + (System.currentTimeMillis() - start) + ") Loaded level '" + levelName + "' image in " + (System.currentTimeMillis() - last) + " ms");
				last = System.currentTimeMillis();

				loadLevelBounds();
				System.out.println("(" + (System.currentTimeMillis() - start) + ") Loaded level '" + levelName + "' level bounds in " + (System.currentTimeMillis() - last) + " ms");
				last = System.currentTimeMillis();

				loadDynamicTiles();
				System.out.println("(" + (System.currentTimeMillis() - start) + ") Loaded level '" + levelName + "' dynamic tiles in " + (System.currentTimeMillis() - last) + " ms");
				last = System.currentTimeMillis();

				loadEventLocations();
				System.out.println("(" + (System.currentTimeMillis() - start) + ") Loaded level '" + levelName + "' event locations in " + (System.currentTimeMillis() - last) + " ms");
				last = System.currentTimeMillis();

				loadOther(forceXSpawn, forceYSpawn);
				System.out.println("(" + (System.currentTimeMillis() - start) + ") Loaded level '" + levelName + "' other in " + (System.currentTimeMillis() - last) + " ms");
				last = System.currentTimeMillis();

				hasFinishedLoading = true;
				System.out.println("Milliseconds to load level '" + levelName + "': " + (last - start));

				loadAfter(fadeOut, setAsCurrent, modifyToInLevel);

				try
				{
					interrupt();
				}
				catch(Exception exc)
				{

				}
			}
		};

		thread.start();
	}

	public void loadMonsterSpawns()
	{
		int startingBoundsLine = findStartingConfigLine(GameSettings.start_monsters);

		ArrayList<Point> tempPoints = new ArrayList<Point>();

		for(int i = startingBoundsLine; i < loadedSettings.size(); i++)
		{
			String s = loadedSettings.get(i).replaceAll("\\s+","");

			// Checks to see if it has hit the end of the configuration section.
			if(s.equalsIgnoreCase("}"))
			{
				break;
			}

			// format creature type, x, y
			String[] split = s.split(",");
			CreatureType creatureType = CreatureType.valueOf(split[0].toUpperCase());
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);

			creatureType.spawn(game, x, y);
		}
	}

	public void loadAfter(boolean fadeOut, boolean setAsCurrent, boolean modifyToInLevel)
	{
		game.getGraphicEffects().remove("fade in level");
		game.getGraphicEffects().remove("2");

		if(fadeOut)
		{
			game.getGraphicEffects().put("fade out level", new FadeEffect(game, Color.BLACK, 1, true, true));
		}

		if(setAsCurrent)
		{
			game.setCurrentLevel(this);
		}

		if(modifyToInLevel)
		{
			game.setGameState(GameState.IN_LEVEL);
		}

		if(overlayType == OverlayType.SNOW)
		{
			SnowOverlay flakes = new SnowOverlay(game, 500);
			flakes.createAllFlakes();
			overlay = flakes;
		}
		else if(overlayType == OverlayType.GOLD)
		{
			GoldOverlay goldOverlay = new GoldOverlay(game, 500);
			//goldOverlay.createAllSparks();
			overlay = goldOverlay;
		}

		loadMonsterSpawns();
	}

	/**
	 * Loads the player as well as the game camera
	 */
	public void loadOther(int forceX, int forceY)
	{
		if(getLevelId().equalsIgnoreCase("forest-1"))
		{
			walkSound = Sound.WALK_GRASS.getSoundClip();
		}
		else
		{
			walkSound = Sound.WALK_HARD_FLOOR.getSoundClip();
		}

		this.gameCamera = new GameCamera(game, 0, 0, 1);
		hud = new HUD(game);

		pauseMenu = new PauseMenu(game);
		pauseMenu.loadImage();

		if(getPlayer() == null)
		{
			setPlayer(new Player(game, "TestCharacter1", forceX != -1 ? forceX : getSpawnPoint().x, forceY != -1 ? forceY : getSpawnPoint().y, 50, 50, 3));
		}
		else
		{
			player.getLocation().setX(forceX != -1 ? forceX : getSpawnPoint().x);
			player.getLocation().setY(forceY != -1 ? forceY : getSpawnPoint().y);

			getPlayer().setLastCheckPoint(new Location(game, player.getLocation().getX(), player.getLocation().getY()));
			getPlayer().setCheckpointHealth(getPlayer().getHealth());
		}
		entities.add(0, getPlayer());

		getGameCamera().centerOnEntity(getPlayer());

		game.setPlaySpeed(100);
	}

	/**
	 * Loads the level's Buffered image.
	 */
	public void loadImage()
	{
		levelBaseWindow = FileUtils.loadImage("levels/" + levelId + ".png", levelImageScale);
		this.width = levelBaseWindow.getWidth();
		this.height = levelBaseWindow.getHeight();
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

		String[] unParsedSpawn = loadedSettings.get(startingSettingsLine + 1).split(",");
		spawnPoint = new Point(Integer.parseInt(unParsedSpawn[0].replaceAll("\\s+", "")), Integer.parseInt(unParsedSpawn[1].replaceAll("\\s+", "")));

		levelSongStream = Sound.valueOf(loadedSettings.get(startingSettingsLine + 2).replaceAll("\\s+", "").toUpperCase()).getSoundClip();

		this.overlayType = OverlayType.valueOf(loadedSettings.get(startingSettingsLine + 3).replaceAll("\\s+", "").toUpperCase());

		levelImageScale = Double.parseDouble((loadedSettings.get(startingSettingsLine + 4) + "").replaceAll("\\s+", ""));

		String[] backgroundArgs = loadedSettings.get(startingSettingsLine + 5).replaceAll("\\s+", "").split(",");
		background = new Background(game, backgroundArgs[0].replaceAll("\\s+", ""), Integer.parseInt(backgroundArgs[1]), Double.parseDouble(backgroundArgs[2]));
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
			StaticTile staticTile = new StaticTile(game, Integer.parseInt(parsedTiles[1]), Integer.parseInt(parsedTiles[2]), 0, 0, BlockType.valueOf(parsedTiles[0].toUpperCase()), Boolean.valueOf(parsedTiles[3]));
			getEntities().add(staticTile);
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

			//event name, x loc of event, y loc of event, event radius, new level name, force x spawn (-1 to not force), force y spawn (..)

			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int radius = Integer.parseInt(args[3]);
			int forceX = Integer.parseInt(args[5]);
			int forceY = Integer.parseInt(args[6]);

			switch(eventLocationType)
			{
				case NEW_LEVEL:
					eventLocations.add(new NewLevelEventLocation(game, x, y, radius, forceX, forceY, args[4]));
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
		if(hasFinishedLoading)
		{
			background.render(graphics);
			graphics.drawImage(levelBaseWindow, -(int) getGameCamera().getxOffset(), -(int) getGameCamera().getyOffset(), null);

			// Renders the tiles that will be beneath the player_walk
			renderTiles(getPlayer(), graphics, true);

			getPlayer().draw(graphics);

			// Renders the tiles that will be above the player_walk (if the player_walk exists, otherwise they're already rendered)
			renderTiles(getPlayer(), graphics, false);

			for (Entity ent : (ArrayList<Entity>) entities.clone())
			{
				if (!(ent instanceof Tile) && !(ent instanceof Player))
				{
					ent.draw(graphics);

					if(GameSettings.drawHitboxes)
					{
						Vector2 entVec = ent.getLocation().getVectorAsMiddleWithOffset(ent.getWidth(), ent.getHeight());
						graphics.fillRect((int) entVec.x - 5, (int) entVec.y - 5, 10, 10);
					}
				}
			}

			// All level design code
			if (GameSettings.levelBuildMode)
			{
				if (game.getMouseEventListener().getPoints().size() > 1)
				{
					int[] xPoints = new int[game.getMouseEventListener().getPoints().size() + 1];
					int[] yPoints = new int[game.getMouseEventListener().getPoints().size() + 1];

					for (int i = 0; i < xPoints.length - 1; i++)
					{
						xPoints[i] = game.getMouseEventListener().getPoints().get(i).x - (int) getGameCamera().getxOffset();
					}
					for (int i = 0; i < yPoints.length - 1; i++)
					{
						yPoints[i] = game.getMouseEventListener().getPoints().get(i).y - (int) getGameCamera().getyOffset();
					}

					xPoints[xPoints.length - 1] = game.getMouseEventListener().getX();
					yPoints[yPoints.length - 1] = game.getMouseEventListener().getY();

					Polygon p = new Polygon(xPoints, yPoints, game.getMouseEventListener().getPoints().size() + 1);
					graphics.drawPolygon(p);
				}
			}

			hud.drawHealth(graphics);
			hud.drawBossHealth(graphics);

			// This is to go to a new level (typically). Code may be moved at some point
			for (EventLocation eventLocation : eventLocations)
			{
				eventLocation.executeFor(graphics, getPlayer());
			}

			pauseMenu.draw(graphics);

			if(GameSettings.drawHitboxes)
			{
				int[] xPoints = new int[points.length];
				int[] yPoints = new int[points.length];

				for(int i = 0; i < points.length; i++)
				{
					xPoints[i] = points[i].x - (int)(getGameCamera().getxOffset()) - (getPlayer().getWidth()/2);
					yPoints[i] = points[i].y - (int)(getGameCamera().getyOffset()) - (getPlayer().getHeight());
				}

				Polygon polygon = new Polygon(xPoints, yPoints, points.length);
				graphics.drawPolygon(polygon);

				for (Entity ent : getEntities())
				{
					graphics.setColor(Color.BLACK);
					graphics.drawRect((int) ent.getHitbox().getX(), (int) ent.getHitbox().getY(), (int) ent.getHitbox().getWidth(), (int) ent.getHitbox().getHeight());
				}
			}
		}
	}

	public void renderOverlay(Graphics graphics)
	{
		if (hasFinishedLoading)
		{
			if (!(overlay == null))
			{
				// Also ticks the snowflakes to improve performance
				overlay.tick();
				overlay.render(graphics);
			}
		}
	}

	/**
	 * Renders all of the tiles based off of the player_walk to determine whether to render it before or after the player_walk is rendered.
	 * @param player
	 * @param graphics
	 */
	public void renderTiles(Entity player, Graphics graphics, boolean before)
	{
		for(Entity tile : (ArrayList<Entity>) getEntities().clone())
		{
			if(tile instanceof Tile)
			{
				if ((player.renderBefore(tile) == before))
				{
					tile.draw(graphics);
				}
			}
		}
	}

	public void endLevel()
	{
		endedLevel = true;
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
			xPoints[i] = points[i].x - (int)(getGameCamera().getxOffset()) - (ent.getWidth()/2);
			yPoints[i] = points[i].y - (int)(getGameCamera().getyOffset()) - (ent.getHeight());
		}

		Polygon polygon = new Polygon(xPoints, yPoints, points.length);

		return !polygon.contains(ent.getLocation().getX() - getGameCamera().getxOffset(), ent.getLocation().getY() - getGameCamera().getyOffset());
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

	public ArrayList<EventLocation> getEventLocations()
	{
		return eventLocations;
	}

	public void setEventLocations(ArrayList<EventLocation> eventLocations)
	{
		this.eventLocations = eventLocations;
	}

	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities)
	{
		this.entities = entities;
	}

	public Player getPlayer()
	{
		return player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public GameCamera getGameCamera()
	{
		return gameCamera;
	}

	public void setGameCamera(GameCamera gameCamera)
	{
		this.gameCamera = gameCamera;
	}

	public boolean isFinished()
	{
		return finished;
	}

	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}

	public boolean isHasFinishedLoading()
	{
		return hasFinishedLoading;
	}

	public void setHasFinishedLoading(boolean hasFinishedLoading)
	{
		this.hasFinishedLoading = hasFinishedLoading;
	}

	public PauseMenu getPauseMenu()
	{
		return pauseMenu;
	}

	public void setPauseMenu(PauseMenu pauseMenu)
	{
		this.pauseMenu = pauseMenu;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public String getLevelId()
	{
		return levelId;
	}

	public void setLevelId(String levelId)
	{
		this.levelId = levelId;
	}

	public AudioInputStream getWalkSound()
	{
		return walkSound;
	}

	public void setWalkSound(AudioInputStream walkSound)
	{
		this.walkSound = walkSound;
	}
}
