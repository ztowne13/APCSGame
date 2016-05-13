package me.zm.apcsgame.displays.effects;

import me.zm.apcsgame.Game;

import java.awt.*;

/**
 * Created by ztowne13 on 5/12/16.
 */
public class FadeEffect extends GraphicEffect
{
	Color toFadeTo;
	int speedToFade;

	boolean fadeOut;

	public FadeEffect(Game game, Color toFadeTo, int speedToFade, boolean fadeOut)
	{
		super(game);

		this.toFadeTo = toFadeTo;
		this.speedToFade = speedToFade;
		this.fadeOut = fadeOut;
	}

	public Color getCurrentColor()
	{
		return new Color(toFadeTo.getRed(), toFadeTo.getGreen(), toFadeTo.getBlue(), fadeOut ? 255 - tickCount : tickCount);

	}

	@Override
	public void draw(Graphics graphics)
	{
		graphics.setColor(getCurrentColor());
		graphics.drawRect(0, 0, (int)graphics.getClipBounds().getWidth(), (int)graphics.getClipBounds().getHeight());
	}

	@Override
	public boolean tick()
	{
		setTickCount(getTickCount() + speedToFade);

		if(getTickCount() > 255)
		{
			setTickCount(255);
			return false;
		}

		return true;
	}
}
