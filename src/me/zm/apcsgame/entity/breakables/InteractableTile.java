package me.zm.apcsgame.entity.breakables;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.level.BlockType;

import java.awt.*;

/**
 * Created by ztowne13 on 5/1/16.
 */
public class InteractableTile extends Tile
{
	public InteractableTile(Game game, int x, int y, int width, int height, BlockType blockType)
	{
		super(game, x, y, width, height, blockType);

		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void draw(Graphics g)
	{

	}
}
