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
		int tempX = getX();
		int tempY = getY();

		KeyInputListener keyInputListener = getGame().getKeyInputListener();
		if(keyInputListener.downKey)
		{
			setY(getY() + speed);
		}
		if(keyInputListener.upKey)
		{
			setY(getY() - speed);
		}
		if(keyInputListener.leftKey)
		{
			setX(getX() - speed);
		}
		if(keyInputListener.rightKey)
		{
			setX(getX() + speed);
		}

		if(getGame().getCurrentLevel().isEntityOutsideBounds(this))
		{
			System.out.println("is outside bounds");
			setX(tempX);
			setY(tempY);
		}

		getGame().getGameCamera().move(getX() - tempX, getY() - tempY);
	}

	@Override
	public void draw(Graphics graphics)
	{
		graphics.drawImage(image, getX(), getY(), null);
	}
}
