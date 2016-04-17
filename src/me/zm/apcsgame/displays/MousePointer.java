package me.zm.apcsgame.displays;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.utils.GraphicUtils;
import me.zm.apcsgame.utils.Locatable;

import java.awt.*;

/**
 * Created by ztowne13 on 4/17/16.
 */
public class MousePointer implements Locatable
{
	Game game;
	int x, y;

	Image image;

	public MousePointer(Game game)
	{
		this.game = game;
		this.image = GraphicUtils.loadImage("hud/APCSGamePointer.png");
		this.image = image.getScaledInstance(image.getWidth(null)*2, image.getHeight(null)*2, 0);

		game.getDisplay().getFrame().getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image,  new Point(0, 0), "Custom Cursor"));
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
