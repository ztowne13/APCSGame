package me.zm.apcsgame.level;

import me.zm.apcsgame.entity.tiles.InteractType;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/15/16.
 *
 * All of the tiles in the game
 */
public enum BlockType
{
	// (name of file, broken?, custom hitbox, x1, y1, x2, y2)
	EMPTY_SQUARE_1("Square1",false,true, 0, 0, 100, 100),

	GRASS_1("Grass1", true, false),

	TREE_1("Tree1", false, true, 38, 332, 152, 373),

	TREE_2("Tree2", false, true, 52, 251, 187, 292),

	DARK_TREE_1("Dark-Tree1", false, true, 117, 310, 213, 341),

	DARK_TREE_2("Dark-Tree2", false, true, 107, 310, 150, 351),

	/*TREE_1("Tree1", false, true, 38, 332, 152, 393),

	TREE_2("Tree2", false, true, 52, 251, 187, 300),

	DARK_TREE_1("Dark-Tree1", false, true, 117, 310, 213, 345),

	DARK_TREE_2("Dark-Tree2", false, true, 107, 310, 150, 350),*/

	PILLAR_1("Pillar1", false, true, 0, 180, 70, 242);

	String fileName;
	boolean breakable, hasCustomHitbox;
	InteractType interactType;
	Rectangle customHitbox;

	/**
	 * The block's constructor
	 * @param fileName The name of the block's image. (May later be changed to support animations)
	 * @param breakable True = the block is breakable. False = not breakable.
	 */
	BlockType(String fileName, boolean breakable, boolean customHitbox)
	{
		this(fileName, breakable, InteractType.NONE, customHitbox);
	}

	/**
	 * @param interactType If and how the block can be interacted with
	 */
	BlockType(String fileName, boolean breakable, InteractType interactType, boolean hasCustomHitbox)
	{
		this(fileName, breakable, interactType, hasCustomHitbox, 0, 0, 0, 0);
	}

	BlockType(String fileName, boolean breakable, boolean hasCustomHitbox, int x, int y, int x2, int y2)
	{
		this(fileName, breakable, InteractType.NONE, hasCustomHitbox, x, y, x2, y2);
	}

	BlockType(String fileName, boolean breakable, InteractType interactType, boolean hasCustomHitbox, int x, int y, int x2, int y2)
	{
		this.fileName = fileName;
		this.breakable = breakable;
		this.interactType = interactType;
		this.hasCustomHitbox = hasCustomHitbox;
		this.customHitbox = new Rectangle(x, y, x2-x, y2-y);
	}

	public BufferedImage getImage()
	{
		return FileUtils.loadImage("blocks/" + fileName + ".png");
	}

	public boolean isBreakable()
	{
		return breakable;
	}

	public void setBreakable(boolean breakable)
	{
		this.breakable = breakable;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public InteractType getInteractType()
	{
		return interactType;
	}

	public void setInteractType(InteractType interactType)
	{
		this.interactType = interactType;
	}

	public boolean isHasCustomHitbox()
	{
		return hasCustomHitbox;
	}

	public void setHasCustomHitbox(boolean hasCustomHitbox)
	{
		this.hasCustomHitbox = hasCustomHitbox;
	}

	public Rectangle getCustomHitbox()
	{
		return customHitbox;
	}

	public void setCustomHitbox(Rectangle customHitbox)
	{
		this.customHitbox = customHitbox;
	}
}
