package me.zm.apcsgame.displays.effects;

import me.zm.apcsgame.Game;

import java.awt.*;

/**
 * Created by ztowne13 on 5/18/16.
 */
public class MaskEffect extends GraphicEffect
{
	Color maskColor;
	int ticksToLive;
	int ticksAlive = 0;

	public MaskEffect(Game game, Color maskColor, int ticksToLive)
	{
		super(game);

		this.maskColor = maskColor;
		this.ticksToLive = ticksToLive;
	}

	@Override
	public void draw(Graphics graphics)
	{
		graphics.setColor(maskColor);
		graphics.fillRect(0, 0, game.getDisplay().getFrame().getWidth(), game.getDisplay().getFrame().getHeight());
	}

	@Override
	public boolean tick()
	{
		ticksAlive++;
		if(ticksToLive < ticksAlive && ticksToLive != -1)
		{
			return false;
		}
		return true;
	}
}
