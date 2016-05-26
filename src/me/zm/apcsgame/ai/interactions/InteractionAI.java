package me.zm.apcsgame.ai.interactions;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.ai.ActionAI;
import me.zm.apcsgame.entity.creature.Creature;

/**
 * Created by ztowne13 on 5/25/16.
 */
public abstract class InteractionAI extends ActionAI
{
	public InteractionAI(Game game, Creature creature)
	{
		super(game, creature);
	}
}
