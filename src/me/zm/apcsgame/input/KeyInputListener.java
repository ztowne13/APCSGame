package me.zm.apcsgame.input;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ztowne13 on 4/8/16.
 *
 * Listens for keys pressed by user.
 */
public class KeyInputListener implements KeyListener
{
	Game game;

	private ArrayList<Integer> directionalKeys = new ArrayList<Integer>(Arrays.asList(GameSettings.UP_KEY, GameSettings.DOWN_KEY, GameSettings.LEFT_KEY, GameSettings.RIGHT_KEY));

	/**
	 * Current pressed keys
	 */
	private ArrayList<Integer> keysPressed = new ArrayList<Integer>();

	/**
	 * The last directional key that was pressed
	 */
	int lastKeyPressed = GameSettings.DOWN_KEY;

	/**
	 * Are the directional keys pressed
	 */
	public boolean upKey, downKey, leftKey, rightKey, interact;

	/**
	 * Sets up the key listener
	 */
	public KeyInputListener(Game game)
	{
		this.game = game;
	}

	/**
	 * Updates the key pressed booleans to match what keys are actually pressed
	 */
	public void update()
	{
		upKey = keysPressed.contains(GameSettings.UP_KEY);
		downKey = keysPressed.contains(GameSettings.DOWN_KEY);
		leftKey = keysPressed.contains(GameSettings.LEFT_KEY);
		rightKey = keysPressed.contains(GameSettings.RIGHT_KEY);
		interact = keysPressed.contains(GameSettings.INTERACT_KEY);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(!keysPressed.contains(e.getKeyCode()))
		{
			keysPressed.add(e.getKeyCode());
		}

		if(directionalKeys.contains(e.getKeyCode()))
		{
			lastKeyPressed = e.getKeyCode();
		}

		if(e.getKeyCode() == GameSettings.PAUSE_KEY)
		{
			game.getCurrentLevel().getPauseMenu().setInPauseMenu(!game.getCurrentLevel().getPauseMenu().isInPauseMenu());
		}

		//test health bar
		if(e.getKeyCode()== e.VK_Z)
		{
			game.getCurrentLevel().getPlayer().damage(1);
		}
		else if(e.getKeyCode() == e.VK_SPACE)
		{
			game.getCurrentLevel().getPlayer().jump();
		}
	}


	@Override
	public void keyReleased(KeyEvent e)
	{
		if(keysPressed.contains(e.getKeyCode()))
		{
			keysPressed.remove((Object)e.getKeyCode());
		}
	}

	public ArrayList<Integer> getKeysPressed()
	{
		return keysPressed;
	}

	public void setKeysPressed(ArrayList<Integer> keysPressed)
	{
		this.keysPressed = keysPressed;
	}

	public int getLastKeyPressed()
	{
		return lastKeyPressed;
	}

	public void setLastKeyPressed(int lastKeyPressed)
	{
		this.lastKeyPressed = lastKeyPressed;
	}
}
