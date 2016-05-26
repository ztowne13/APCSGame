package me.zm.apcsgame.displays.menus;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Boss1;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.utils.MathUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/15/16.
 */
public class HUD
{
	Game game;

	public HUD(Game game)
	{
		this.game = game;
	}

	public void drawHealth(Graphics graphics)
	{
		//health bar
		graphics.setColor(Color.GRAY);
		graphics.fillRect(5, 5, 200, 25);

		graphics.setColor(Color.GREEN);
		graphics.fillRect(5, 5, game.getCurrentLevel().getPlayer().getHealth() * 20, 25);

		graphics.setColor(Color.WHITE);
		graphics.drawRect(5, 5, 200, 25);

	}

	public void drawBossHealth(Graphics graphics)
	{
		Player p = game.getCurrentLevel().getPlayer();
		for(Entity ent : (ArrayList<Entity>) game.getCurrentLevel().getEntities().clone())
		{
			if(ent instanceof Boss1)
			{
				if (MathUtils.distance(p.getLocation(), ent.getLocation()) < ((Creature)ent).getCreatureType().getVisibleRange())
				{
					Canvas canvas = game.getDisplay().getCanvas();
					graphics.setColor(Color.GRAY);
					graphics.fillRect(50, canvas.getHeight() - 50, canvas.getWidth() - 100, 25);

					//300 health
					graphics.setColor(Color.RED);
					double mult =  (double) ent.getMaxhealth() / ((double)canvas.getWidth() - 100);
					graphics.fillRect(50, canvas.getHeight() - 50, (int)(ent.getHealth() / mult), 25);

					graphics.setColor(Color.WHITE);
					graphics.drawRect(50, canvas.getHeight() - 50, canvas.getWidth() - 100, 25);
				}
			}
		}
	}
}
