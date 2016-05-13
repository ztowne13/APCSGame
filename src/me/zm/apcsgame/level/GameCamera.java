package me.zm.apcsgame.level;

import com.badlogic.gdx.math.Vector2;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.locations.Location;

/**
 * Created by ztowne13 on 4/12/16.
 */
public class GameCamera
{
	private Game game;

	private int cameraMoveSpeed = 30;

	private float xOffset, yOffset, baseXOffset, baseYOffset;
	private float toMoveX, toMoveY;
	float moveRatio;

	public GameCamera(Game game, float baseXOffset, float baseYOffset, float moveRatio)
	{
		this.game = game;
		this.baseXOffset = baseXOffset;
		this.baseYOffset = baseYOffset;

		this.moveRatio = moveRatio;
	}

	public void centerOnEntity(Entity e)
	{
		xOffset = e.getLocation().getX() - game.getWidth() / 2 + e.getWidth() / 2;
		yOffset = e.getLocation().getY() - game.getHeight() / 2 + e.getHeight() / 2;
	}

	public void move(float xAmt, float yAmt)
	{
		if(GameSettings.levelBuildMode)
		{
			xOffset += xAmt;
			yOffset += yAmt;
		}
		else
		{
			toMoveY += yAmt / moveRatio;
			toMoveX += xAmt / moveRatio;
		}
	}

	public boolean moveGameCamera(Player player)
	{
		Location location = player.getLocation();

		return (new Vector2(location.getX() - xOffset, location.getY() - yOffset).dst(new Vector2(game.getWidth()/2, game.getHeight()/2)) > (game.getWidth() + game.getHeight()) / 25) || GameSettings.levelBuildMode;
	}

	public void tick()
	{
		float xDist = toMoveX;
		float yDist = toMoveY;

		float xSpeed = xDist / cameraMoveSpeed;
		float ySpeed = yDist / cameraMoveSpeed;

		toMoveY -= ySpeed;
		toMoveX -= xSpeed;

		xOffset += xSpeed;
		yOffset += ySpeed;
	}

	public float getxOffset()
	{
		return xOffset;
	}

	public void setxOffset(float xOffset)
	{
		this.xOffset = xOffset;
	}

	public float getyOffset()
	{
		return yOffset;
	}

	public void setyOffset(float yOffset)
	{
		this.yOffset = yOffset;
	}
}
