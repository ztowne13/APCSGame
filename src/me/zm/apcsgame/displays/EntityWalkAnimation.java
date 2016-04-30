package me.zm.apcsgame.displays;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.utils.EntityUtils;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by ztowne13 on 4/19/16.
 */
public class EntityWalkAnimation
{
	Game game;
	Creature creature;

	// String is formated with the direction and the animation stage number tacked onto it. Ex: NORTH2
	HashMap<String,Image> images = new HashMap<>();

	int currentAnimationStage = 0;

	public EntityWalkAnimation(Game game, Creature creature)
	{
		this.game = game;
		this.creature = creature;
	}

	/**
	 * Loads an image for each
	 */
	public void loadImages()
	{
		for(String dir : new String[]{"NORTH", "EAST", "SOUTH", "WEST"})
		{
			for(int i = 0; i < GameSettings.totalAnimationFrames; i++)
			{
				// Formatted CreatureType + Direction + Anim. stage number. EX: NORTH1 or EAST3
				images.put(dir + i, FileUtils.loadImage((creature instanceof Player ? "characters" : "creatures") + "/" + creature.getCreatureType().name().toLowerCase() + "/" + dir + (i+1) + ".png"));
			}
		}
	}

	public void tick()
	{
		currentAnimationStage++;
		if(currentAnimationStage >= GameSettings.totalAnimationFrames)
		{
			currentAnimationStage = 0;
		}
	}

	public void render(boolean moving, Graphics g)
	{
		if (moving)
		{
			g.drawImage(images.get(EntityUtils.getDirectionFromKeypress(game.getKeyInputListener().getLastKeyPressed()).toString() + currentAnimationStage), creature.getLocation().getX() - (int) game.getGameCamera().getxOffset(), creature.getLocation().getY() - (int) game.getGameCamera().getyOffset(), null);
		}
		else
		{
			g.drawImage(images.get(EntityUtils.getDirectionFromKeypress(game.getKeyInputListener().getLastKeyPressed()).toString() + "0"), creature.getLocation().getX() - (int)game.getGameCamera().getxOffset(), creature.getLocation().getY() - (int)game.getGameCamera().getyOffset(), null);
		}
	}

	public int getCurrentAnimationStage()
	{
		return currentAnimationStage;
	}

	public void setCurrentAnimationStage(int currentAnimationStage)
	{
		this.currentAnimationStage = currentAnimationStage;
	}

	public HashMap<String, Image> getImages()
	{
		return images;
	}

	public void setImages(HashMap<String, Image> images)
	{
		this.images = images;
	}
}
