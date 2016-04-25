package me.zm.apcsgame.input;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by ztowne13 on 4/24/16.
 */
public class MouseEventListener implements MouseListener
{
	Game game;

	public MouseEventListener(Game game)
	{
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

		for(Entity entity : game.getEntities())
		{

			if(entity instanceof Player)
			{

				((Player)entity).swing();
				break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}
}
