package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Creature extends Entity
{
	CreatureType creatureType;

	public Creature(Game game, int x, int y, int width, int height, CreatureType creatureType)
	{
		super(game, x, y, width, height);
		this.creatureType = creatureType;
	}

	public abstract void checkMove();

	public CreatureType getCreatureType()
	{
		return creatureType;
	}

	public void setCreatureType(CreatureType creatureType)
	{
		this.creatureType = creatureType;
	}

}
