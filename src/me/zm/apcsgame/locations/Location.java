package me.zm.apcsgame.locations;

import com.badlogic.gdx.math.Vector2;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.utils.MathUtils;

/**
 * Created by ztowne13 on 4/20/16.
 */
public class Location implements Locatable
{
	Game game;

	Direction direction;
	int x, y;

	public Location(Game game, int x, int y)
	{
		this(game, x, y, Direction.SOUTH);
	}

	public Location(Game game, int x, int y, Direction direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.game = game;
	}

	public Vector2 getVector()
	{
		return new Vector2(x, y);
	}

	public Vector2 getVectorAsMiddle(int width, int height)
	{
		return new Vector2(MathUtils.middle(x, width), MathUtils.middle(y, height));
	}

	public Vector2 getVectorAsMiddleWithOffset(int width, int height)
	{
		return new Vector2(MathUtils.middle(x, width) - game.getCurrentLevel().getGameCamera().getxOffset(), MathUtils.middle(y, height) - game.getCurrentLevel().getGameCamera().getyOffset());
	}

	@Override
	public void setX(int x)
	{
		this.x = x;
	}

	@Override
	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
}
