package me.zm.apcsgame;

import java.awt.event.KeyEvent;

/**
 * Created by ztowne13 on 4/8/16.
 */
public class GameSettings
{
	public static int FPS = 60;
	public static int totalAnimationFrames = 1;

	/**
	 * How many pixels smaller the friendly hitbox will be and bigger the enemy hitbox will be.
	 */
	public static int hitBoxGiveTake = 10;

	/**
	 * The amount of pixels that the player can move under breakable tiles.
	 */
	public static int xShift = 10;

	public static int UP_KEY = KeyEvent.VK_W;
	public static int DOWN_KEY = KeyEvent.VK_S;
	public static int LEFT_KEY = KeyEvent.VK_A;
	public static int RIGHT_KEY = KeyEvent.VK_D;
}
