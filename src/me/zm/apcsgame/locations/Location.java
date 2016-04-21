package me.zm.apcsgame.locations;

/**
 * Created by ztowne13 on 4/20/16.
 */
public class Location implements Locatable
{
	Direction direction;
	int x, y;

	public Location(int x, int y)
	{
		this(x, y, Direction.SOUTH);
	}

	public Location(int x, int y, Direction direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
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
