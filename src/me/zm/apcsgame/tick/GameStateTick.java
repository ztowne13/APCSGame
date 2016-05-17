package me.zm.apcsgame.tick;

import me.zm.apcsgame.Game;

import java.awt.*;

/**
 * Created by ztowne13 on 5/16/16.
 */
public abstract class GameStateTick
{
	Game game;

	public GameStateTick(Game game)
	{
		this.game = game;
	}

	public abstract void tick();

	public abstract void draw(Graphics graphics);
}
