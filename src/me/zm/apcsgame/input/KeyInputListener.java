package me.zm.apcsgame.input;

import me.zm.apcsgame.GameSettings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/8/16.
 *
 * Listens for keys pressed by user.
 */
public class KeyInputListener implements KeyListener
{
	private ArrayList<Integer> keysPressed = new ArrayList<>();
	public boolean upKey, downKey, leftKey, rightKey;
	public int lastKeyPressed = 0;

	/**
	 * Sets up the key listener
	 */
	public KeyInputListener()
	{

	}

	public void update()
	{
		upKey = keysPressed.contains(GameSettings.UP_KEY);
		downKey = keysPressed.contains(GameSettings.DOWN_KEY);
		leftKey = keysPressed.contains(GameSettings.LEFT_KEY);
		rightKey = keysPressed.contains(GameSettings.RIGHT_KEY);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		keysPressed.add(e.getKeyCode());
		lastKeyPressed = e.getKeyCode();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(keysPressed.contains(e.getKeyCode()))
		{
			keysPressed.remove((Object)e.getKeyCode());
		}
	}
}
