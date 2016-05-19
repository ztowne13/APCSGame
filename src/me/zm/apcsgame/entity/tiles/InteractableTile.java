package me.zm.apcsgame.entity.tiles;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.level.BlockType;

import java.awt.*;

/**
 * Created by ztowne13 on 5/1/16.
 *
 * Tiles that can be interacted by walking over, clicking on, or walking over them and clicking at the same time.
 */
public class InteractableTile extends Tile
{
	InteractType interactType;

	public InteractableTile(Game game, int x, int y, int width, int height, BlockType blockType, boolean flipped)
	{
		super(game, x, y, width, height, blockType, flipped);

		setWidth(image.getWidth());
		setHeight(image.getHeight());

		this.interactType = blockType.getInteractType();
	}

	/**
	 * Checks if the player is interacting with this tile.
	 */
	@Override
	public void tick()
	{
		switch(interactType)
		{
			case WALK_OVER:
				if(getGame().getCurrentLevel().getPlayer().collidesWith(getHitbox()))
				{
					interact();
				}
				break;
			case MOUSE_INTERACT_WHILE_ABOVE:
				if(isClicking())
				{
					interact();
				}
				break;
			case MOUSE_INTERACT_OBJECT:
				Rectangle mouseHitBox = getGame().getMousePointer().getMouseHitbox();
				if(((Entity)this).collidesWith(mouseHitBox))
				{
					interact();
				}
				break;
		}
	}

	/**
	 * Checks if the player is holding the interact button.
	 * @return True = player is holding interact button. False = player not holding interact button.
	 */
	public boolean isClicking()
	{
		return getGame().getKeyInputListener().interact;
	}

	@Override
	public void draw(Graphics g)
	{

	}

	/**
	 * Interacts with this tile based off of what it's supposed to do.
	 */
	public void interact()
	{

	}
}
