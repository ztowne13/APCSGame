package me.zm.apcsgame.displays.menus;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 5/15/16.
 */
public class PauseMenu
{
	Game game;

	BufferedImage pauseImage;

	int box_width = 162;

	int pixel_y_resume = 394;
	int pixel_y_settings = 703;
	int pixel_y_mainMenu = 1373;

	boolean inPauseMenu = false;

	public PauseMenu(Game game)
	{
		this.game = game;
	}

	public void loadImage()
	{
		pauseImage = FileUtils.loadImage("hud/Pause-Menu.png");
	}

	public void draw(Graphics graphics)
	{
		if(inPauseMenu)
		{
			graphics.drawImage(pauseImage, 0, 0, game.getDisplay().getFrame().getWidth(), game.getDisplay().getFrame().getHeight(), null);
		}
	}

	public boolean isInPauseMenu()
	{
		return inPauseMenu;
	}

	public void setInPauseMenu(boolean inPauseMenu)
	{
		this.inPauseMenu = inPauseMenu;
	}
}
