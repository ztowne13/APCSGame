package me.zm.apcsgame.displays;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ztowne13 on 5/10/16.
 */
public class Background
{
	Game game;

	int offset;
	BufferedImage backGroundImage;

	public Background(Game game, String backgroundName, int offset, double imageScale)
	{
		this.game = game;
		this.offset = offset;

		this.backGroundImage = FileUtils.loadImage("backgrounds/" + backgroundName + ".png", imageScale);
	}

	public void render(Graphics graphics)
	{
		graphics.drawImage(backGroundImage, -offset-(int)(game.getGameCamera().getxOffset()/5), -offset-(int)(game.getGameCamera().getyOffset()/5), null);
	}
}
