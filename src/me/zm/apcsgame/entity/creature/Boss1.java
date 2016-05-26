package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.displays.animations.AnimationType;
import me.zm.apcsgame.displays.animations.OrderedAnimation;
import me.zm.apcsgame.utils.MathUtils;

import java.awt.*;

/**
 * Created by APCS on 5/25/2016.
 */
public class Boss1 extends Creature
{
    OrderedAnimation moveAnim;

    public Boss1(Game game, String id, int x, int y, int width, int height, int speed)
    {
        super(game, x, y, width, height, 10, CreatureType.BOSS_1.getDefaultHealth(), CreatureType.BOSS_1);

        moveAnim = new OrderedAnimation(game, AnimationType.BOSS_WALK, getLocation());
        moveAnim.loadImages(.3);
        setWidth(moveAnim.getImages().values().iterator().next().getWidth(null));
        setHeight(moveAnim.getImages().values().iterator().next().getHeight(null));
    }

    @Override
    public void checkMove() {

    }

    @Override
    public void tick()
    {
        if(getPathFinderLoc() == null)
        {
            Player p = getGame().getCurrentLevel().getPlayer();
            if (MathUtils.distance(p.getLocation(), getLocation()) < getCreatureType().getVisibleRange())
            {
                setPathFinderLoc(p.getLocation());
            }
        }

        update_pathfinder();

        if(getGame().getTicksAlive() % getTickDelay() == 0)
        {
            moveAnim.tick();
        }
    }

    @Override
    public void draw(Graphics g) {
        moveAnim.render(g);
    }


}
