package me.zm.apcsgame.entity.breakables;

import me.zm.apcsgame.Game;
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
		graphics.drawImage(image, getLocation().getX() - (int)getGame().getGameCamera().getxOffset(), getLocation().getY() - (int)getGame().getGameCamera().getyOffset(), null);
	}

	public void breakItem()
	{

	}

	public void handleBreakBy(Player player)
	{

	}

	/*
	*
	* Old code that was used for prior hit detection
	*
	public boolean collidesWith(Entity ent)
	{
		return collidesWithOnAxis(ent, true) && collidesWithOnAxis(ent, false);
	}

	public boolean collidesWithOnAxis(Entity ent, boolean x)
	{
		int xOffset = - (int)getGame().getGameCamera().getxOffset();
		int yOffset = - (int)getGame().getGameCamera().getyOffset();
		// Entity coordinates
		int eBottomY = ent.getLocation().getY() + ent.getHeight() + yOffset - GameSettings.xShift;

		int eLeftX = ent.getLocation().getX() + xOffset;
		int eRightX = ent.getLocation().getX() + ent.getWidth() + xOffset;

		// Block coordinates
		int bBottomY = getLocation().getY() + getHeight() + yOffset;
		int bTopY = getLocation().getY() + yOffset;

		int bLeftX = getLocation().getX() + xOffset;
		int bRightX = getLocation().getX() + getWidth() + xOffset;

		return x ? (eLeftX > bLeftX && eLeftX < bRightX) || (eRightX <  bRightX && eRightX > bLeftX) : eBottomY < bBottomY && eBottomY > bTopY;
	}*/
}
