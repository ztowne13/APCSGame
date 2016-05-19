package me.zm.apcsgame.entity;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.locations.Location;

import java.awt.*;
import java.util.UUID;

/**
 * Created by ztowne13 on 4/11/16.
 */
public abstract class Entity
{
	private Game game;
	String name;

	private UUID uuid;
	private int width, height, maxhealth, health;
	boolean damageable, dead;

	Location location;

	public Entity(Game game, String name, int x, int y, int width, int height, int maxhealth)
	{
		this(game, name, x, y, width, height, maxhealth, true);
	}

	/**
	 * Constructs a new entity
	 * @param game The game instance
	 * @param name The name of the entity (must be the creature type for creatures and such)
	 * @param x The base x location of the entity
	 * @param y The base y location of the entity
	 * @param width The width of the entity
	 * @param height The height of the enity
	 * @param maxhealth The maximum health of the entity
     * @param damageable Is the entity able to be damaged
     */
	public Entity(Game game, String name, int x, int y, int width, int height, int maxhealth, boolean damageable)
	{
		this.game = game;
		this.width = width;
		this.height = height;
		this.maxhealth = maxhealth;
		this.health = maxhealth;
		this.damageable = damageable;

		this.name = name;

		this.location = new Location(game, x, y);

		this.uuid = UUID.randomUUID();
	}

	public abstract void tick();

	public abstract void draw(Graphics g);

	/**
	 * Checks whether or not the entity should be rendered first because it is beneath the entity
	 * @param entity The other object with x and y coordinates to see if it should be rendered before.
	 * @return Returns true if it should render before and false if it should render after
	 */
	public boolean renderBefore(Entity entity)
	{
		return getHitbox().getY() + getHitbox().getHeight() > entity.getHitbox().getY() + entity.getHitbox().getHeight();
	}

	/**
	 * Gets the hitbox of the entity
	 * @return The hitbox of the entity.
	 */
	public Rectangle getHitbox()
	{
		return new Rectangle(getLocation().getX() - (int)getGame().getCurrentLevel().getGameCamera().getxOffset(), getLocation().getY() - (int)getGame().getCurrentLevel().getGameCamera().getyOffset(), getWidth(), getHeight());
	}

	/**
	 * Checks if the tile collides with an entity
	 * @param otherHitBox The other hit box it may be colliding with
	 * @return True if it collides, false if it doesn't
	 */
	public boolean collidesWith(Rectangle otherHitBox)
	{
		return getHitbox().intersects(otherHitBox);
	}

	public void damage(int amount)
	{
		if(damageable)
		{
			health -= amount;

			if (health <= 0)
			{
				destroy();
			}
		}
	}

	public void destroy()
	{
		getGame().getCurrentLevel().getEntities().remove(this);
		setDead(true);
	}

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

	public int getMaxhealth()
	{
		return maxhealth;
	}

	public void setMaxhealth(int maxhealth)
	{
		this.maxhealth = maxhealth;
	}

	public int getHealth()
	{
		return health;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isDamageable() {
		return damageable;
	}

	public void setDamageable(boolean damageable) {
		this.damageable = damageable;
	}

	public boolean isDead()
	{
		return dead;
	}

	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
}
