package me.zm.apcsgame.input;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.level.Point;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/24/16.
 */
public class MouseEventListener implements MouseListener, MouseMotionListener
{
	ArrayList<Point> points = new ArrayList<Point>();

	int x = 0, y = 0;

	Game game;

	public MouseEventListener(Game game)
	{
		this.game = game;
	}

	/**
	 * Listens for mouse clicks and performs mouse click actions.
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{

		for(Entity entity : game.getEntities())
		{

			if(entity instanceof Player)
			{

				((Player)entity).attack_melee();
				break;
			}
		}

		if(GameSettings.levelBuildMode)
		{
			if(e.isShiftDown())
			{
				if(e.isAltDown())
				{
					System.out.println("---------");
					for (Point p : points)
					{
						System.out.println(p);
					}
				}
				else
				{
					points.remove(points.size()-1);
				}
			}
			else
			{
				points.add(new Point((int) (e.getX() + game.getGameCamera().getxOffset()), (int) (e.getY() + game.getGameCamera().getyOffset())));
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

	@Override
	public void mouseDragged(MouseEvent e)
	{

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
	}

	public ArrayList<Point> getPoints()
	{
		return points;
	}

	public void setPoints(ArrayList<Point> points)
	{
		this.points = points;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}
