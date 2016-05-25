package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.displays.animations.AnimationType;
import me.zm.apcsgame.displays.animations.OrderedAnimation;

import java.awt.*;

/**
 * Created by APCS on 5/25/2016.
 */
public class Boss1 extends Creature
{
    OrderedAnimation moveAnim;

    public Boss1(Game game, String id, int x, int y, int width, int height, int speed)
    {
        super(game, x, y, width, height, CreatureType.BOSS_1.getDefaultHealth(), CreatureType.BOSS_1);

        moveAnim = new OrderedAnimation(game, AnimationType.BOSS_WALK, getLocation());
        moveAnim.loadImages();
    }

    @Override
    public void checkMove() {

    }

    @Override
    public void tick()
    {
        moveAnim.tick();
        update_pathfinder();
    }

    @Override
    public void draw(Graphics g) {
        moveAnim.render(g);
    }
}
