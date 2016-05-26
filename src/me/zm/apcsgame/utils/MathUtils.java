package me.zm.apcsgame.utils;

import me.zm.apcsgame.level.Point;
import me.zm.apcsgame.locations.Location;


/**
 * Created by ztowne13 on 4/24/16.
 */
public class MathUtils
{
	public static Point getPointOnCircumfernce(int radius, int angle, Point origin)
	{
		int x = (int) (origin.x + radius * Math.cos(angle));
		int y = (int) (origin.y + radius * Math.sin(angle));

		return new Point(x, y);
	}

	public static double distance(Location l1, Location l2)
	{
		return Math.sqrt(Math.pow(Math.abs(l1.getX() - l2.getX()) / 2, 2) + Math.pow(Math.abs(l1.getY() - l2.getY()) / 2, 2));
	}

	/**
	 * Gets the middle position of the entity
	 * @param xOrY the x or y position of the entity
	 * @param widthOrHeight The width or height of the entity
	 * @return The middle of that position.
	 */
	public static int middle(int xOrY, int widthOrHeight)
	{
		return xOrY + (widthOrHeight/2);
	}

}
