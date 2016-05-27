package me.zm.apcsgame.displays.animations;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.locations.Location;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by ztowne13 on 5/1/16.
 */
public abstract class Animation
{
	HashMap<String,Image> images = new HashMap<String,Image>();

	Game game;

	Location location;

	int framesCount;
	AnimationType animationType;
	String defaultPath;

	int currentAnimationStage = 0;

	/**
	 *
	 * @param game Game instance
	 * @param animationType The animation type of this specific animation
	 * @param location The location where the animation will display
	 * @param defaultPath The filepath of the files based off of the /res/ folder
	 */
	public Animation(Game game, AnimationType animationType, Location location, String defaultPath)
	{
		this.game = game;
		this.animationType = animationType;
		this.location = location;
		this.defaultPath = "animations/" + defaultPath + animationType.name().toLowerCase() + "/";

		this.framesCount = animationType.framesCount;
	}

	/**
	 * Loads all of the required images in the map
	 */
	public abstract void loadImages();

	public abstract Animation clone();

	/**
	 * Is a tick method if specific animation classes need extra code run when the plugin is ticked.
	 */
	public abstract void individualTick();

	public boolean tick()
	{
		boolean done = false;

		currentAnimationStage++;
		if(currentAnimationStage >= animationType.framesCount)
		{
			currentAnimationStage = 0;
			done = true;
		}

		individualTick();

		return done;
	}

	public HashMap<String, Image> getImages()
	{
		return images;
	}

	public void setImages(HashMap<String, Image> images)
	{
		this.images = images;
	}

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	public AnimationType getAnimationType()
	{
		return animationType;
	}

	public void setAnimationType(AnimationType animationType)
	{
		this.animationType = animationType;
	}

	public int getFramesCount()
	{
		return framesCount;
	}

	public void setFramesCount(int framesCount)
	{
		this.framesCount = framesCount;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public int getCurrentAnimationStage()
	{
		return currentAnimationStage;
	}

	public void setCurrentAnimationStage(int currentAnimationStage)
	{
		this.currentAnimationStage = currentAnimationStage;
	}

	public String getDefaultPath()
	{
		return defaultPath;
	}

	public void setDefaultPath(String defaultPath)
	{
		this.defaultPath = defaultPath;
	}
}
