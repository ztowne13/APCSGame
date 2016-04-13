package me.zm.apcsgame.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by ztowne13 on 4/10/16.
 */
public class GraphicUtils
{
	public static BufferedImage loadImage(String path)
	{
		try
		{
			return ImageIO.read(GraphicUtils.class.getResource("/" + path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
