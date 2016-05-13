package me.zm.apcsgame.level;

import me.zm.apcsgame.entity.tiles.InteractType;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/15/16.
 *
 * All of the tiles in the game
 */
public enum BlockType
{
	GRASS_1("Grass1", true);

	String fileName;
	boolean breakable;
	InteractType interactType;

	/**
	 * The block's constructor
	 * @param fileName The name of the block's image. (May later be changed to support animations)
	 * @param breakable True = the block is breakable. False = not breakable.
	 */
	BlockType(String fileName, boolean breakable)
	{
		this(fileName, breakable, InteractType.NONE);
	}

	/**
	 * @param interactType If and how the block can be interacted with
	 */
	BlockType(String fileName, boolean breakable, InteractType interactType)
	{
		this.fileName = fileName;
		this.breakable = breakable;
		this.interactType = interactType;
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
}
