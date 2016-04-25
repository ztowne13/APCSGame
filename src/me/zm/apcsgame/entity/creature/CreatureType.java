package me.zm.apcsgame.entity.creature;

/**
 * Created by ztowne13 on 4/11/16.
 */
public enum CreatureType
{
	PLAYER(10);

	int defaultHealth;

	CreatureType(int defaultHealth)
	{
		this.defaultHealth = defaultHealth;
	}

	public int getDefaultHealth()
	{
		return defaultHealth;
	}

	public void setDefaultHealth(int defaultHealth)
	{
		this.defaultHealth = defaultHealth;
	}
}
