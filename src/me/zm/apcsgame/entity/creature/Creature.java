package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Creature extends Entity
{
	CreatureType creatureType;

	int health, size, speed;
	String name;

	public Creature(Game game, int x, int y, CreatureType creatureType)
	{
		super(game, x, y);
		this.creatureType = creatureType;
	}

	public abstract void move(int x, int y);

	public CreatureType getCreatureType()
	{
		return creatureType;
	}

	public void setCreatureType(CreatureType creatureType)
	{
		this.creatureType = creatureType;
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
}
