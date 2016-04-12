package me.zm.apcsgame.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class FileUtils
{
	public static ArrayList<String> loadFileByLine(String localPath)
	{
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
}
