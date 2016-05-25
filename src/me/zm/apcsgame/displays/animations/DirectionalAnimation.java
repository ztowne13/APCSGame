package me.zm.apcsgame.displays.animations;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by ztowne13 on 4/19/16.
 *
 * Animations that are supposed to be draw in a specific direction.
 */
public class DirectionalAnimation extends Animation
{
	public DirectionalAnimation(Game game, AnimationType animationType, Location location)
	{
		super(game, animationType, location, "directional/");
	}

	@Override
	public void loadImages()
	{
		for(String dir : new String[]{"NORTH", "EAST", "SOUTH", "WEST"})
		{
			for(int i = 0; i < framesCount; i++)
			{
				images.put(dir + i, FileUtils.loadImage(defaultPath + dir + "_" + (i+1) + ".png", 2));
			}
		}
	}

	@Override
	public void individualTick()
	{

	}

	/**
	 * Renders the specific animation sequence.
	 * @param keepAtOne True = forces it to render the 1st image. False = render the specific image the animation sequence ison
	 * @param direction Direction to display the animation.
	 * @param g The graphics instance
	 */
	public void render(boolean keepAtOne, Direction direction, Graphics g)
	{
		g.drawImage(images.get(direction.name() + (keepAtOne ? 0 + "" : currentAnimationStage)), location.getX() - (int) game.getCurrentLevel().getGameCamera().getxOffset(), location.getY() - (int) game.getCurrentLevel().getGameCamera().getyOffset(), null);
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
