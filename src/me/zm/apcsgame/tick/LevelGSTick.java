package me.zm.apcsgame.tick;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.displays.effects.GraphicEffect;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ztowne13 on 5/16/16.
 */
public class LevelGSTick extends GameStateTick
{
	public LevelGSTick(Game game)
	{
		super(game);
	}

	@Override
	public void tick()
	{
		Set<String> clonedList = new HashSet<String>();
		clonedList.addAll(game.getGraphicEffects().keySet());
		for(String graphicEffect : clonedList)
		{
			GraphicEffect effect = game.getGraphicEffects().get(graphicEffect);
			if(!effect.tick())
			{
				game.getGraphicEffects().remove(graphicEffect);
			}
		}

		game.getCurrentLevel().tick();
	}

	@Override
	public void draw(Graphics graphics)
	{
		game.getCurrentLevel().render(graphics);
		game.getCurrentLevel().renderOverlay(graphics);

		for(GraphicEffect graphicEffect : game.getGraphicEffects().values())
		{
			graphicEffect.draw(graphics);
		}
	}
}
