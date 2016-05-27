package me.zm.apcsgame.displays.effects;

import me.zm.apcsgame.Game;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jason Rhoads on 5/25/2016.
 */
public class WastedEffect extends GraphicEffect
{


    int time;
    BufferedImage image;

    boolean endWhenDone;

    public WastedEffect(Game game, boolean endWhenDone, String path)
    {
        super(game);
        this.endWhenDone = endWhenDone;
        image = FileUtils.loadImage("hud/" + path + ".png");
    }
    public void draw(Graphics graphics)
    {

        graphics.drawImage(image,0,0, game.getDisplay().getCanvas().getWidth(), game.getDisplay().getCanvas().getHeight(),null);
    }
    public boolean tick()
    {
        tickCount += time;
        if (tickCount > 255)
        {
            tickCount = 255;
            if(endWhenDone)
            {
                return false;
            }
        }
        return true;
    }
}

