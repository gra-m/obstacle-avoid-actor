package com.obstacleavoid.config;

public class GameConfig {
    // pixels
    public static final float WIDTH = 480f;
    public static final float HEIGHT = 800f;
    // world units
    public static final float WORLD_WIDTH = 6.0f;
    public static final float WORLD_HEIGHT = 10.0f;
    public static final float HUD_WIDTH = 480f;
    public static final float HUD_HEIGHT = 800f;

    // rotate around
    public static final float WORLD_CENTER_X = WORLD_WIDTH/2f;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT/2f;

    public static final float OBSTACLES_SPAWN_EVERY = 0.25f;
    public static final float PLAYER_SCORES_AFTER = 1.25f;
    public static final int PLAYER_INITIAL_LIVES = 3;

    public static final float EASY_OBSTACLE_SPEED = 0.1f;
    public static final float MEDIUM_OBSTACLE_SPEED = 0.15f;
    public static final float HARD_OBSTACLE_SPEED = 0.18f;

    // object sizing
    public static final float PLAYER_BOUNDS_RADIUS = 0.4f;
    public static final float PLAYER_SIZE = 2 * PLAYER_BOUNDS_RADIUS;
    public static final float OBSTACLE_BOUNDS_RADIUS = 0.3f;
    public static final float OBSTACLE_SIZE = 2 * OBSTACLE_BOUNDS_RADIUS;

    private GameConfig(){}
}
