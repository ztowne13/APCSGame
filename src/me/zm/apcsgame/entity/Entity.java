package me.zm.apcsgame.entity;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.locations.Location;

import java.awt.*;
import java.util.UUID;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Entity
{
	private Game game;

	private UUID uuid;
	private int width, height;

	Location location;

	/**
	 * Creates a new entity model
	 * @param game The running game instnace
	 * @param x The entity's x position
	 * @param y
	 */
	public Entity(Game game, int x, int y, int width, int height)
	{
		this.game = game;
		this.width = width;
		this.height = height;

		this.location = new Location(x, y);

		this.uuid = UUID.randomUUID();
	}

	public abstract void tick();

	public abstract void draw(Graphics g);

	/**
	 * Checks whether or not the entity should be rendered first because it is beneath the entity
	 * @param entity The other object with x and y coordinates to see if it should be rendered before.
	 * @return Returns true if it should render before and false if it should render after
	 */
	public boolean renderBefore(Entity entity)
	{
		return location.getY() + getHeight() > entity.getLocation().getY() + entity.getHeight();
	}

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

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}
}
