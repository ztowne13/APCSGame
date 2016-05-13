package me.zm.apcsgame.displays.effects;

import me.zm.apcsgame.Game;

import java.awt.*;

/**
 * Created by ztowne13 on 5/12/16.
 */
public abstract class GraphicEffect
{
	Game game;
	int tickCount = 0;

	public GraphicEffect(Game game)
	{
		this.game = game;
	}

	public abstract void draw(Graphics graphics);

	public abstract boolean tick();

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	public int getTickCount()
	{
		return tickCount;
	}

	public void setTickCount(int tickCount)
	{
		this.tickCount = tickCount;
	}
}
