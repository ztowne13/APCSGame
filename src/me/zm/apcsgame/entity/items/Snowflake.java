package me.zm.apcsgame.entity.items;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.Entity;
import me.zm.apcsgame.locations.Location;

import java.awt.*;
import java.util.Random;

/**
 * Created by zacharymorris on 5/4/16.
 */
public class Snowflake extends Entity
{
    Random random = new Random();
    int speed, speedBase;

    public Snowflake(Game game, int x, int y, int width, int height, int speed, int speedBase)
    {
        super(game, "Snowflake", x, y, width, height, 1, false);

        this.speed = speed;
        this.speedBase = speedBase;
    }

    public void respawn()
    {
        int randX = random.nextInt(getGame().getCurrentLevel().getWidth()) - random.nextInt(getGame().getCurrentLevel().getWidth());

        getLocation().setX(randX);
    }

    @Override
    public void tick()
    {
        Location l = getLocation();

        int xMove = random.nextInt(speed-speedBase) + speedBase;
        int yMove = random.nextInt(speed-speedBase) + speedBase;

        l.setX(l.getX() + xMove);
        l.setY(l.getY() + yMove);

        if(l.getY() > getGame().getCurrentLevel().getHeight() || l.getX() > getGame().getCurrentLevel().getWidth() + 20)
        {
            respawn();
        }
    }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillOval(getLocation().getX()-(int)getGame().getCurrentLevel().getGameCamera().getxOffset(), getLocation().getY()-(int)getGame().getCurrentLevel().getGameCamera().getyOffset(), getWidth(), getHeight());
    }
}
