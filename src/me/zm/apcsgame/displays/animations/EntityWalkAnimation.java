package me.zm.apcsgame.displays.animations;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.utils.EntityUtils;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by ztowne13 on 4/19/16.
 */
public class EntityWalkAnimation extends Animation
{
	public EntityWalkAnimation(Game game, Entity entity, int animationStagesCount)
	{
		super(game, entity, animationStagesCount);
	}

	@Override
	public void loadImages()
	{
		for(String dir : new String[]{"NORTH", "EAST", "SOUTH", "WEST"})
		{
			for(int i = 0; i < animationStagesCount; i++)
			{
				// Formatted CreatureType + Direction + Anim. stage number. EX: NORTH1 or EAST3
				images.put(dir + i, FileUtils.loadImage((entity instanceof Player ? "characters" : "creatures") + "/" + entity.getName().toLowerCase() + "/" + dir + "_" + (i+1) + ".png"));
			}
		}
	}

	@Override
	public void individualTick()
	{

	}

	public void render(boolean moving, Graphics g)
	{
		if (moving)
		{
			String directionName = Direction.combineCardinalDirections(EntityUtils.keysPressesToDirections(game.getKeyInputListener().getKeysPressed())).getDegrees() % 90 == 0 ?
					Direction.combineCardinalDirections(EntityUtils.keysPressesToDirections(game.getKeyInputListener().getKeysPressed())).toString() :
					EntityUtils.getDirectionFromKeypress(game.getKeyInputListener().getLastKeyPressed()).toString();
			g.drawImage(images.get(directionName + currentAnimationStage), entity.getLocation().getX() - (int) game.getGameCamera().getxOffset(), entity.getLocation().getY() - (int) game.getGameCamera().getyOffset(), null);
		}
		else
		{
			g.drawImage(images.get(EntityUtils.getDirectionFromKeypress(game.getKeyInputListener().getLastKeyPressed()).toString() + "0"), entity.getLocation().getX() - (int)game.getGameCamera().getxOffset(), entity.getLocation().getY() - (int)game.getGameCamera().getyOffset(), null);
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
