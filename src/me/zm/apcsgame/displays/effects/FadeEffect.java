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
	boolean endWhenDone;

	public FadeEffect(Game game, Color toFadeTo, int speedToFade, boolean fadeOut, boolean endWhenDone)
	{
		super(game);

		this.toFadeTo = toFadeTo;
		this.speedToFade = speedToFade;
		this.fadeOut = fadeOut;
		this.endWhenDone = endWhenDone;
	}

	public Color getCurrentColor()
	{
		return new Color(toFadeTo.getRed(), toFadeTo.getGreen(), toFadeTo.getBlue(), fadeOut ? 255 - tickCount : tickCount);

	}

	@Override
	public void draw(Graphics graphics)
	{
		graphics.setColor(getCurrentColor());
		graphics.fillRect(0, 0, game.getDisplay().getFrame().getWidth(), game.getDisplay().getFrame().getHeight());
	}

	@Override
	public boolean tick()
	{
		tickCount += speedToFade;
		if (tickCount > 255)
		{
			tickCount = 255;
			if(endWhenDone)
			{
				return false;
			}
		}
		return true;
	}
}
