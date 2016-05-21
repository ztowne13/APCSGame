package me.zm.apcsgame.entity.items;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;

import java.awt.*;
import java.util.Random;

/**
 * Created by ztowne13 on 5/20/16.
 */
public class Sparkle extends Entity
{
	Random random = new Random();
	int speed, speedBase;

	public Sparkle(Game game, int x, int y, int width, int height, int speed, int speedBase)
	{
		super(game, "Snowflake", x, y, width, height, 1, false);

		this.speed = speed;
		this.speedBase = speedBase;
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void draw(Graphics g)
	{

	}
}
