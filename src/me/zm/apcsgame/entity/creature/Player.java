package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class Player extends Creature
{
	public Player(Game game, int x, int y)
	{
		super(game, x, y, CreatureType.PLAYER);
	}

	@Override
	public void move(int x, int y)
	{

	}

	@Override
	public void draw()
	{

	}
}
