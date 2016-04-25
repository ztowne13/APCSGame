package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.breakables.Tile;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.utils.EntityUtils;
import me.zm.apcsgame.utils.GraphicUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class Player extends Creature
{
	String id;
	int speed;

	// This line will be removed later to support animated characters.
	BufferedImage image;

	public Player(Game game, String id, int x, int y, int width, int height, int speed)
	{
		super(game, x, y, width, height, CreatureType.PLAYER.getDefaultHealth(), CreatureType.PLAYER);
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
		getLocation().setDirection(Direction.combineCardinalDirections(EntityUtils.keysPressesToDirections(getGame().getKeyInputListener().getKeysPressed())));
		checkMove();
	}

	@Override
	public void checkMove()
	{
		int xMove = 0;
		int yMove = 0;

		// To revert back to original position if position outside bounds.
		int tempX = getLocation().getX();
		int tempY = getLocation().getY();

		KeyInputListener keyInputListener = getGame().getKeyInputListener();
		if(keyInputListener.downKey)
			yMove = speed;
		if(keyInputListener.upKey)
			yMove = -speed;
		if(keyInputListener.leftKey)
			xMove = -speed;
		if(keyInputListener.rightKey)
			xMove = speed;

		getLocation().setX(getLocation().getX() + xMove);

		if(collides())
		{
			getLocation().setX(tempX);
			xMove = 0;
		}

		getLocation().setY(getLocation().getY() + yMove);

		if(collides())
		{
			getLocation().setY(tempY);
			yMove = 0;
		}

		getGame().getGameCamera().move(xMove, yMove);
	}

	// Checks if the player collides with the walls or entity

	public boolean collides()
	{
		boolean collidesWithTile = false;

		for(Entity tile : getGame().getEntities())
		{
			if(tile instanceof Tile)
			{
				if (collidesWith(tile.getHitbox()))
				{
					collidesWithTile = true;
					break;
				}
			}
		}

		return getGame().getCurrentLevel().isEntityOutsideBounds(this) || collidesWithTile;
	}


	@Override
	public void draw(Graphics graphics)
	{
		graphics.drawImage(image, getLocation().getX() - (int)getGame().getGameCamera().getxOffset(), getLocation().getY() - (int)getGame().getGameCamera().getyOffset(), null);
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}


}
