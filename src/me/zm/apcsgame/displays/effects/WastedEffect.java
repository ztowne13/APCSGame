package me.zm.apcsgame.displays.effects;

import java.awt.*;
import me.zm.apcsgame.Game;
import me.zm.apcsgame.utils.FileUtils;

import java.awt.image.BufferedImage;
import java.net.URL;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

/**
 * Created by Jason Rhoads on 5/25/2016.
 */
public class WastedEffect extends GraphicEffect
{


    int time;
    BufferedImage image;

    boolean endWhenDone;

    public WastedEffect(Game game, boolean endWhenDone)
    {
        super(game);
        this.endWhenDone = endWhenDone;
        image = FileUtils.loadImage("hud/WASTED.png");
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

