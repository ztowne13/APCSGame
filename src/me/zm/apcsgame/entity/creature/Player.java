package me.zm.apcsgame.entity.creature;

import com.badlogic.gdx.math.Vector2;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.ai.interactions.InteractionAIType;
import me.zm.apcsgame.ai.pathfinding.PathfinderAIType;
import me.zm.apcsgame.displays.animations.AnimationType;
import me.zm.apcsgame.displays.animations.DirectionalAnimation;
import me.zm.apcsgame.displays.effects.FadeEffect;
import me.zm.apcsgame.displays.effects.MaskEffect;
import me.zm.apcsgame.displays.effects.WastedEffect;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.tiles.Tile;
import me.zm.apcsgame.input.KeyInputListener;
import me.zm.apcsgame.level.GameCamera;
import me.zm.apcsgame.level.Level;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.EntityUtils;
import me.zm.apcsgame.utils.MathUtils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class Player extends Creature
{
	String id;
	int speed;

	Clip clip;
	boolean moving = false;
	Location lastCheckPoint;
	int checkpointHealth;

	float lastJump = 0;
	DirectionalAnimation walkAnimation;
	DirectionalAnimation swingAnimation;

	public Player(Game game, String id, int x, int y, int width, int height, int speed)
	{
		super(game, x, y, width, height, speed, 7, CreatureType.PLAYER, PathfinderAIType.NONE, InteractionAIType.NONE);
		this.id = id;
		this.speed = speed;
		lastCheckPoint = new Location(getGame(), getLocation().getX(), getLocation().getY());
		checkpointHealth = getHealth();

		loadImages();
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
						AudioInputStream walkSound = getGame().getCurrentLevel().getWalkSound();
						if (clip != null)
						{
							clip.stop();
						}
						else
						{
							clip = AudioSystem.getClip();
							clip.open(walkSound);
						}

						if (!clip.isActive())
						{
							walkSound.mark(20);
							walkSound.reset();


							clip.setMicrosecondPosition(0);

							if (getGame().getTicksAlive() % 21 == 0)
							{
								//clip.start();
							}
						}
					}
					catch(Exception exc)
					{
						exc.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void loadImages()
	{
		this.walkAnimation = new DirectionalAnimation(getGame(), AnimationType.PLAYER_WALK, getLocation());
		walkAnimation.loadImages(2);

		this.swingAnimation = new DirectionalAnimation(getGame(), AnimationType.PLAYER_SWING, getLocation());
		swingAnimation.loadImages(2);

		setWidth(walkAnimation.getImages().values().iterator().next().getWidth(null));
		setHeight(walkAnimation.getImages().values().iterator().next().getHeight(null));
	}

	/**
	 * Swings the creatures melee attack. I.E. sword swing.
	 */
	public void attack_melee()
	{
		if(System.nanoTime() - getLastSwing() > GameSettings.swingCooldown)
		{
			if(this instanceof Player)
			{
				((Player)this).swingAnimation.setCurrentAnimationStage(0);
			}
			// Mouse X and Y positions
			int mouseX = (int) getGame().getDisplay().getCanvas().getMousePosition().getX();
			int mouseY = (int) getGame().getDisplay().getCanvas().getMousePosition().getY();

			// Creature X and Y positions with game camera offset
			double midX = MathUtils.middle(getLocation().getX(), getWidth()) - getGame().getCurrentLevel().getGameCamera().getxOffset();
			double midY = MathUtils.middle(getLocation().getY(), getHeight()) - getGame().getCurrentLevel().getGameCamera().getyOffset();

			// Creature position as Vector2
			Vector2 crVec = getLocation().getVectorAsMiddleWithOffset(getWidth(), getHeight());

			// Sorts through all entities (Players, Creatures, Tiles)
			for(Entity entity : (ArrayList<Entity>) getGame().getCurrentLevel().getEntities().clone())
			{
				// Make sure it's a breakable tile
				if (!(entity instanceof Tile && !((Tile) entity).getBlockType().isBreakable()) || entity instanceof Creature)
				{
					// Checks that the entity is not this entity so it doesn't kill itsself.
					if (!entity.getUuid().toString().equalsIgnoreCase(getUuid().toString()))
					{
						// The specific entities Vector and X and Y position.
						Vector2 entVec = entity.getLocation().getVectorAsMiddleWithOffset(entity.getWidth(), entity.getHeight());
						double x = entVec.x;
						double y = entVec.y;

						// The angle of the mouse with this creature.
						float mouseAngle = new Vector2((float) (midX - mouseX), (float) (midY - mouseY)).angle();

						// The angle of the mouse with the entity.
						float enemyAngle = new Vector2((float) (midX - x), (float) (midY - y)).angle();

						// Checks if the entity is within 60 degrees of the swing radius
						boolean inField = (mouseAngle - enemyAngle < 90 && mouseAngle - enemyAngle > -90);

						float distance = crVec.dst(entVec);

						if (inField && distance < CreatureType.PLAYER.getSwingDistance())
						{
							entity.damage(1);
						}

					}
				}
			}

			lastSwing = System.nanoTime();
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
			
			if(!collides(false))
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

			if (collides(false) && !GameSettings.levelBuildMode)
			{
				getLocation().setX(tempX);
				xMove = 0;
			}

			getLocation().setY(getLocation().getY() + yMove);

			if (collides(false) && !GameSettings.levelBuildMode)
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
		setHealth(checkpointHealth);
		setDead(false);

		Level respawnLevel = new Level(getGame(), getGame().getCurrentLevel().getLevelId(), -1, -1, this);
		respawnLevel.loadAll(true, true, true, lastCheckPoint.getX(), lastCheckPoint.getY());

		getGame().getGraphicEffects().remove("0");
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
			float xDiff = getLocation().getAsCentered(getWidth(), getHeight()).getX() - getGame().getCurrentLevel().getGameCamera().getxOffset() - getGame().getMouseEventListener().getX();
			float yDiff = getLocation().getAsCentered(getWidth(), getHeight()).getY() - getGame().getCurrentLevel().getGameCamera().getyOffset() - getGame().getMouseEventListener().getY();

			direction = Math.abs(yDiff) > Math.abs(xDiff) ? yDiff < 0 ? Direction.SOUTH : Direction.NORTH : xDiff < 0 ? Direction.EAST : Direction.WEST;

			getGame().getKeyInputListener().setLastKeyPressed(EntityUtils.directionToKeypress(direction));
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
		getGame().getGraphicEffects().put("1", new MaskEffect(getGame(), Color.RED, 100, -1));
		getGame().getGraphicEffects().put("0",new WastedEffect(getGame(),false, "WASTED"));

		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					getGame().getCurrentLevel().getEntities().clear();
					sleep(1000);
					getGame().getGraphicEffects().put("2", new FadeEffect(getGame(), Color.BLACK, 2, false, false));
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

	public Location getLastCheckPoint()
	{
		return lastCheckPoint;
	}

	public void setLastCheckPoint(Location lastCheckPoint)
	{
		this.lastCheckPoint = lastCheckPoint;
	}

	public int getCheckpointHealth()
	{
		return checkpointHealth;
	}

	public void setCheckpointHealth(int checkpointHealth)
	{
		this.checkpointHealth = checkpointHealth;
	}
}
