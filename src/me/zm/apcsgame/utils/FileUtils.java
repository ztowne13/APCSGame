package me.zm.apcsgame.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
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
		boolean res = true;
		localPath = "/" + localPath;
		String loadedString = "";

		try
		{
			InputStream fileInputSteam = FileUtils.class.getResourceAsStream(localPath);

			int content;
			while ((content = fileInputSteam.read()) != -1)
			{
				loadedString += (char) content;
			}
		}
		catch (Exception exc)
		{
			JOptionPane.showConfirmDialog(null, exc.getMessage());
			JOptionPane.showConfirmDialog(null, exc.getCause());
			JOptionPane.showConfirmDialog(null, exc.getLocalizedMessage());
			JOptionPane.showConfirmDialog(null, exc.getStackTrace());
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
			//JOptionPane.showConfirmDialog(null, (FileUtils.class.getResource((res ? "/res" : "") + "/" + path)));
			System.out.println(path);
			System.out.println(FileUtils.class.getResource("/" + path));
			BufferedImage bI = ImageIO.read(FileUtils.class.getResourceAsStream("/" + path));

			BufferedImage after = new BufferedImage((int)(bI.getWidth()*imageScale), (int)(bI.getHeight()*imageScale), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(imageScale, imageScale);
			AffineTransformOp scaleOp =
					new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			return scaleOp.filter(bI, after);
		}
		catch (IOException e)
		{
			JOptionPane.showConfirmDialog(null, ("failed to load image: " + path));
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
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(FileUtils.class.getResourceAsStream("/sounds/" + path)));
			return inputStream;
		}
		catch(Exception exc)
		{
			JOptionPane.showConfirmDialog(null, ("failed to load sound: " + path));
			JOptionPane.showConfirmDialog(null, exc.getMessage());
			JOptionPane.showConfirmDialog(null, exc.getStackTrace());
			exc.printStackTrace();
		}
		return null;
	}
}
