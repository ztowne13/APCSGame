package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.utils.GraphicUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class Player extends Creature
{
	String id;
	int maxhealth, health, speed;

	// This line will be removed later to support animated characters.
	BufferedImage image;

	public Player(Game game, String id, int x, int y, int width, int height, int speed)
	{
		super(game, x, y, width, height, CreatureType.PLAYER);
		this.id = id;
		this.speed = speed;
		this.image = GraphicUtils.loadImage("characters/" + id + ".png");
		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}

	@Override
	public void tick()
	{
		getGame().getKeyInputListener().update();
		checkMove();
	}

	@Override
	public void checkMove()
	{
		int xMove = 0;
		int yMove = 0;

		// To revert back to original position if position outside bounds.
		int tempX = getX();
		int tempY = getY();

		KeyInputListener keyInputListener = getGame().getKeyInputListener();
		if(keyInputListener.downKey)
		{
			yMove = speed;
		}
		if(keyInputListener.upKey)
		{
			yMove = -speed;
		}
		if(keyInputListener.leftKey)
		{
			xMove = -speed;
		}
		if(keyInputListener.rightKey)
		{
			xMove = speed;
		}

		setX(getX() + xMove);
		setY(getY() + yMove);

		if(getGame().getCurrentLevel().isEntityOutsideBounds(this))
		{
			xMove = 0;
			yMove = 0;

			setX(tempX);
			setY(tempY);
		}

		getGame().getGameCamera().move(xMove, yMove);
	}

	@Override
	public void draw(Graphics graphics)
	{
		graphics.drawImage(image, getX() - (int)getGame().getGameCamera().getxOffset(), getY() - (int)getGame().getGameCamera().getyOffset(), null);
	}
}
