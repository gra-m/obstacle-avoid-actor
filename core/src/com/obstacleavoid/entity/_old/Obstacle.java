package com.obstacleavoid.entity._old;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;
import com.obstacleavoid.config.GameDifficulty;

import static com.obstacleavoid.config.GameConfig.OBSTACLE_BOUNDS_RADIUS;
import static com.obstacleavoid.config.GameConfig.OBSTACLE_SIZE;

@Deprecated
public class Obstacle extends GameObjectBase implements Pool.Poolable
{
    private boolean hitAlready;

    private float ySpeed = GameDifficulty.MEDIUM.getObjectSpeed();

    public Obstacle(){
        super(OBSTACLE_BOUNDS_RADIUS);
        setSize( OBSTACLE_SIZE, OBSTACLE_SIZE );
    }

    public void update() {
        super.setY(  super.getY() - ySpeed);
    }

    public boolean isPlayerColliding(Player player) {
        Circle playerBounds = player.getBounds();

        hitAlready = Intersector.overlaps( playerBounds, getBounds() );

        return hitAlready;
    }

    public boolean notHitAlready() {
        return !hitAlready;
    }


    public void setDifficulty( float difficultySpeed ) {
        this.ySpeed = difficultySpeed;
    }

    @Override
    public void reset( )
    {
        hitAlready = false;
    }

}
