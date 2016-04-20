package me.zm.apcsgame.utils;

import apple.laf.JRSUIConstants;
import me.zm.apcsgame.GameSettings;

/**
 * Created by ztowne13 on 4/19/16.
 */
public class EntityUtils
{
	public static JRSUIConstants.Direction getDirectionFromArrowkey(int key)
	{
		JRSUIConstants.Direction direction = null;
		if(key == GameSettings.UP_KEY)
		{
			direction = JRSUIConstants.Direction.NORTH;
		}
		else if(key == GameSettings.DOWN_KEY)
		{
			direction = JRSUIConstants.Direction.SOUTH;
		}
		else if(key == GameSettings.RIGHT_KEY)
		{
			direction = JRSUIConstants.Direction.EAST;
		}
		else if(key == GameSettings.LEFT_KEY)
		{
			direction = JRSUIConstants.Direction.WEST;
		}
		return direction;
	}
}
