package me.zm.apcsgame.ai.pathfinding;

import javafx.scene.shape.Path;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.MathUtils;

/**
 * Created by ztowne13 on 5/27/16.
 */
public class BallPF extends PathfinderAI
{
    Location targetLoc = null;
    float startChargeTime = 0, ballCarge;
    boolean runningPath;

    public BallPF(Game game, Creature entity, Location pathFindingGoal, int speed, float ballCharge) {
        super(game, entity, pathFindingGoal, 0, 0, speed);
        this.ballCarge = ballCharge;
    }


    @Override
    public boolean run()
    {
        if(!(pathFindingGoal == null))
        {
            Location toLoc = pathFindingGoal.getAsCentered(getPathFindingWidth(), getPathFindingHeight());
            Location cL = getEntity().getLocation();
            Location cLCentered = cL.getAsCentered(getEntity().getWidth(), getEntity().getHeight());
            int tempX = cL.getX();
            int tempY = cL.getY();

            double angle = Math.toDegrees(Math.atan2(toLoc.getY() - cLCentered.getY(), toLoc.getX() - cLCentered.getX()));

            double distance = MathUtils.distance(toLoc, cLCentered);

            if(!runningPath && distance < getEntity().getCreatureType().getVisibleRange() && canRunPath(toLoc, cLCentered, tempX, tempY, distance, angle))
            {
                startChargeTime = System.nanoTime();
                targetLoc = toLoc;
                runningPath = true;
            }
            else if(runningPath && System.nanoTime() - startChargeTime > ballCarge * 1000000000)
            {
                double angleToTargetLoc = Math.toDegrees(Math.atan2(targetLoc.getY() - cLCentered.getY(), targetLoc.getX() - cLCentered.getX()));

                double sin = Math.sin(Math.toRadians(angle)) * getSpeed();
                double cos = Math.cos(Math.toRadians(angle)) * getSpeed();

                cL.setY(tempY + (int) sin);
                cL.setX(tempX + (int) cos);

                if (getEntity().collides(false))
                {
                    runningPath = false;
                    targetLoc = null;
                }
            }
        }
        return true;
    }

    public boolean canRunPath(Location toLoc, Location clCentered, int tempX, int tempY, double distance, double angle)
    {
        for(int i = 0; i < distance; i++)
        {
            double sin = Math.sin(Math.toRadians(angle)) * i;
            double cos = Math.cos(Math.toRadians(angle)) * i;

            getEntity().getLocation().setX(tempX + (int)cos);
            getEntity().getLocation().setY(tempY + (int)sin);

            if(getEntity().collides(false))
            {
                getEntity().getLocation().setX(tempX);
                getEntity().getLocation().setY(tempY);
                return false;
            }
        }

        getEntity().getLocation().setX(tempX);
        getEntity().getLocation().setY(tempY);
        return true;
    }
}
