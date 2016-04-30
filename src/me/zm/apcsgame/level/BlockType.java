package me.zm.apcsgame.level;

import me.zm.apcsgame.utils.FileUtils;

import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/15/16.
 */
public enum BlockType
{
	GRASS_1("Grass1");

	String fileName;

	BlockType(String fileName)
	{
		this.fileName = fileName;
	}

	public BufferedImage getImage()
	{
		return FileUtils.loadImage("blocks/" + fileName + ".png");
	}
}
