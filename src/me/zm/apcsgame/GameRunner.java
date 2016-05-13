package me.zm.apcsgame;

/**
 * Created by ztowne13 on 4/8/16.
 *
 * Main runner class for the APCS game.
 */
public class GameRunner
{
	static int WIDTH = 1000;
	static int HEIGHT = 1000;

	public static void main(String[] args)
	{
		Game game = new Game(WIDTH, HEIGHT);
		game.start();
	}
}
