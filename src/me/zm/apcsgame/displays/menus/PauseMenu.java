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

			double heightScale = (double)game.getDisplay().getFrame().getHeight() / (double)pauseImage.getHeight();

			int mY = game.getMouseEventListener().getY();

			for(int i : new Integer[]{pixel_y_mainMenu, pixel_y_resume, pixel_y_settings})
			{
				i = (int)(i * heightScale);
				if(mY > i && mY <= i + (int)(heightScale*box_width))
				{
					graphics.setColor(new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 150));
					graphics.fillRect(0, i, game.getDisplay().getFrame().getWidth(), (int)(box_width*heightScale) + 1);
				}
			}
		}
	}

	public void click()
	{
		if(isInPauseMenu())
		{
			double heightScale = (double)game.getDisplay().getFrame().getHeight() / (double)pauseImage.getHeight();
			int mY = game.getMouseEventListener().getY();

			if (mY > pixel_y_resume*heightScale && mY < (pixel_y_resume + box_width)*heightScale)
			{
				setInPauseMenu(false);
			}
			if (mY > pixel_y_settings*heightScale && mY < (pixel_y_settings + box_width)*heightScale)
			{

			}
			if (mY > pixel_y_mainMenu*heightScale && mY < (pixel_y_mainMenu + box_width)*heightScale)
			{

			}
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
