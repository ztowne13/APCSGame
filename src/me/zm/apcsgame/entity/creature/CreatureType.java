package me.zm.apcsgame.entity.creature;

import me.zm.apcsgame.Game;

/**
 * Created by ztowne13 on 4/11/16.
 */
public enum CreatureType
{
	PLAYER(10, 0, 1),

	BOSS_1(200, 300, 2),

	BOSS_MINION(3, 300, 1);

	int defaultHealth;
	int visibleRange;
	int damageAmount;

	/**
	 * CreatureType constructor
	 * @param defaultHealth The default amount of health this specific creature will have unless changed.
	 * @param visibleRange How many pixels the mob's AI will detect the player
	 */
	CreatureType(int defaultHealth, int visibleRange, int damageAmount)
	{
		this.defaultHealth = defaultHealth;
		this.visibleRange = visibleRange;
		this.damageAmount = damageAmount;
	}

	public void spawn(Game game, int x, int y)
	{
		switch(this)
		{
			case BOSS_1:
				game.getCurrentLevel().getEntities().add(new Boss1(game, "boss1", x, y, 100, 100, 3, .6));
				break;
			case BOSS_MINION:
				game.getCurrentLevel().getEntities().add(new Boss1(game, "boss1", x, y, 100, 100, 3, .2));
				break;
		}
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
}
