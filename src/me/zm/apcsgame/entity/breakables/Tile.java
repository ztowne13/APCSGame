package me.zm.apcsgame.entity.breakables;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;

/**
 * Created by ztowne13 on 4/15/16.
 */
public abstract class Tile extends Entity
{
	public Tile(Game game, int x, int y, int width, int height)
	{
		super(game, x, y, width, height);
	}

	/**
	 * Checks if the tile collides with an entity
	 * @param ent The entity it may be colliding with
	 * @return True if it collides, false id it doesn't
	 */
	public abstract boolean collidesWith(Entity ent);

	/**
	 * Checks if the tile collides with an entity on a specific axis.
	 * @param ent The entity it may be colliding with
	 * @param x True checks if it collides on the x axis, false to check the y axis.
	 * @return If it collides on that axis, only.
	 */
	public abstract boolean collidesWithOnAxis(Entity ent, boolean x);


}
