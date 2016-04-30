package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.displays.EntityWalkAnimation;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.breakables.Tile;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.sounds.Sound;
import me.zm.apcsgame.utils.EntityUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class Player extends Creature
{
	String id;
	int speed;

	AudioInputStream walkSound;
	Clip clip;
	boolean moving = false;

	// This line will be removed later to support animated characters.
	EntityWalkAnimation entityWalkAnimation;

	public Player(Game game, String id, int x, int y, int width, int height, int speed)
	{
		super(game, x, y, width, height, CreatureType.PLAYER.getDefaultHealth(), CreatureType.PLAYER);
		this.id = id;
		this.speed = speed;

		this.entityWalkAnimation = new EntityWalkAnimation(game, this);
		entityWalkAnimation.loadImages();

		setWidth(entityWalkAnimation.getImages().values().iterator().next().getWidth(null));
		setHeight(entityWalkAnimation.getImages().values().iterator().next().getHeight(null));

		walkSound = Sound.WALK.getSoundClip();
	}

	@Override
	public void tick()
	{
		getGame().getKeyInputListener().update();
		getLocation().setDirection(Direction.combineCardinalDirections(EntityUtils.keysPressesToDirections(getGame().getKeyInputListener().getKeysPressed())));
		checkMove();

		if(getGame().getTicksAlive() % 20 == 0)
		{
			if(moving)
			{
				entityWalkAnimation.tick();
				try
				{
					walkSound.mark(20);
					walkSound.reset();

					if(clip != null)
					{
						clip.stop();
					}
					else
					{
						clip = AudioSystem.getClip();
						clip.open(walkSound);
					}

					clip.setMicrosecondPosition(0);
					clip.start();
				}
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
			}
		}
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

		moving = yMove != 0 || xMove != 0;

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
				if (tile.collidesWith(getHitbox()))
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
		//graphics.drawImage(image, getLocation().getX() - (int)getGame().getGameCamera().getxOffset(), getLocation().getY() - (int)getGame().getGameCamera().getyOffset(), null);
		entityWalkAnimation.render(graphics);
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
