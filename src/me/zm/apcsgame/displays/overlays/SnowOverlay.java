package me.zm.apcsgame.displays.overlays;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.entity.items.Snowflake;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zacharymorris on 5/4/16.
 */
public class SnowOverlay extends Overlay
{
    ArrayList<Snowflake> flakes = new ArrayList<Snowflake>();

    public SnowOverlay(Game game, int particleCount)
    {
        super(game, particleCount);

        createAllFlakes();
    }

    public void createAllFlakes()
    {
        Random random = new Random();
        for(int i = 0; i < particleCount; i++)
        {
            int randX = random.nextInt(game.getCurrentLevel().getWidth()) - random.nextInt(game.getCurrentLevel().getHeight());
            int randY = random.nextInt(game.getCurrentLevel().getHeight());
            Snowflake snowflake = new Snowflake(game, randX, randY, 4, 4, 4, 2);
            flakes.add(snowflake);
        }
    }

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Graphics g)
    {
        for(Snowflake snowflake : flakes)
        {
            if(!game.getCurrentLevel().getPauseMenu().isInPauseMenu())
            {
                snowflake.tick();
            }
            snowflake.draw(g);
        }
    }
}
