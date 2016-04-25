package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Monster extends Creature
{
	public Monster(Game game, int x, int y, int width, int height, int maxhealth, CreatureType creatureType)
	{
		super(game, x, y, width, height, maxhealth, creatureType);
	}

	/**
	 * Begins a specific AI movement
	 */
	public void run_ai_movement()
	{

	}
}
