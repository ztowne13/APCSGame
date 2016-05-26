package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.ai.interactions.InteractionAIType;
import me.zm.apcsgame.ai.pathfinding.PathfinderAIType;
import me.zm.apcsgame.displays.animations.AnimationType;
import me.zm.apcsgame.displays.animations.DirectionalAnimation;
import me.zm.apcsgame.displays.effects.FadeEffect;
import me.zm.apcsgame.displays.effects.MaskEffect;
import me.zm.apcsgame.displays.effects.WastedEffect;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.level.GameCamera;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.locations.Location;
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
	Location lastCheckPoint;

	float lastJump = 0;
	DirectionalAnimation walkAnimation;
	DirectionalAnimation swingAnimation;

	public Player(Game game, String id, int x, int y, int width, int height, int speed)
	{
		super(game, x, y, width, height, 7, CreatureType.PLAYER, PathfinderAIType.NONE, InteractionAIType.NONE);
		this.id = id;
		this.speed = speed;
		lastCheckPoint = new Location(getGame(), getLocation().getX(), getLocation().getY());

		this.walkAnimation = new DirectionalAnimation(game, AnimationType.PLAYER_WALK, getLocation());
		walkAnimation.loadImages(2);

		this.swingAnimation = new DirectionalAnimation(game, AnimationType.PLAYER_SWING, getLocation());
		swingAnimation.loadImages(2);

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
		if (!isDead())
		{
			getGame().getKeyInputListener().update();
			getLocation().setDirection(Direction.combineCardinalDirections(EntityUtils.keysPressesToDirections(getGame().getKeyInputListener().getKeysPressed())));

			checkMove();

			if (getGame().getTicksAlive() % getTickDelay() == 0)
			{
				if (moving)
				{
					if(canMove())
					{
						walkAnimation.tick();
					}

					try
					{
						walkSound.mark(20);
						walkSound.reset();

						if (clip != null)
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

						if (getGame().getTicksAlive() % 21 == 0)
						{
							//clip.start();
						}
					}
					catch (Exception exc)
					{
						exc.printStackTrace();
					}
				}
			}
		}
	}

	public void jump()
	{
		System.out.println(System.nanoTime() - lastJump);
		if(System.nanoTime() - lastJump > GameSettings.jumpCooldown)
		{
			int tempX = getLocation().getX();
			int tempY = getLocation().getY();
			
			GameCamera gc = getGame().getCurrentLevel().getGameCamera();

			int pX = getLocation().getX() - (int) gc.getxOffset();
			int pY = getLocation().getY() - (int) gc.getyOffset();

			int mX = getGame().getMouseEventListener().getX();
			int mY = getGame().getMouseEventListener().getY();

			double angle = Math.toDegrees(Math.atan2(mY - pY, mX - pX));

			double sin = Math.sin(Math.toRadians(angle)) * GameSettings.toMoveJump;
			double cos = Math.cos(Math.toRadians(angle)) * GameSettings.toMoveJump;

			getLocation().setX(getLocation().getX() + (int) cos);
			getLocation().setY(getLocation().getY() + (int) sin);
			
			if(!collides())
			{
				gc.setToMoveX(gc.getToMoveX() + (float) cos);
				gc.setToMoveY(gc.getToMoveY() + (float) sin);
			}
			else
			{
				getLocation().setX(tempX);
				getLocation().setY(tempY);
			}

			lastJump = System.nanoTime();
		}
	}

	/**
	 * Checks whether or not the player should be moving based on the keys that are pressed.
	 */
	@Override
	public void checkMove()
	{
		if(canMove())
		{
			int xMove = 0;
			int yMove = 0;

			// To revert back to original position if position outside bounds.
			int tempX = getLocation().getX();
			int tempY = getLocation().getY();

			KeyInputListener keyInputListener = getGame().getKeyInputListener();
			if (keyInputListener.downKey)
				yMove = speed;
			if (keyInputListener.upKey)
				yMove = -speed;
			if (keyInputListener.leftKey)
				xMove = -speed;
			if (keyInputListener.rightKey)
				xMove = speed;

			getLocation().setX(getLocation().getX() + xMove);

			if (collides() && !GameSettings.levelBuildMode)
			{
				getLocation().setX(tempX);
				xMove = 0;
			}

			getLocation().setY(getLocation().getY() + yMove);

			if (collides() && !GameSettings.levelBuildMode)
			{
				getLocation().setY(tempY);
				yMove = 0;
			}

			moving = yMove != 0 || xMove != 0;

			if (getGame().getCurrentLevel().getGameCamera().moveGameCamera(this))
			{
				getGame().getCurrentLevel().getGameCamera().move(xMove, yMove);
			}
		}
	}

	public void respawn(boolean fadeOut)
	{
		getGame().getGraphicEffects().put("fade out respawn", new FadeEffect(getGame(), Color.BLACK, 1, true, true));
		setLocation(lastCheckPoint);
		walkAnimation.setLocation(getLocation());
		getGame().getCurrentLevel().getGameCamera().centerOnEntity(this);
		setHealth(getMaxhealth());
		setDead(false);
		getGame().getGraphicEffects().remove("0");
		getGame().getGraphicEffects().remove("2");
		getGame().getGraphicEffects().remove("1");

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


		if(System.nanoTime() - lastSwing > GameSettings.swingCooldown)
		{
			walkAnimation.render(!moving, direction, graphics);
		}
		else
		{
			if (getGame().getTicksAlive() % 4 == 0)
			{
				swingAnimation.tick();
				swingAnimation.render(false, direction, graphics);
			}
			else
			{
				swingAnimation.render(false, direction, graphics);
			}
		}
	}

	@Override
	public void destroy()
	{
		setDead(true);
		getGame().getGraphicEffects().put("2", new FadeEffect(getGame(), Color.BLACK, 2, false, false));
		getGame().getGraphicEffects().put("1", new MaskEffect(getGame(), Color.RED, 100, -1));
		getGame().getGraphicEffects().put("0",new WastedEffect(getGame(),false));

		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					sleep(4000);
					respawn(true);
					interrupt();
				}
				catch(Exception exc)
				{

				}
			}
		};

		thread.start();
	}

	@Override
	public boolean canMove()
	{
		return (System.nanoTime() - lastSwing > GameSettings.swingCooldown);
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
