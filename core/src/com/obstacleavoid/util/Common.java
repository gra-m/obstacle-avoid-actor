package com.obstacleavoid.util;

import java.util.Locale;

import static com.obstacleavoid.config.GameConfig.OBSTACLE_SIZE;
import static com.obstacleavoid.config.GameConfig.PLAYER_SIZE;

// Not necessarily common but all in the same place for experimentation, that's all.
public class Common
{
    public static final Locale LOCALE = Locale.ENGLISH;
    public static final int DEFAULT_CELL_SIZE = 1;
    public static final float DEFAULT_ZOOM_LEVEL = 1.0f;
    public static final float MAX_PLAYER_SPEED = 0.25f;
    public static final float DRAW_ADJUST_HALF_PLAYER = PLAYER_SIZE / 2;
    public static final float DRAW_ADJUST_HALF_OBJECT = OBSTACLE_SIZE / 2;


    private Common(){}
}
