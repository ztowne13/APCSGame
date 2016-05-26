package me.zm.apcsgame.ai.pathfinding;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.ai.ActionAI;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.locations.Location;

/**
 * Created by ztowne13 on 5/25/16.
 */
public abstract class PathfinderAI extends ActionAI
{
	Location pathFindingGoal;
	int pathFindingWidth, pathFindingHeight, speed;

	public PathfinderAI(Game game, Creature entity, Location pathFindingGoal, int pathFindingWidth, int pathFindingHeigt, int speed)
	{
		super(game, entity);

		this.pathFindingGoal = pathFindingGoal;
		this.pathFindingWidth = pathFindingWidth;
		this.pathFindingHeight = pathFindingHeigt;
		this.speed = speed;
	}

	public Location getPathFindingGoal()
	{
		return pathFindingGoal;
	}

	public void setPathFindingGoal(Location pathFindingGoal)
	{
		this.pathFindingGoal = pathFindingGoal;
	}

	public int getPathFindingWidth()
	{
		return pathFindingWidth;
	}

	public void setPathFindingWidth(int pathFindingWidth)
	{
		this.pathFindingWidth = pathFindingWidth;
	}

	public int getPathFindingHeight()
	{
		return pathFindingHeight;
	}

	public void setPathFindingHeight(int pathFindingHeight)
	{
		this.pathFindingHeight = pathFindingHeight;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int getSpeed()
	{
		return speed;
	}
}
