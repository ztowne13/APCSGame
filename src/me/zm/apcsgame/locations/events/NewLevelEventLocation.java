package me.zm.apcsgame.locations.events;

import com.badlogic.gdx.math.Vector2;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.displays.effects.FadeEffect;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.level.Level;

import java.awt.*;

/**
 * Created by ztowne13 on 5/14/16.
 */
public class NewLevelEventLocation extends EventLocation
{
	String toLevel;
	int toX, toY;

	public NewLevelEventLocation(Game game, int x, int y, int eventRadius, int toX, int toY, String toLevel)
	{
		super(game, x, y, eventRadius);

		this.toLevel = toLevel;
		this.toX = toX;
		this.toY = toY;
	}

	@Override
	public void executeFor(Graphics graphics, Entity entity)
	{
		if(isExecutable(entity))
		{
			game.getCurrentLevel().setFinished(true);
			game.setPlaySpeed(95);
			game.getGraphicEffects().put("fade in level", new FadeEffect(game, Color.BLACK, 2, false, false));

			Thread thread = new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						sleep(2134);
						game.getCurrentLevel().endLevel();

						Level level = new Level(game, toLevel, game.getWidth(), game.getHeight(), game.getCurrentLevel().getPlayer());
						level.loadAll(true, true, true, toX, toY);

						interrupt();
					}
					catch(Exception exc)
					{

					}
				}
			};

			thread.start();
		}
	}

	@Override
	public boolean isExecutable(Entity entity)
	{
		Vector2 eventVec = new Vector2(getX() - (int)game.getCurrentLevel().getGameCamera().getxOffset(), getY() - (int)game.getCurrentLevel().getGameCamera().getyOffset());
		Vector2 entityVec = new Vector2(entity.getLocation().getX() - (int)game.getCurrentLevel().getGameCamera().getxOffset(), entity.getLocation().getY() - (int)game.getCurrentLevel().getGameCamera().getyOffset());
		return eventVec.dst(entityVec) <= eventRadius && !game.getCurrentLevel().isFinished();
	}
}
