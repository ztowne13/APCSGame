package me.zm.apcsgame.entity.creature;

import com.badlogic.gdx.math.Vector2;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.ai.MobAI;
import me.zm.apcsgame.ai.interactions.Boss1AI;
import me.zm.apcsgame.ai.interactions.HitNearAI;
import me.zm.apcsgame.ai.interactions.InteractionAI;
import me.zm.apcsgame.ai.interactions.InteractionAIType;
import me.zm.apcsgame.ai.pathfinding.PathfinderAI;
import me.zm.apcsgame.ai.pathfinding.PathfinderAIType;
import me.zm.apcsgame.ai.pathfinding.WalkLinearPF;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.tiles.Tile;
import me.zm.apcsgame.utils.MathUtils;

import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Creature extends Entity implements MobAI
{
	CreatureType creatureType;

	int tickDelay;
	float lastSwing = 0;
	PathfinderAI pathfindingAI;
	InteractionAI interactionAI;

	public Creature(Game game, int x, int y, int width, int height, int speed, int tickDelay, CreatureType creatureType, PathfinderAIType pathfindingAIType, InteractionAIType interactionAIType)
	{
		super(game, creatureType.name(), x, y, width, height, creatureType.getDefaultHealth(), true);
		this.tickDelay = tickDelay;
		this.creatureType = creatureType;

		switch(pathfindingAIType)
		{
			case WALK_STRAIGHT:
				pathfindingAI = new WalkLinearPF(game, this, null, 60, speed);
				break;
		}

		switch(interactionAIType)
		{
			case HIT_NEAR:
				interactionAI = new Boss1AI(game, this, 3, 150);
				break;
		}
	}

	/**
	 * Checks to see whether or not the entity should move
	 */
	public abstract void checkMove();

	public abstract boolean canMove();

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

						if (inField && distance < 160)
						{
							entity.damage(1);
						}

					}
				}
			}

			lastSwing = System.nanoTime();
		}
	}

	public boolean collides()
	{
		boolean collidesWithTile = false;

		for(Entity tile : (ArrayList<Entity>) getGame().getCurrentLevel().getEntities().clone())
		{
			if(tile instanceof Tile)
			{
				if (((Tile)tile).collidesWith(getHitbox()))
				{
					collidesWithTile = true;
					break;
				}
			}
		}

		return getGame().getCurrentLevel().isEntityOutsideBounds(this) || collidesWithTile;
	}

	public boolean find_path()
	{
		return pathfindingAI.run();
	}

	public boolean interact()
	{
		if(interactionAI.run())
		{
			setLastSwing(System.nanoTime());
			return true;
		}
		return false;
	}


	public CreatureType getCreatureType()
	{
		return creatureType;
	}

	public void setCreatureType(CreatureType creatureType)
	{
		this.creatureType = creatureType;
	}

	public float getLastSwing()
	{
		return lastSwing;
	}

	public void setLastSwing(float lastSwing)
	{
		this.lastSwing = lastSwing;
	}

	public int getTickDelay()
	{
		return tickDelay;
	}

	public void setTickDelay(int tickDelay)
	{
		this.tickDelay = tickDelay;
	}

	public PathfinderAI getPathfindingAI()
	{
		return pathfindingAI;
	}

	public void setPathfindingAI(PathfinderAI pathfindingAI)
	{
		this.pathfindingAI = pathfindingAI;
	}

	public InteractionAI getInteractionAI()
	{
		return interactionAI;
	}

	public void setInteractionAI(InteractionAI interactionAI)
	{
		this.interactionAI = interactionAI;
	}


}
