package me.zm.apcsgame.utils;

import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.locations.Direction;

import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/19/16.
 */
public class EntityUtils
{
	/**
	 * Turns the key code into a direction
	 * @param key The key code
	 * @return The direction the key code turned into.
	 */
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

	/**
	 * Turns a list of key codes into a list of directions
	 * @param pressedKeys List of key codes
	 * @return List of translated directions
	 */
	public static ArrayList<Direction> keysPressesToDirections(ArrayList<Integer> pressedKeys)
	{
		ArrayList<Direction> directions = new ArrayList<Direction>();

		for(int i : (ArrayList<Integer>) pressedKeys.clone())
		{
			directions.add(getDirectionFromKeypress(i));
		}

		return directions;
	}

}
