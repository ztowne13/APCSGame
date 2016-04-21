package me.zm.apcsgame.utils;

import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.locations.Direction;

import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/19/16.
 */
public class EntityUtils
{
	public static Direction getDirectionFromKeypress(int key)
	{
		Direction direction = null;
		if(key == GameSettings.UP_KEY)
		{
			direction = Direction.NORTH;
		}
		else if(key == GameSettings.DOWN_KEY)
		{
			direction = Direction.SOUTH;
		}
		else if(key == GameSettings.RIGHT_KEY)
		{
			direction = Direction.EAST;
		}
		else if(key == GameSettings.LEFT_KEY)
		{
			direction = Direction.WEST;
		}
		return direction;
	}

	public static ArrayList<Direction> keysPressesToDirections(ArrayList<Integer> pressedKeys)
	{
		ArrayList<Direction> directions = new ArrayList<>();

		for(Integer i : pressedKeys)
		{
			directions.add(getDirectionFromKeypress(i));
		}

		return directions;
	}
}
