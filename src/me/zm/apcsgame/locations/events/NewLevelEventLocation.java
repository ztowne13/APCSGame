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

	public NewLevelEventLocation(Game game, int x, int y, int eventRadius, String toLevel)
	{
		super(game, x, y, eventRadius);

		this.toLevel = toLevel;
	}

	@Override
	public void executeFor(Entity entity)
	{
		if(isExecutable(entity))
		{
			new FadeEffect(game, Color.BLACK, 20, false);
			Level level = new Level(game, toLevel, game.getWidth(), game.getHeight());
			level.loadAll();
			game.setCurrentLevel(level);

			game.getPlayer().getLocation().setX(level.getSpawnPoint().x);
			game.getPlayer().getLocation().setY(level.getSpawnPoint().y);
		}
	}

	@Override
	public boolean isExecutable(Entity entity)
	{
		return new Vector2(getX(), getY()).nor().dst(new Vector2(entity.getLocation().getX(), entity.getLocation().getY())) <= eventRadius;
	}
}
