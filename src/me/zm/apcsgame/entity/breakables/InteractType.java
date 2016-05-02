package me.zm.apcsgame.entity.breakables;

/**
 * Created by ztowne13 on 5/1/16.
 */
public enum InteractType
{
	/**
	 * This tile can't be interacted with
	 */
	NONE,

	/**
	 * Interacts when the player walks on top of the object
	 */
	WALK_OVER,

	/**
	 * Interacts when the mouse / interact button is clicked on an object
	 */
	MOUSE_INTERACT_OBJECT,

	/**
	 * Interacts when the mouse / interact button is being clicked AND the player is standing on the tile
	 */
	MOUSE_INTERACT_WHILE_ABOVE;
}