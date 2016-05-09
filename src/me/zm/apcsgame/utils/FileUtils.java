package me.zm.apcsgame.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class FileUtils
{
	/**
	 * Loads a text file to an arraylist where each array entry is a new line in the file
	 * @param localPath The path of the text file.
	 * @return The parsed file array
	 */
	public static ArrayList<String> loadFileByLine(String localPath)
	{
		localPath = "/" + localPath;
		String loadedString = "";

		try
		{
			File file = new File(FileUtils.class.getResource(localPath).toURI());
			FileInputStream fileInputSteam = new FileInputStream(file);

			int content;
			while ((content = fileInputSteam.read()) != -1)
			{
				loadedString += (char) content;
			}
		}
		catch (Exception exc)
		{

		}

		ArrayList<String> loadedList = new ArrayList<String>();

		for(String s : loadedString.split("\n"))
		{
			loadedList.add(s);
		}

		return loadedList;
	}

	/**
	 * Loads an image from the resources
	 * @param path The path of the image
	 * @return The loaded image as a BufferedImage
	 */
	public static BufferedImage loadImage(String path)
	{
		return loadImage(path, 1);
	}

	public static BufferedImage loadImage(String path, double imageScale)
	{
		try
		{

			BufferedImage bI = ImageIO.read(FileUtils.class.getResource("/" + path));

			BufferedImage after = new BufferedImage((int)(bI.getWidth()*imageScale), (int)(bI.getHeight()*imageScale), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(imageScale, imageScale);
			AffineTransformOp scaleOp =
					new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			return scaleOp.filter(bI, after);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage bitmapToImage(InputStream is, String format) throws IOException
	{
		final ImageReader rdr = ImageIO.getImageReadersByFormatName(format).next();
		final ImageInputStream imageInput = ImageIO.createImageInputStream(is);
		rdr.setInput(imageInput);
		final BufferedImage image = rdr.read(0);
		is.close();
		return image;
	}

	/**
	 * Loads a sound clip from the resources
	 * @param path The path of the sound clip
	 * @return The sound clip as an AudioInputStream with the clip loaded.
	 */
	public static AudioInputStream loadSoundClip(String path)
	{
		try
		{
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(FileUtils.class.getResourceAsStream("/sounds/" + path));
			return inputStream;
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
		return null;
	}
}
