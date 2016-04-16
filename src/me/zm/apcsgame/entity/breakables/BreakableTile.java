package me.zm.apcsgame.entity.breakables;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.level.BlockType;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class BreakableTile extends Tile
{
	BlockType blockType;
	BufferedImage image;

	public BreakableTile(Game game, int x, int y, int width, int height, BlockType blockType)
	{
		super(game, x, y, width, height);
		this.blockType = blockType;

		this.image = blockType.getImage();

		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void draw(Graphics graphics)
	{
		graphics.drawImage(image, getX() - (int)getGame().getGameCamera().getxOffset(), getY() - (int)getGame().getGameCamera().getyOffset(), null);
	}

	public void breakItem()
	{

	}

	public void handleBreakBy(Player player)
	{

	}

	@Override
	public boolean collidesWith(Entity ent)
	{
		return collidesWithOnAxis(ent, true) && collidesWithOnAxis(ent, false);
	}

	@Override
	public boolean collidesWithOnAxis(Entity ent, boolean x)
	{
		int xOffset = - (int)getGame().getGameCamera().getxOffset();
		int yOffset = - (int)getGame().getGameCamera().getyOffset();
		// Entity coordinates
		int eBottomY = ent.getY() + ent.getHeight() + yOffset;

		int eLeftX = ent.getX() + xOffset;
		int eRightX = ent.getX() + ent.getWidth() + xOffset;

		// Block coordinates
		int bBottomY = getY() + getHeight() + yOffset;
		int bTopY = getY() + yOffset;

		int bLeftX = getX() + xOffset;
		int bRightX = getX() + getWidth() + xOffset;

		return x ? (eLeftX > bLeftX && eLeftX < bRightX) || (eRightX <  bRightX && eRightX > bLeftX) : eBottomY < bBottomY && eBottomY > bTopY;
	}
}
