package me.zm.apcsgame.utils;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

	public static AudioInputStream loadSoundClip(String path)
	{
		try
		{
			Clip clip = AudioSystem.getClip();
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
