package me.zm.apcsgame;

import me.zm.apcsgame.tick.GameStateTick;

/**
 * Created by ztowne13 on 4/8/16.
 */
public enum GameState
{
	STARTUP(null),

	MAIN_MENU(null),

	IN_LEVEL(null),

	STOPPED(null);

	GameStateTick gameStateTick;

	GameState(GameStateTick gameStateTick)
	{
		this.gameStateTick = gameStateTick;
	}
}
