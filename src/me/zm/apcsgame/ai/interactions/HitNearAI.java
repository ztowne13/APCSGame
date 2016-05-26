package me.zm.apcsgame.ai.interactions;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.MathUtils;

/**
 * Created by ztowne13 on 5/25/16.
 */
public class HitNearAI extends InteractionAI
{
	float swingCharge, chargeCount = -1;
	int swingDistance;

	public HitNearAI(Game game, Creature creature, float swingCharge, int swingDistance)
	{
		super(game, creature);
		this.swingCharge = swingCharge * 60;
		this.swingDistance = swingDistance;
	}

	@Override
	public boolean run()
	{
		Player p = getGame().getCurrentLevel().getPlayer();
		Location cL = getEntity().getLocation();

		if(chargeCount != -1)
		{
			chargeCount++;
			if (chargeCount > swingCharge)
			{
				if (isInRange(p, cL))
				{
					p.damage(getEntity().getCreatureType().getDamageAmount());
					chargeCount = 0;
				}
				else
				{
					chargeCount = -1;
				}
				return true;
			}
		}
		else
		{
			if (isInRange(p, cL))
			{
				chargeCount = 0;
			}
		}

		return false;
	}

	public boolean isInRange(Player p, Location cL)
	{
		//System.out.println(MathUtils.distance(p.getLocation().getAsCentered(p.getWidth(), p.getHeight()), cL.getAsCentered(getEntity().getWidth(), getEntity().getHeight())));
		return (MathUtils.distance(p.getLocation().getAsCentered(p.getWidth(), p.getHeight()), cL.getAsCentered(getEntity().getWidth(), getEntity().getHeight())) < swingDistance);
	}
}
