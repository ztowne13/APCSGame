package me.zm.apcsgame.displays.animations;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ztowne13 on 4/19/16.
 *
 * Animations that are supposed to be draw in a specific direction.
 */
public class DirectionalAnimation extends Animation
{
	ArrayList<String> excludedDirections;
	public DirectionalAnimation(Game game, AnimationType animationType, Location location)
	{
		this(game, animationType, location, new ArrayList<String>());
	}

	public DirectionalAnimation(Game game, AnimationType animationType, Location location, ArrayList<String> excludedDirections)
	{
		super(game, animationType, location, "directional/");
		this.excludedDirections = excludedDirections;
	}

	@Override
	public void loadImages()
	{
		loadImages(1);
	}

	public void loadImages(double scale)
	{
		for(String dir : new String[]{"NORTH", "EAST", "SOUTH", "WEST"})
		{
			if (!excludedDirections.contains(dir))
			{
				for (int i = 0; i < framesCount; i++)
				{
					images.put(dir + i, FileUtils.loadImage(defaultPath + dir + "_" + (i + 1) + ".png", scale));
				}
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
