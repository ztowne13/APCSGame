package me.zm.apcsgame.input;

import me.zm.apcsgame.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ztowne13 on 4/8/16.
 *
 * Listens for keys pressed by user.
 */
public class KeyInputListener implements KeyListener
{
	private Game game;

	/**
	 * Sets up the key listener
	 * @param game The main game class.
	 */
	public KeyInputListener(Game game)
	{
		this.game = game;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{

	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}
}
