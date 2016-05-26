package me.zm.apcsgame.displays.animations;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.level.GameCamera;
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
		loadImages(1);
	}

	public void loadImages(double scale)
	{
		for(int i = 0; i < framesCount; i++)
		{
			images.put(i + "", FileUtils.loadImage(defaultPath + animationType.name() + "_" + (i+1) + ".png", scale));
		}
	}

	@Override
	public void individualTick()
	{

	}

	public void render(Graphics graphics)
	{
		GameCamera gc = getGame().getCurrentLevel().getGameCamera();
		graphics.drawImage(images.get(currentAnimationStage + ""), getLocation().getX() - (int)gc.getxOffset(), getLocation().getY() - (int)gc.getyOffset(), null);
	}
}
