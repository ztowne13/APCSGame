package me.zm.apcsgame.input;

import me.zm.apcsgame.GameSettings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by ztowne13 on 4/8/16.
 *
 * Listens for keys pressed by user.
 */
public class KeyInputListener implements KeyListener
{
	private boolean[] keys;
	public boolean upKey, downKey, leftKey, rightKey;

	/**
	 * Sets up the key listener
	 */
	public KeyInputListener()
	{

	}

	public void update()
	{
		upKey = keys[GameSettings.UP_KEY];
		downKey = keys[GameSettings.DOWN_KEY];
		leftKey = keys[GameSettings.LEFT_KEY];
		rightKey = keys[GameSettings.RIGHT_KEY];
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}
}
