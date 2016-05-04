package me.zm.apcsgame.displays.overlays;

import me.zm.apcsgame.Game;

import java.awt.*;

/**
 * Created by zacharymorris on 5/4/16.
 */
public abstract class Overlay
{
    Game game;
    int particleCount;

    public Overlay(Game game, int particleCount)
    {
        this.game = game;
        this.particleCount = particleCount;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
}
