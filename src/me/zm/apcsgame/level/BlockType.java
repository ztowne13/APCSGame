package me.zm.apcsgame.level;

import me.zm.apcsgame.entity.breakables.InteractType;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/15/16.
 */
public enum BlockType
{
	GRASS_1("Grass1", true);

	String fileName;
	boolean breakable;
	InteractType interactType;

	BlockType(String fileName, boolean breakable)
	{
		this(fileName, breakable, InteractType.NONE);
	}

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
}
