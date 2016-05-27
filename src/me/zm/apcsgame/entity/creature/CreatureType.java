package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;

/**
 * Created by ztowne13 on 4/11/16.
 */
public enum CreatureType
{
	PLAYER(10, 0, 1, 160),

	BOSS_1(200, 300, 2, 220),

	CROW(3, 200, 1, 50),

	BOSS_MINION(3, 300, 1, 67);

	int defaultHealth;
	int visibleRange;
	int damageAmount;
	int swingDistance;

	/**
	 * CreatureType constructor
	 * @param defaultHealth The default amount of health this specific creature will have unless changed.
	 * @param visibleRange How many pixels the mob's AI will detect the player
	 */
	CreatureType(int defaultHealth, int visibleRange, int damageAmount, int swingDistance)
	{
		this.defaultHealth = defaultHealth;
		this.visibleRange = visibleRange;
		this.damageAmount = damageAmount;
		this.swingDistance = swingDistance;
	}

	public Creature spawn(Game game, int x, int y)
	{
		return spawn(game, x, y, true);
	}

	public Creature spawn(Game game, int x, int y, boolean loadImages)
	{
		Creature creature = null;
		switch(this)
		{
			case BOSS_1:
				creature = new Boss1(game, "boss1", x, y, -1, -1, 3);
				game.getCurrentLevel().getEntities().add(creature);
				break;
			case BOSS_MINION:
				creature = new Boss1Minion(game, "boss1 minion", x, y, -1, -1, 3, loadImages);
				game.getCurrentLevel().getEntities().add(creature);
				break;
		}

		return creature;
	}

	public int getDefaultHealth()
	{
		return defaultHealth;
	}

	public void setDefaultHealth(int defaultHealth)
	{
		this.defaultHealth = defaultHealth;
	}

	public int getVisibleRange()
	{
		return visibleRange;
	}

	public void setVisibleRange(int visibleRange)
	{
		this.visibleRange = visibleRange;
	}

	public int getDamageAmount()
	{
		return damageAmount;
	}

	public void setDamageAmount(int damageAmount)
	{
		this.damageAmount = damageAmount;
	}

	public int getSwingDistance()
	{
		return swingDistance;
	}

	public void setSwingDistance(int swingDistance)
	{
		this.swingDistance = swingDistance;
	}
}
