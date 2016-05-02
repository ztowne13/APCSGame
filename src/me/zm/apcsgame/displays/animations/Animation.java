package me.zm.apcsgame.displays.animations;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.entity.Entity;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by ztowne13 on 5/1/16.
 */
public abstract class Animation
{
	HashMap<String,Image> images = new HashMap<>();

	Game game;
	Entity entity;
	/**
	 * How many animation stages there are for this specific animation so it knows when to reloop.
	 */
	int animationStagesCount;

	int currentAnimationStage = 0;

	public Animation(Game game, Entity entity, int animationStagesCount)
	{
		this.game = game;
		this.entity = entity;
		this.animationStagesCount = animationStagesCount;
	}

	/**
	 * Loads all of the required images in the map
	 */
	public abstract void loadImages();

	public abstract void individualTick();

	public void tick()
	{
		currentAnimationStage++;
		if(currentAnimationStage >= GameSettings.totalAnimationFrames)
		{
			currentAnimationStage = 0;
		}

		individualTick();
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

	public Entity getEntity()
	{
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}

	public int getAnimationStagesCount()
	{
		return animationStagesCount;
	}

	public void setAnimationStagesCount(int animationStagesCount)
	{
		this.animationStagesCount = animationStagesCount;
	}

	public int getCurrentAnimationStage()
	{
		return currentAnimationStage;
	}

	public void setCurrentAnimationStage(int currentAnimationStage)
	{
		this.currentAnimationStage = currentAnimationStage;
	}
}
