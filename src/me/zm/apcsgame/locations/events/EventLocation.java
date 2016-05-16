package me.zm.apcsgame.locations.events;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.locations.Locatable;

import java.awt.*;

/**
 * Created by ztowne13 on 5/14/16.
 */
public abstract class EventLocation implements Locatable
{
	Game game;
	int x, y, eventRadius;

	public EventLocation(Game game, int x, int y, int eventRadius)
	{
		this.game = game;
		this.x = x;
		this.y = y;
		this.eventRadius = eventRadius;
	}

	public abstract void executeFor(Graphics graphics, Entity entity);

	public abstract boolean isExecutable(Entity entity);

	@Override
	public void setX(int x)
	{
		this.x = x;
	}

	@Override
	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}
}
