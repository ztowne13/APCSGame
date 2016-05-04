package me.zm.apcsgame.utils;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

		ArrayList<String> loadedList = new ArrayList<>();

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
		try
		{

			return ImageIO.read(FileUtils.class.getResource("/" + path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
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
