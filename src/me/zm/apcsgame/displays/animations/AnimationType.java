package me.zm.apcsgame.displays.animations;

/**
 * Created by ztowne13 on 5/3/16.
 */
public enum AnimationType
{
	PLAYER_WALK(3),

	INTERACT(4);

	int framesCount;

	/**
	 * AnimationType constructor.
	 * @param framesCount The amount of frames the specific animation has.
	 */
	AnimationType(int framesCount)
	{
		this.framesCount = framesCount;
	}
}
