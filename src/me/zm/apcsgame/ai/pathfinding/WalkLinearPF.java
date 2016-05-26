package me.zm.apcsgame.ai.pathfinding;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.MathUtils;

/**
 * Created by ztowne13 on 5/25/16.
 */
public class WalkLinearPF extends PathfinderAI
{
	Location lastLoc = null;
	int minDistance;

	public WalkLinearPF(Game game, Creature entity, Location pathFindingGoal, int minDistance, int speed)
	{
		super(game, entity, pathFindingGoal, 0, 0, speed);
		lastLoc = entity.getLocation().clone();
		this.minDistance = minDistance;
	}

	@Override
	public boolean run()
	{
		if(!(pathFindingGoal == null)) {
			Location toLoc = pathFindingGoal.getAsCentered(getPathFindingWidth(), getPathFindingHeight());
			Location cL = getEntity().getLocation();
			Location cLCentered = cL.getAsCentered(getEntity().getWidth(), getEntity().getHeight());
			int tempX = cL.getX();
			int tempY = cL.getY();

			double angle = Math.toDegrees(Math.atan2(toLoc.getY() - cLCentered.getY(), toLoc.getX() - cLCentered.getX()));

			int addPlus = 0;
			boolean success = false;

			if(getEntity().canMove() && MathUtils.distance(toLoc, cLCentered) > minDistance)
			{
				while (true)
				{
					double sin = Math.sin(Math.toRadians(angle + addPlus)) * getSpeed();
					double cos = Math.cos(Math.toRadians(angle + addPlus)) * getSpeed();

					cL.setY(tempY + (int) sin);
					cL.setX(tempX + (int) cos);

					if (!getEntity().collides())
					{
						success = true;
						break;
					}

					sin = Math.sin(Math.toRadians(angle - addPlus)) * getSpeed();
					cos = Math.cos(Math.toRadians(angle - addPlus)) * getSpeed();

					cL.setY(tempY + (int) sin);
					cL.setX(tempX + (int) cos);

					if (!getEntity().collides())
					{
						success = true;
						break;
					}

					addPlus++;

					if (addPlus >= 360)
					{
						break;
					}
				}

				if (!success)
				{
					cL.setX(tempX);
					cL.setY(tempY);
				}

				lastLoc = cL.clone();
			}
		}
		return true;
	}
}
