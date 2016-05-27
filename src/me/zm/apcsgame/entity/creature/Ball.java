package me.zm.apcsgame.entity.creature;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.ai.interactions.InteractionAIType;
import me.zm.apcsgame.ai.pathfinding.PathfinderAIType;
import me.zm.apcsgame.displays.animations.AnimationType;
import me.zm.apcsgame.displays.animations.DirectionalAnimation;
import me.zm.apcsgame.locations.Direction;
import me.zm.apcsgame.locations.Location;
import me.zm.apcsgame.utils.MathUtils;

import java.awt.*;
import java.util.ArrayList;

/*** Created by ztowne13 on 5/27/16.	private final float swingCooldown = 1000000000;*/
public class Ball extends Creature
{
 Location lastLoc;
 DirectionalAnimation moveAnim;
 Direction lastMoveDirection = Direction.EAST;

 public Ball(Game game, String id, int x, int y, int width, int height)
 {
 this(game, id, x, y, width, height, true);
 }

 public Ball(Game game, String id, int x, int y, int width, int height, boolean loadImages)
 {
 super(game, x, y, width, height, 5, 10, CreatureType.CROW, PathfinderAIType.BALL, InteractionAIType.HIT_NEAR);

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

 moveAnim = new DirectionalAnimation(getGame(), AnimationType.BALL_ROLL, getLocation(), excludedDirections);
 moveAnim.loadImages(1);

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
 moveAnim.render(false, lastMoveDirection, g);
 }

 @Override
 public boolean canMove()
 {
 return true;
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

 }
