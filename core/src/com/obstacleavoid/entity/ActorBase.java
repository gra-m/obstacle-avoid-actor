package com.obstacleavoid.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Logger;

public abstract class ActorBase extends Actor {
    private static final Logger LOG = new Logger(ActorBase.class.getName(), Logger.DEBUG);

    private final Circle collisionShape = new Circle();
    private TextureRegion region;

    // empty constructor == compatible with Pools (no existing textures in pools)
    public ActorBase(){}

    public void setCollisionRadius( float radius) {
        collisionShape.setRadius(radius);
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public void draw(Batch batch, float parentAlpha) {
        // Do not have to set projection matrix or begin/end since the stage handles these
        if (this.region == null) {
            LOG.debug("Region not set on Actor");
            return;
        }

        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(),getWidth(), getHeight(), getScaleX(),getScaleY(),getRotation());

    }

    // private

    private void updateCollisionShape() {
        // get circle centre as that is how it draws
        float halfWidth = getWidth() / 2;
        float halfHeight = getHeight() / 2;

        // get x and y from actor (bottom left) and adjust for circular shape of this class
        collisionShape.setPosition(getX() + halfWidth, getY() + halfHeight);
        // we do not override setPosition, it is called automatically in base class
    }

    // Route position and size changes auto from baseclass to this overridden method:
    @Override
    protected void positionChanged() {
        updateCollisionShape();

    }

    @Override
    protected void sizeChanged() {
        updateCollisionShape();
    }

    // default is for rectangle here overridden for circle
    @Override
    protected void drawDebugBounds(ShapeRenderer shapeRenderer) {
        if(!getDebug()) {
            return;
        }
        shapeRenderer.x(collisionShape.x, collisionShape.y, 0.1f);
        shapeRenderer.circle(collisionShape.x, collisionShape.y, collisionShape.radius);
    }


}
