package me.zm.apcsgame.entity.creature;

import com.badlogic.gdx.math.Vector2;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.GameSettings;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.entity.tiles.Tile;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.MathUtils;

import java.util.ArrayList;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Creature extends Entity
{
	CreatureType creatureType;

	Location pathFinderLoc = null;
	int tickDelay;
	float lastSwing = 0;

	public Creature(Game game, int x, int y, int width, int height, int tickDelay, int maxhealth, CreatureType creatureType)
	{
		super(game, creatureType.name(), x, y, width, height, maxhealth, true);
		this.tickDelay = tickDelay;
		this.creatureType = creatureType;
	}

	/**
	 * Checks to see whether or not the entity should move
	 */
	public abstract void checkMove();

	/**
	 * Swings the creatures melee attack. I.E. sword swing.
	 */
	public void attack_melee()
	{
		if(System.nanoTime() - getLastSwing() > GameSettings.swingCooldown)
		{
			if(this instanceof Player)
			{
				((Player)this).swingAnimation.setCurrentAnimationStage(0);
			}
			// Mouse X and Y positions
			int mouseX = (int) getGame().getDisplay().getCanvas().getMousePosition().getX();
			int mouseY = (int) getGame().getDisplay().getCanvas().getMousePosition().getY();

			// Creature X and Y positions with game camera offset
			double midX = MathUtils.middle(getLocation().getX(), getWidth()) - getGame().getCurrentLevel().getGameCamera().getxOffset();
			double midY = MathUtils.middle(getLocation().getY(), getHeight()) - getGame().getCurrentLevel().getGameCamera().getyOffset();

			// Creature position as Vector2
			Vector2 crVec = getLocation().getVectorAsMiddleWithOffset(getWidth(), getHeight());

			// Sorts through all entities (Players, Creatures, Tiles)
			for(Entity entity : (ArrayList<Entity>) getGame().getCurrentLevel().getEntities().clone())
			{
				// Make sure it's a breakable tile
				if (!(entity instanceof Tile && !((Tile) entity).getBlockType().isBreakable()) || entity instanceof Creature)
				{
					// Checks that the entity is not this entity so it doesn't kill itsself.
					if (!entity.getUuid().toString().equalsIgnoreCase(getUuid().toString()))
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
						boolean inField = (mouseAngle - enemyAngle < 90 && mouseAngle - enemyAngle > -90);

						float distance = crVec.dst(entVec);

						if (inField && distance < 160)
						{
							entity.damage(1);
						}

					}
				}
			}

			lastSwing = System.nanoTime();
		}
	}

	public boolean collides()
	{
		boolean collidesWithTile = false;

		for(Entity tile : getGame().getCurrentLevel().getEntities())
		{
			if(tile instanceof Tile)
			{
				if (((Tile)tile).collidesWith(getHitbox()))
				{
					collidesWithTile = true;
					break;
				}
			}
		}

		return getGame().getCurrentLevel().isEntityOutsideBounds(this) || collidesWithTile;
	}
	
	public void update_pathfinder()
	{
		if(!(pathFinderLoc == null)) {
 			Location toLoc = pathFinderLoc;
 			Location cL = getLocation();
 			int tempX = cL.getX();
 			int tempY = cL.getY();

 			double angle = Math.toDegrees(Math.atan2(toLoc.getY() - cL.getY(), toLoc.getX() - cL.getX()));
			System.out.println(angle);

 			int addPlus = 0;
 			boolean success = false;

 			while (true) {
 				double sin = Math.sin(Math.toRadians(angle + addPlus)) * 3;
 				double cos = Math.cos(Math.toRadians(angle + addPlus)) * 3;
				System.out.println(sin + " -- " + cos);

 				cL.setY(tempY + (int) sin);
 				cL.setX(tempX + (int) cos);

 				if (!collides()) {
 					success = true;
 					break;
 				}

 				sin = Math.sin(Math.toRadians(angle - addPlus)) * 3;
 				cos = Math.cos(Math.toRadians(angle - addPlus)) * 3;

 				cL.setY(tempY + (int) sin);
 				cL.setX(tempX + (int) cos);

 				if (!collides()) {
 					success = true;
 					break;
 				}

				addPlus++;

				if(addPlus >= 360)
				{
					break;
				}
 			}

 			if (!success) {
 				cL.setX(tempX);
 				cL.setY(tempY);
 			}
 		}
	}

	public CreatureType getCreatureType()
	{
		return creatureType;
	}

	public void setCreatureType(CreatureType creatureType)
	{
		this.creatureType = creatureType;
	}

	public float getLastSwing()
	{
		return lastSwing;
	}

	public void setLastSwing(float lastSwing)
	{
		this.lastSwing = lastSwing;
	}

	public Location getPathFinderLoc()
	{
		return pathFinderLoc;
	}

	public void setPathFinderLoc(Location pathFinderLoc)
	{
		this.pathFinderLoc = pathFinderLoc;
	}

	public int getTickDelay()
	{
		return tickDelay;
	}

	public void setTickDelay(int tickDelay)
	{
		this.tickDelay = tickDelay;
	}
}
