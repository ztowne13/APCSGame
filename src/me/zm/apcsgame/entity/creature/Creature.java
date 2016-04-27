package me.zm.apcsgame.entity.creature;

import com.badlogic.gdx.math.Vector2;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.utils.MathUtils;

import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Creature extends Entity
{
	CreatureType creatureType;

	public Creature(Game game, int x, int y, int width, int height, int maxhealth, CreatureType creatureType)
	{
		super(game, x, y, width, height, maxhealth);
		this.creatureType = creatureType;
	}

	public abstract void checkMove();

	public CreatureType getCreatureType()
	{
		return creatureType;
	}

	public void setCreatureType(CreatureType creatureType)
	{
		this.creatureType = creatureType;
	}

	/**
	 * Swings the creatures melee attack. I.E. sword swing.
	 */
	public void attack_melee()
	{
		// Mouse X and Y positions
		int mouseX = (int) getGame().getDisplay().getCanvas().getMousePosition().getX();
		int mouseY = (int) getGame().getDisplay().getCanvas().getMousePosition().getY();

		// Creature X and Y positions with game camera offset
		double midX = MathUtils.middle(getLocation().getX(), getWidth()) - getGame().getGameCamera().getxOffset();
		double midY = MathUtils.middle(getLocation().getY(), getHeight()) - getGame().getGameCamera().getyOffset();

		// Creature position as Vector2
		Vector2 crVec = getLocation().getVectorAsMiddleWithOffset(getWidth(), getHeight());

		// Sorts through all entities (Players, Creatures, Tiles)
		for(Entity entity : (ArrayList<Entity>) getGame().getEntities().clone())
		{
			// Checks that the entity is not this entity so it doesn't kill itsself.
			if(!entity.getUuid().toString().equalsIgnoreCase(getUuid().toString()))
			{
				// The specific entities Vector and X and Y position.
				Vector2 entVec = entity.getLocation().getVectorAsMiddleWithOffset(entity.getWidth(), entity.getHeight());
				double x = entVec.x;
				double y = entVec.y;

				// The angle of the mouse with this creature.
				float mouseAngle = new Vector2((float) (midX - mouseX), (float) (midY - mouseY)).angle();

				// The angle of the mouse with the entity.
				float enemyAngle = new Vector2((float) (midX - x), (float) (midY - y)).angle();

				// Checks if the entity is within 60 degrees of the swing radius
				boolean inField = (mouseAngle - enemyAngle < 30 && mouseAngle - enemyAngle > -30);

				float distance = crVec.dst(entVec);

				if(inField && distance < 100)
				{
					entity.damage(1);
				}

			}
		}
	}
}
