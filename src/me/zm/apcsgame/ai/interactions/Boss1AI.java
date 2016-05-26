package me.zm.apcsgame.ai.interactions;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.entity.creature.CreatureType;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.locations.Location;

import java.util.Random;

/**
 * Created by Ztowne13 on 5/26/2016.
 */
public class Boss1AI extends InteractionAI
{
    HitNearAI hitNearAI;

    public Boss1AI(Game game, Creature creature, float swingCharge, int swingDistance)
    {
        super(game, creature);

        this.hitNearAI = new HitNearAI(game, creature, swingCharge, swingDistance);
    }

    @Override
    public boolean run()
    {
        hitNearAI.run();

        //if(getEntity().getHealth() == getEntity().getMaxhealth() / 2)
        if(getEntity().getHealth() == getEntity().getMaxhealth() - 10)
        {
            Random r = new Random();
            for(int i = 0; i < 10; i++)
            {
                int x = r.nextInt(100);
                int y = r.nextInt(100);

                CreatureType.BOSS_MINION.spawn(getGame(), x, y);
            }
        }
        return false;
    }

}
