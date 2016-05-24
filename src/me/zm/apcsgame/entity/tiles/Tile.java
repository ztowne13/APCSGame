package me.zm.apcsgame.entity.tiles;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.level.BlockType;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/15/16.
 *
 * Square objects that have blocks.hitboxes.
 */
public abstract class Tile extends Entity
{
	BufferedImage image;
	BlockType blockType;
	boolean flipped;

	public Tile(Game game, int x, int y, int width, int height, BlockType blockType, boolean flipped)
	{
		super(game, blockType.name(), x, y, width, height, 1);
		this.blockType = blockType;

		this.image = blockType.getImage();
		this.flipped = flipped;

		setWidth(image.getWidth());
		setHeight(image.getHeight());
	}

	@Override
	public Rectangle getHitbox()
	{
		Rectangle cHB = blockType.getCustomHitbox();
		return new Rectangle((int)(getLocation().getX() + (isFlipped() ? (getWidth()-(cHB.getX()+blockType.getCustomHitbox().getWidth())) : cHB.getX()) - getGame().getCurrentLevel().getGameCamera().getxOffset()), getLocation().getY() + (int)cHB.getY() - (int)getGame().getCurrentLevel().getGameCamera().getyOffset(), (int)cHB.getWidth(), (int)cHB.getHeight());
	}

	/**
	 * Checks if the tile collides with an entity but allows leeway above and below the entity
	 * @param otherHitBox The other hit box it may be colliding with
	 * @return True if it collides according to tile collision rules, false if it doesn't
	 */
	@Override
	public boolean collidesWith(Rectangle otherHitBox)
	{
		//Rectangle realOtherHitbox = new Rectangle((int)otherHitBox.getX(), (int)otherHitBox.getY(), (int)otherHitBox.getWidth(), (int)otherHitBox.getHeight()/4);
		//return getHitbox().intersects(otherHitBox);
		return collidesWithOnAxis(otherHitBox, true) && collidesWithOnAxis(otherHitBox, false);
	}

	/**
	 * Checks if other hit box collides with this tile on a certain axis.
	 * @param oHB The other hitbox to check against
	 * @param x True, check the x axis, false, check the y axis
	 * @return True = it collides on the specified axis. False = It doesn't collide on the axis
	**/
	@Deprecated
	public boolean collidesWithOnAxis(Rectangle oHB, boolean x)
	{
		int xOffset = - (int)getGame().getCurrentLevel().getGameCamera().getxOffset();
		int yOffset = - (int)getGame().getCurrentLevel().getGameCamera().getyOffset();
		// Entity coordinates
		int eBottomY = (int) (oHB.getY() + oHB.getHeight() + yOffset - GameSettings.xShift);

		int eLeftX = (int) (oHB.getX() + xOffset);
		int eRightX = (int) (oHB.getX() + oHB.getWidth() + xOffset);

		// Block coordinates
		int bBottomY = (int)(getHitbox().getY() + getHitbox().getHeight() + yOffset);
		int bTopY = (int)(getHitbox().getY() + yOffset);

		int bLeftX = (int)(getHitbox().getX() + xOffset);
		int bRightX = (int)(getHitbox().getX() + getHitbox().getWidth() + xOffset);

		return x ? (eLeftX > bLeftX && eLeftX < bRightX) || (eRightX <  bRightX && eRightX > bLeftX) : eBottomY < bBottomY && eBottomY > bTopY;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public BlockType getBlockType()
	{
		return blockType;
	}

	public void setBlockType(BlockType blockType)
	{
		this.blockType = blockType;
	}

	public boolean isFlipped()
	{
		return flipped;
	}

	public void setFlipped(boolean flipped)
	{
		this.flipped = flipped;
	}
}
