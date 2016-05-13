package me.zm.apcsgame.displays;

import me.zm.apcsgame.Game;

import java.awt.*;

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
		graphics.fillRect(5, 5, game.getPlayer().getHealth() * 20, 25);

		graphics.setColor(Color.WHITE);
		graphics.drawRect(5, 5, 200, 25);
	}
}
