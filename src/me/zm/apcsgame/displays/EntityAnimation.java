package me.zm.apcsgame.displays;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.entity.creature.Creature;
import me.zm.apcsgame.entity.creature.Player;
import me.zm.apcsgame.utils.GraphicUtils;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by ztowne13 on 4/19/16.
 */
public class EntityAnimation
{
	Game game;
	Creature creature;

	// String is formated with the direction and the animation stage number tacked onto it. Ex: NORTH2
	HashMap<String,Image> images = new HashMap<>();

	int currentAnimationStage = 0;

	public EntityAnimation(Game game, Creature creature)
	{
		this.game = game;
		this.creature = creature;
	}

	/**
	 * Loads an image for each
	 */
	public void loadImages()
	{
		for(String dir : new String[]{"NORTH", "EAST", "SOUTH", "WEST"})
		{
			for(int i = 0; i < GameSettings.totalAnimationFrames; i++)
			{
				// Formatted CreatureType + Direction + Anim. stage number. EX: PlayerNORTH1 or FighterEAST0
				images.put(dir + i, GraphicUtils.loadImage((creature instanceof Player ? "characters" : "creatures") + "/" + creature.getCreatureType().name() + dir + i + ".png"));
			}
		}
	}

	public void tick()
	{
		currentAnimationStage++;
		if(currentAnimationStage >= GameSettings.totalAnimationFrames)
		{
			currentAnimationStage = 0;
		}
	}

	public void render(Graphics g)
	{
		g.drawImage(images.get(creature.getLocation().getDirection().toString() + currentAnimationStage), creature.getLocation().getX(), creature.getLocation().getY(), null);
	}
}
