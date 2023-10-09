package com.obstacleavoid.entity._old;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

@Deprecated
public abstract class GameObjectBase
{
    private float x;
    private float y;
    private float width = 1f;
    private float height = 1f;
    private Circle bounds;

    GameObjectBase(float BOUNDS_RADIUS) {
        bounds = new Circle(x, y, BOUNDS_RADIUS);
        
    }


    // fixme remove calls to this when not debug
    public void drawDebug( ShapeRenderer renderer) {
        renderer.x(bounds.x, bounds.y, 0.1f);
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }

    public void setSize ( float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }


    /**
     * Pick up and place used for initial position
     */
    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
        // whenever a game object is repositioned its bounds also have to be updated.
        updateBounds();
    }

    private void updateBounds( )
    {
        float adjustmentAlignsCircleToTextureWidth = width/2f;
        float adjustmentAlignsCircleToTextureHeight = height/2f;
        bounds.setPosition(x + adjustmentAlignsCircleToTextureWidth, y + adjustmentAlignsCircleToTextureHeight );
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
    public Circle getBounds() {
        return bounds;
    }

    public void setX( float x ) {
        this.x = x;
        updateBounds() ;
    }
    public void setY( float y) {
        this.y = y;
        updateBounds();
    }

}
