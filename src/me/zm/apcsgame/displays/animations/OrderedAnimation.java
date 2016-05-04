package me.zm.apcsgame.displays.animations;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;

/**
 * Created by ztowne13 on 5/1/16.
 */
public class OrderedAnimation extends Animation
{
	String animationName;
	public OrderedAnimation(Game game, AnimationType animationType, Location location)
	{
		super(game, animationType, location, "/ordered_animations/");
	}

	@Override
	public void loadImages()
	{
		for(int i = 0; i < framesCount; i++)
		{
			images.put(i + "", FileUtils.loadImage(defaultPath + animationType.name() + "_" + (i+1) + ".png"));
		}
	}

	@Override
	public void individualTick()
	{

	}

	public void render(Graphics graphics)
	{
		graphics.drawImage(images.get(currentAnimationStage + ""), getLocation().getX(), getLocation().getY(), null);
	}
}
