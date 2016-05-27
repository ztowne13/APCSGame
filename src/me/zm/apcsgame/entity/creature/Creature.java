package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.ai.MobAI;
import me.zm.apcsgame.ai.interactions.Boss1AI;
import me.zm.apcsgame.ai.interactions.HitNearAI;
import me.zm.apcsgame.ai.interactions.InteractionAI;
import me.zm.apcsgame.ai.interactions.InteractionAIType;
import me.zm.apcsgame.ai.pathfinding.BallPF;
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
				pathfindingAI = new WalkLinearPF(game, this, null, creatureType.getMinimumRange(), speed);
				break;
			case BALL:
				pathfindingAI = new BallPF(game, this, null, speed, 6);
		}

		switch(interactionAIType)
		{
			case HIT_NEAR:
				if(this instanceof Crow)
				{
					interactionAI = new HitNearAI(game, this, .8F, creatureType.getSwingDistance());
				}
				else if(this instanceof Ball)
				{
					interactionAI = new HitNearAI(game, this, .5F, creatureType.getSwingDistance());
				}
				else
				{
					interactionAI = new HitNearAI(game, this, 1.75F, creatureType.getSwingDistance());
				}
				break;
			case BOSS_AI:
				interactionAI = new Boss1AI(game, this, 3, 150);
				break;

		}
	}

	/**
	 * Checks to see whether or not the entity should move
	 */
	public abstract void checkMove();

	public abstract boolean canMove();

	public abstract void loadImages();

	public boolean collides(boolean includeOthers)
	{
		boolean collidesWithTile = false;

		for(Entity tile : (ArrayList<Entity>) getGame().getCurrentLevel().getEntities().clone())
		{
			if(tile instanceof Tile || includeOthers ? (!(tile instanceof  Player)) : false)
			{
				if (tile instanceof Tile ? ((Tile) tile).collidesWith(getHitbox()) : MathUtils.distance(tile.getLocation(), getLocation()) < getHitbox().getWidth()/4)
				{
					if(!tile.getUuid().equals(getUuid()))
					{
						collidesWithTile = true;
						break;
					}
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
