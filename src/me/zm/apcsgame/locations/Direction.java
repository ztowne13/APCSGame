package me.zm.apcsgame.locations;

import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/20/16.
 *
 * The degrees travel counter-clockwise
 */
public enum Direction
{
	NORTH(90),

	EAST(0),

	SOUTH(270),

	WEST(180),

	NORTH_EAST(45),

	SOUTH_EAST(315),

	SOUTH_WEST(225),

	NORTH_WEST(135);

	int degrees;

	Direction(int degrees)
	{
		this.degrees = degrees;
	}

	/**
	 * Takes a list of multiple directions of NORTH,EAST,SOUTH,WEST and changes it into all the cardinal directions
	 * @param dir The list of cardinal directions
	 * @return The one direction that is the combination of all directions.
	 */
	public static Direction combineCardinalDirections(ArrayList<Direction> dir)
	{
		if(dir.contains(NORTH) && dir.contains(SOUTH))
		{
			dir.remove(NORTH); dir.remove(SOUTH);
		}
		else if(dir.contains(WEST) && dir.contains(EAST))
		{
			dir.remove(EAST); dir.remove(WEST);
		}

		if(dir.contains(NORTH) && dir.contains(EAST))
		{
			return NORTH_EAST;
		}
		else if(dir.contains(NORTH) && dir.contains(WEST))
		{
			return NORTH_WEST;
		}
		else if(dir.contains(SOUTH) && dir.contains(EAST))
		{
			return SOUTH_EAST;
		}
		else if(dir.contains(SOUTH) && dir.contains(WEST))
		{
			return SOUTH_WEST;
		}
		else
		{
			return dir.size() == 0 ? SOUTH : dir.get(0);
		}
	}
}
