package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.ai.interactions.InteractionAIType;
import me.zm.apcsgame.ai.pathfinding.PathfinderAIType;
import me.zm.apcsgame.displays.animations.AnimationType;
import me.zm.apcsgame.displays.animations.DirectionalAnimation;
import me.zm.apcsgame.displays.animations.OrderedAnimation;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.MathUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by APCS on 5/25/2016.
 */
public class Boss1 extends Creature
{
    private final float swingCooldown = 1000000000;

    Location lastLoc;
    DirectionalAnimation moveAnim;
    OrderedAnimation swingAnim;
    Direction lastMoveDirection = Direction.EAST;

    public Boss1(Game game, String id, int x, int y, int width, int height, int speed)
    {
        super(game, x, y, width, height, 10, CreatureType.BOSS_1, PathfinderAIType.WALK_STRAIGHT, InteractionAIType.HIT_NEAR);

        ArrayList<String> excludedDirections = new ArrayList<String>();
        excludedDirections.add("NORTH");
        excludedDirections.add("SOUTH");
        moveAnim = new DirectionalAnimation(game, AnimationType.BOSS_WALK, getLocation(), excludedDirections);
        moveAnim.loadImages(.3);

        swingAnim = new OrderedAnimation(game, AnimationType.BOSS_ATTACK, getLocation());
        swingAnim.loadImages(.3);

        setWidth(moveAnim.getImages().values().iterator().next().getWidth(null));
        setHeight(moveAnim.getImages().values().iterator().next().getHeight(null));
    }

    @Override
    public void checkMove() {

    }

    @Override
    public void tick()
    {
        if(getPathfindingAI().getPathFindingGoal() == null)
        {
            Player p = getGame().getCurrentLevel().getPlayer();
            if (MathUtils.distance(p.getLocation(), getLocation()) < getCreatureType().getVisibleRange())
            {
                getPathfindingAI().setPathFindingGoal(p.getLocation());
                getPathfindingAI().setPathFindingWidth(p.getWidth());
                getPathfindingAI().setPathFindingHeight(p.getHeight());
            }
        }

        lastLoc = getLocation().clone();

        find_path();
        if(interact())
        {
            lastSwing = System.nanoTime();
        }

        lastMoveDirection = getLocation().getX() - lastLoc.getX() < 0 ? Direction.WEST : Direction.EAST;

        if(getGame().getTicksAlive() % getTickDelay() == 0)
        {
            moveAnim.tick();
        }
    }

    @Override
    public void draw(Graphics g) {
        if(canMove())
        {
            moveAnim.render(false, lastMoveDirection, g);
        }
        else
        {
            if (getGame().getTicksAlive() % 4 == 0)
            {
                swingAnim.tick();
                swingAnim.render(g);
            }
            else
            {
                swingAnim.render(g);
            }
        }
    }

    @Override
    public boolean canMove()
    {
        return System.nanoTime() - lastSwing > swingCooldown;
    }

    public Direction getLastMoveDirection()
    {
        return lastMoveDirection;
    }

    public void setLastMoveDirection(Direction lastMoveDirection)
    {
        this.lastMoveDirection = lastMoveDirection;
    }
}
