package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.level.Point;
import me.zm.apcsgame.utils.MathUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Creature extends Entity
{
	CreatureType creatureType;

	public Creature(Game game, int x, int y, int width, int height, int maxhealth, CreatureType creatureType)
	{
		super(game, x, y, width, height, maxhealth);
		this.creatureType = creatureType;
	}

	public abstract void checkMove();

	public CreatureType getCreatureType()
	{
		return creatureType;
	}

	public void setCreatureType(CreatureType creatureType)
	{
		this.creatureType = creatureType;
	}

	public void swing()
	{
		int mouseX = (int) getGame().getDisplay().getCanvas().getMousePosition().getX();
		int mouseY = (int) getGame().getDisplay().getCanvas().getMousePosition().getY();

		int midX = (int) (MathUtils.middle(getLocation().getX(), getWidth()) - getGame().getGameCamera().getxOffset());
		int midY = (int) (MathUtils.middle(getLocation().getY(), getHeight()) - getGame().getGameCamera().getyOffset());

		Point origin = new Point(midX, midY);

		int angle = (int)MathUtils.calcRotationAngleInDegrees(origin, new Point(mouseX, mouseY));

		int[] xPoints = new int[]{midX, MathUtils.getPointOnCircumfernce(20, angle - 30, origin).x, MathUtils.getPointOnCircumfernce(20, angle + 30, origin).y};
		int[] yPoints = new int[]{midY, MathUtils.getPointOnCircumfernce(20, angle - 30, origin).y, MathUtils.getPointOnCircumfernce(20, angle + 30, origin).y};

		Polygon swingLocation = new Polygon(xPoints, yPoints, 3);

		for(Entity entity : (ArrayList<Entity>) getGame().getEntities().clone())
		{
			if(!(entity == this || entity instanceof Player) && swingLocation.contains(entity.getLocation().getX(), entity.getLocation().getY()));
			{
				entity.damage(1);

			}
		}
	}
}
