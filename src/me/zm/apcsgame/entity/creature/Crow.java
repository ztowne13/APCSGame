package me.zm.apcsgame.entity.creature;

/**
 * Created by ztowne13 on 5/26/16.
 */
public class Crow
{
	/*private final float swingCooldown = 1000000000;

	Location lastLoc;
	DirectionalAnimation moveAnim;
	OrderedAnimation swingAnim;
	Direction lastMoveDirection = Direction.EAST;

	public Boss1Minion(Game game, String id, int x, int y, int width, int height, int speed)
	{
		this(game, id, x, y, width, height, speed, true);
	}

	public Boss1Minion(Game game, String id, int x, int y, int width, int height, int speed, boolean loadImages)
	{
		super(game, x, y, width, height, 3, 10, CreatureType.BOSS_MINION, PathfinderAIType.WALK_STRAIGHT, InteractionAIType.HIT_NEAR);

		if(loadImages)
		{
			loadImages();
		}
	}

	public void loadImages()
	{
		ArrayList<String> excludedDirections = new ArrayList<String>();
		excludedDirections.add("NORTH");
		excludedDirections.add("SOUTH");

		moveAnim = new DirectionalAnimation(getGame(), AnimationType.BOSS_WALK, getLocation(), excludedDirections);
		moveAnim.loadImages(.2);

		swingAnim = new OrderedAnimation(getGame(), AnimationType.BOSS_ATTACK, getLocation());
		swingAnim.loadImages(.2);

		updateWidthHeight();
	}

	public void updateWidthHeight()
	{
		setWidth(moveAnim.getImages().values().iterator().next().getWidth(null));
		setHeight(moveAnim.getImages().values().iterator().next().getHeight(null));
	}

	@Override
	public void checkMove() {

	}

	@Override
	public void tick()
	{
		if(getPathfindingAI().getPathFindingGoal() == null)
		{
			Player p = getGame().getCurrentLevel().getPlayer();
			if (MathUtils.distance(p.getLocation(), getLocation()) < getCreatureType().getVisibleRange())
			{
				getPathfindingAI().setPathFindingGoal(p.getLocation());
				getPathfindingAI().setPathFindingWidth(p.getWidth());
				getPathfindingAI().setPathFindingHeight(p.getHeight());
			}
		}

		lastLoc = getLocation().clone();

		find_path();
		if(interact())
		{
			lastSwing = System.nanoTime();
		}

		lastMoveDirection = getLocation().getX() - lastLoc.getX() < 0 ? Direction.WEST : Direction.EAST;

		if(getGame().getTicksAlive() % getTickDelay() == 0)
		{
			moveAnim.tick();
		}
	}

	@Override
	public void draw(Graphics g) {
		if(canMove())
		{
			moveAnim.render(false, lastMoveDirection, g);
		}
		else
		{
			if (getGame().getTicksAlive() % 4 == 0)
			{
				swingAnim.tick();
				swingAnim.render(g);
			}
			else
			{
				swingAnim.render(g);
			}
		}
	}

	@Override
	public boolean canMove()
	{
		return System.nanoTime() - lastSwing > swingCooldown;
	}

	public Direction getLastMoveDirection()
	{
		return lastMoveDirection;
	}

	public void setLastMoveDirection(Direction lastMoveDirection)
	{
		this.lastMoveDirection = lastMoveDirection;
	}

	public DirectionalAnimation getMoveAnim()
	{
		return moveAnim;
	}

	public void setMoveAnim(DirectionalAnimation moveAnim)
	{
		this.moveAnim = moveAnim;
	}

	public OrderedAnimation getSwingAnim()
	{
		return swingAnim;
	}

	public void setSwingAnim(OrderedAnimation swingAnim)
	{
		this.swingAnim = swingAnim;
	}*/
}
