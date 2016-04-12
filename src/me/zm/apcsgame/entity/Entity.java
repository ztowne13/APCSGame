package me.zm.apcsgame.entity;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.Locatable;

import java.util.UUID;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Entity implements Locatable
{
	private Game game;

	private UUID uuid;
	private int x, y;

	/**
	 * Creates a new entity model
	 * @param game The running game instnace
	 * @param x The entity's x position
	 * @param y
	 */
	public Entity(Game game, int x, int y)
	{
		this.game = game;
		this.x = x;
		this.y = y;

		this.uuid = UUID.randomUUID();
	}

	public abstract void draw();

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}


}
