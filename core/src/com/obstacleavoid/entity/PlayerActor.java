package com.obstacleavoid.entity;

import static com.obstacleavoid.util.Common.MAX_PLAYER_SPEED;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.obstacleavoid.config.GameConfig;

public class PlayerActor extends ActorBase{

    private float delta;

    //
    public PlayerActor() {
        setCollisionRadius(GameConfig.PLAYER_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
        setDebug(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        update(delta);
    }

    private void update(float delta)
    {
        float xSpeed = 0;

        if ( Gdx.input.isKeyPressed( Input.Keys.RIGHT)) {
            xSpeed = MAX_PLAYER_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -MAX_PLAYER_SPEED;
        }

        this.setX (this.getX() + xSpeed);
        blockPlayerFromLeavingTheWorld( );
    }

    private void blockPlayerFromLeavingTheWorld()
    {
        float playerX = MathUtils.clamp( this.getX( ), 0f ,
                ( GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE ) );

        this.setPosition( playerX, this.getY( ) );
    }

}
