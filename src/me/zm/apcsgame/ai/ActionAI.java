package me.zm.apcsgame.ai;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.creature.Creature;

/**
 * Created by ztowne13 on 5/25/16.
 */
public abstract class ActionAI
{
	Game game;
	Creature entity;

	public ActionAI(Game game, Creature entity)
	{
		this.game = game;
		this.entity = entity;
	}

	public abstract boolean run();

	public Creature getEntity()
	{
		return entity;
	}

	public void setEntity(Creature entity)
	{
		this.entity = entity;
	}

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}
}
