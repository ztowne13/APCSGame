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
		super(game, x, y, width, height, 1);
	}
}
