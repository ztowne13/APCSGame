package me.zm.apcsgame.saves;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.utils.FileUtils;

import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/11/16.
 */
public class GameSave
{
	Game game;
	int save;

	public GameSave(Game game, int save)
	{
		this.game = game;
		this.save = save;
	}

	public void loadGameFromSave()
	{
		ArrayList<String> fileLines = FileUtils.loadFileByLine("save" + save);
	}

	public void saveGameToSave()
	{

	}
}
