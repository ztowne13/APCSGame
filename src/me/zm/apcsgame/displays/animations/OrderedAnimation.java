package me.zm.apcsgame.displays.animations;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;

/**
 * Created by ztowne13 on 5/1/16.
 */
public class OrderedAnimation extends Animation
{
	String animationName;
	public OrderedAnimation(Game game, Entity entity, int animationStagesCount, String animationName)
	{
		super(game, entity, animationStagesCount);
		this.animationName = animationName;
	}

	@Override
	public void loadImages()
	{
		for(int i = 0; i < animationStagesCount; i++)
		{
			images.put(i + "", FileUtils.loadImage((entity instanceof Player ? "characters" : "creatures") + "/" + entity.getName().toLowerCase() + "/" + animationName + "_" + (i+1) + ".png"));
		}
	}

	@Override
	public void individualTick()
	{

	}

	public void render(Graphics graphics)
	{
		graphics.drawImage(images.get(currentAnimationStage + ""), entity.getLocation().getX(), entity.getLocation().getY(), null);
	}
}
