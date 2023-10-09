package com.obstacleavoid.config;

public enum GameDifficulty
{
    EASY(GameConfig.EASY_OBSTACLE_SPEED),
    MEDIUM(GameConfig.MEDIUM_OBSTACLE_SPEED),
    HARD(GameConfig.HARD_OBSTACLE_SPEED);
    
    private final float objectSpeed;

    GameDifficulty(float objectSpeed) {
        this.objectSpeed = objectSpeed;
    }

    public float getObjectSpeed () {
        return objectSpeed;
    }

    public boolean isEasy( ) {
        return this == EASY;
    }
    public boolean isMedium( ) {
        return this == MEDIUM;
    }
    public boolean isHard() {
        return this == HARD;
    }
}
