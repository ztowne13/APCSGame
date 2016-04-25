package me.zm.apcsgame.utils;

import me.zm.apcsgame.level.Point;

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

	public static double calcRotationAngleInDegrees(Point centerPt, Point targetPt)
	{
		double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);

		theta += Math.PI/2.0;

		double angle = Math.toDegrees(theta);

		if (angle < 0) {
			angle += 360;
		}

		return angle;
	}
}
