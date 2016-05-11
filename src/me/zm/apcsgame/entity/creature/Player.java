package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.displays.animations.AnimationType;
import me.zm.apcsgame.displays.animations.DirectionalAnimation;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.breakables.Tile;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.sounds.Sound;
import me.zm.apcsgame.utils.EntityUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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

	DirectionalAnimation walkAnimation;

	public Player(Game game, String id, int x, int y, int width, int height, int speed)
	{
		super(game, x, y, width, height, CreatureType.PLAYER.getDefaultHealth(), CreatureType.PLAYER);
		this.id = id;
		this.speed = speed;

		this.walkAnimation = new DirectionalAnimation(game, AnimationType.PLAYER_WALK, getLocation());
		walkAnimation.loadImages();

		setWidth(walkAnimation.getImages().values().iterator().next().getWidth(null));
		setHeight(walkAnimation.getImages().values().iterator().next().getHeight(null));

		walkSound = Sound.WALK.getSoundClip();
	}

	/**
	 * - Updates the direction the player is facing
	 * - Ticks the walking animation
	 * - Plays the walk sound, if the player is moving
	 */
	@Override
	public void tick()
	{
		getGame().getKeyInputListener().update();
		getLocation().setDirection(Direction.combineCardinalDirections(EntityUtils.keysPressesToDirections(getGame().getKeyInputListener().getKeysPressed())));
		checkMove();

		if(getGame().getTicksAlive() % 7 == 0)
		{
			if(moving)
			{
				walkAnimation.tick();
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

						FloatControl gainControl =
								(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
						gainControl.setValue(-15.0f);
					}

					clip.setMicrosecondPosition(0);

					if(getGame().getTicksAlive() % 21 == 0)
					{
						clip.start();
					}
				}
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
			}
		}
	}

	/**
	 * Checks whether or not the player should be moving based on the keys that are pressed.
	 */
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

		if(collides() && !GameSettings.levelBuildMode)
		{
			getLocation().setX(tempX);
			xMove = 0;
		}

		getLocation().setY(getLocation().getY() + yMove);

		if(collides() && !GameSettings.levelBuildMode)
		{
			getLocation().setY(tempY);
			yMove = 0;
		}

		moving = yMove != 0 || xMove != 0;

		if(getGame().getGameCamera().moveGameCamera(this))
		{
			getGame().getGameCamera().move(xMove, yMove);
		}
	}

	/**
	 * 	Checks if the player_walk collides with the walls or entity
	 */
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
		Direction combinedCardinal = Direction.combineCardinalDirections(EntityUtils.keysPressesToDirections(getGame().getKeyInputListener().getKeysPressed()));
		Direction lastKeypress = EntityUtils.getDirectionFromKeypress(getGame().getKeyInputListener().getLastKeyPressed());

		Direction direction = lastKeypress;

		if (moving)
		{
			try
			{
				direction = combinedCardinal.getDegrees() % 90 == 0 ? combinedCardinal : lastKeypress;
			}
			catch(Exception exc)
			{
				exc.printStackTrace();
			}
		}

		walkAnimation.render(!moving, direction, graphics);
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
