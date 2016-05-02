package me.zm.apcsgame.entity.creature;

/**
 * Created by ztowne13 on 4/11/16.
 */
public enum CreatureType
{
	PLAYER(10, 3);

	int defaultHealth;
	int defaultAnimationLength;

	CreatureType(int defaultHealth, int defaultAnimationLength)
	{
		this.defaultHealth = defaultHealth;
		this.defaultAnimationLength = defaultAnimationLength;
	}

	public int getDefaultHealth()
	{
		return defaultHealth;
	}

	public void setDefaultHealth(int defaultHealth)
	{
		this.defaultHealth = defaultHealth;
	}

	public int getDefaultAnimationLength()
	{
		return defaultAnimationLength;
	}

	public void setDefaultAnimationLength(int defaultAnimationLength)
	{
		this.defaultAnimationLength = defaultAnimationLength;
	}
}
