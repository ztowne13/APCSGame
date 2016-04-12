package me.zm.apcsgame.gfx;

import me.zm.apcsgame.utils.GraphicUtils;

import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class SpriteSheet
{
	private BufferedImage sheet;

	/**
	 * Loads the entire sheet that may contain multiple sprite values
	 * @param filePath The local path to the image "sprite" sheet.
	 */
	public SpriteSheet(String filePath)
	{
		this.sheet = GraphicUtils.loadImage(filePath);
	}

	/**
	 * Gets a section of the main sprite sheet as a buffered image.
	 */
	public BufferedImage crop(int x, int y, int width, int height)
	{
		return sheet.getSubimage(x, y, width, height);
	}
}
