package me.zm.apcsgame.ai.interactions;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.displays.animations.DirectionalAnimation;
import me.zm.apcsgame.displays.animations.OrderedAnimation;
import me.zm.apcsgame.entity.creature.Boss1Minion;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.entity.creature.CreatureType;
import me.zm.apcsgame.locations.Location;

import java.util.Random;

/**
 * Created by Ztowne13 on 5/26/2016.
 */
public class Boss1AI extends InteractionAI
{
    DirectionalAnimation walkAnim = null;
    OrderedAnimation swingAnim = null;

    HitNearAI hitNearAI;
    int phase = 0;

    public Boss1AI(Game game, Creature creature, float swingCharge, int swingDistance)
    {
        super(game, creature);

        this.hitNearAI = new HitNearAI(game, creature, swingCharge, swingDistance);

        Boss1Minion minion = (Boss1Minion) new Boss1Minion(game, "boss1 minion", -1, -1, -1, -1, true);
        walkAnim = minion.getMoveAnim();
        swingAnim = minion.getSwingAnim();
    }

    @Override
    public boolean run()
    {
        if(getEntity().getHealth() == getEntity().getMaxhealth() - 25 && phase == 0)
        {
            spawnMinionCount(1, 600);
            phase++;
        }

        if(getEntity().getHealth() == getEntity().getMaxhealth() - 50 && phase == 1)
        {
            spawnMinionCount(3, 600);
            phase++;
        }

        if(getEntity().getHealth() == getEntity().getMaxhealth() - 100 && phase == 2)
        {
            spawnMinionCount(5, 1000);
            phase++;
        }

        if(getEntity().getHealth() == getEntity().getMaxhealth() - 150 && phase == 3)
        {
            spawnMinionCount(8, 1500);
            phase++;
        }

        if(getEntity().getHealth() == getEntity().getMaxhealth() - 175 && phase == 4)
        {
            spawnMinionCount(7, 1500);
            phase++;
        }

        if(getEntity().getHealth() == getEntity().getMaxhealth() - 195 && phase == 5)
        {
            spawnMinionCount(5, 800);
            phase++;
        }
        return hitNearAI.run();
    }

    public void spawnMinionCount(int amnt, int distance)
    {
        Random r = new Random();
        Location cL = getEntity().getLocation().getAsCentered(getEntity().getWidth(), getEntity().getHeight());;

        for(int i = 0; i < amnt; i++)
        {
            int x = r.nextInt(600) - r.nextInt(600) + cL.getX();
            int y = r.nextInt(600) - r.nextInt(600) + cL.getY();

            Boss1Minion minion = (Boss1Minion) CreatureType.BOSS_MINION.spawn(getGame(), x, y, false);

            if(minion.collides(false))
            {
                i++;
                minion.destroy();
            }

            minion.setMoveAnim((DirectionalAnimation)walkAnim.clone());
            minion.getMoveAnim().setLocation(minion.getLocation());
            minion.setSwingAnim((OrderedAnimation)swingAnim.clone());
            minion.getSwingAnim().setLocation(minion.getLocation());
            minion.updateWidthHeight();
        }
    }
}
