package me.zm.apcsgame.displays;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.locations.Locatable;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;

/**
 * Created by ztowne13 on 4/17/16.
 */
public class MousePointer implements Locatable
{
	Game game;
	int x, y;

	Image image;

	/**
	 * Loads the custom mouse pointer
	 * @param game The game instance
	 */
	public MousePointer(Game game)
	{
		this.game = game;
		this.image = FileUtils.loadImage("hud/APCSGamePointer.png");
		this.image = image.getScaledInstance(image.getWidth(null)*2, image.getHeight(null)*2, 0);

		game.getDisplay().getFrame().getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image,  new Point(0, 0), "Custom Cursor"));
	}

	/**
	 * Gets the 'hitbox' of the mouse for use in detecting if it is clicked on things.
	 * @return The rectangular hitbox of the mouse based off of it's X and Y coordinates with the image's width and heigh as the width and height
	 */
	public Rectangle getMouseHitbox()
	{
		Point point = MouseInfo.getPointerInfo().getLocation();
		Rectangle rect = new Rectangle((int)point.getX(), (int)point.getY(), image.getWidth(null), image.getHeight(null));
		return rect;
	}

	public void tick()
	{
		this.x = (int)MouseInfo.getPointerInfo().getLocation().getX();
		this.y = (int)MouseInfo.getPointerInfo().getLocation().getY();
	}

	public void render(Graphics g)
	{
		g.drawImage(image, getX() - game.getDisplay().getFrame().getX(), getY() - game.getDisplay().getFrame().getY(), null);
	}

	@Override
	public void setX(int x)
	{
		this.x = x;
	}

	@Override
	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}
}
