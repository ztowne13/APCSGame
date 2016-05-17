package me.zm.apcsgame.tick;

import me.zm.apcsgame.Game;

import java.awt.*;

/**
 * Created by ztowne13 on 5/16/16.
 */
public class StartupGSTick extends GameStateTick
{
	public StartupGSTick(Game game)
	{
		super(game);
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void draw(Graphics graphics)
	{
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, game.getWidth(), game.getHeight());
	}
}
