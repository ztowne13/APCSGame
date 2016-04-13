package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.input.KeyInputListener;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class Player extends Creature
{
	int maxhealth, health, speed;

	public Player(Game game, int x, int y, int width, int height)
	{
		super(game, x, y, width, height, CreatureType.PLAYER);
	}

	@Override
	public void tick()
	{
		checkMove();
	}

	@Override
	public void checkMove()
	{
		int tempX = getX();
		int tempY = getY();

		KeyInputListener keyInputListener = getGame().getKeyInputListener();
		if(keyInputListener.downKey)
		{
			setY(getY() - speed);
		}
		else if(keyInputListener.upKey)
		{
			setY(getY() - speed);
		}
		else if(keyInputListener.leftKey)
		{
			setX(getX() - speed);
		}
		else if(keyInputListener.rightKey)
		{
			setX(getX() + speed);
		}

		if(getGame().getCurrentLevel().isEntityOutsideBounds(this))
		{
			setX(tempX);
			setY(tempY);
		}

		getGame().getGameCamera().centerOnEntity(this);
	}

	@Override
	public void draw()
	{

	}
}
