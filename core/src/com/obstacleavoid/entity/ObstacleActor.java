package com.obstacleavoid.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;
import com.obstacleavoid.config.GameConfig;

public class ObstacleActor extends ActorBase implements Pool.Poolable {
    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean hitAlready;

    public ObstacleActor(){
        setCollisionRadius(GameConfig.OBSTACLE_BOUNDS_RADIUS);
        setSize(GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //  I didn't see the point in putting this in a separate update method
        setY(getY() - ySpeed);
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
    public boolean isPlayerColliding(PlayerActor player) {
        Circle playerBounds = player.getCollisionShape();

        hitAlready = Intersector.overlaps( playerBounds, this.getCollisionShape() );

        return hitAlready;
    }
    @Override
    public void reset() {
        setRegion(null);
        hitAlready = false;

    }
}
