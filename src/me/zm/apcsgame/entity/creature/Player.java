package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.input.KeyInputListener;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class Player extends Creature
{
	int maxhealth, health, speed;

	public Player(Game game, int x, int y)
	{
		super(game, x, y, CreatureType.PLAYER);
	}

	@Override
	public void checkMove()
	{
		KeyInputListener keyInputListener = getGame().getKeyInputListener();
		if(keyInputListener.downKey)
		{
			setX(getX() + );
		}
	}

	@Override
	public void draw()
	{

	}
}
