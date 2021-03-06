package me.zm.apcsgame.entity.tiles;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.level.BlockType;

import java.awt.*;

/**
 * Created by ztowne13 on 4/11/16.
 *
 * Tiles that can be broken when the player swings in their direction
 */
public class StaticTile extends Tile
{

	public StaticTile(Game game, int x, int y, int width, int height, BlockType blockType, boolean flipped)
	{
		super(game, x, y, width, height, blockType, flipped);
		this.blockType = blockType;
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void draw(Graphics graphics)
	{
		if (isFlipped())
		{
			graphics.drawImage(image, getLocation().getX() - (int) getGame().getCurrentLevel().getGameCamera().getxOffset() + getWidth(), getLocation().getY() - (int) getGame().getCurrentLevel().getGameCamera().getyOffset(), -getWidth(), getHeight(), null);
		}
		else
		{
			graphics.drawImage(image, getLocation().getX() - (int) getGame().getCurrentLevel().getGameCamera().getxOffset(), getLocation().getY() - (int) getGame().getCurrentLevel().getGameCamera().getyOffset(), null);
		}
	}
}
