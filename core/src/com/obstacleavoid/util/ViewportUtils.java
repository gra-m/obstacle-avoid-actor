package com.obstacleavoid.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.obstacleavoid.util.Common.LOCALE;
import static com.obstacleavoid.util.Common.DEFAULT_CELL_SIZE;


public class ViewportUtils
{
    private static final Logger LOG = new Logger(ViewportUtils.class.getName(), Logger.DEBUG);

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer) {
        drawGrid(viewport, renderer, DEFAULT_CELL_SIZE);
    }

    private static void drawGrid(Viewport viewport, ShapeRenderer renderer, int cellSize)
    {
        if (viewport == null || renderer == null ) {
            throw new IllegalArgumentException("Viewport or ShapeRenderer parameter were not supplied");
        } else if (cellSize < DEFAULT_CELL_SIZE) {
            cellSize = DEFAULT_CELL_SIZE;
        }

        Color oldColor = new Color(renderer.getColor());

        int worldWidth = (int) viewport.getWorldWidth();
        int worldHeight = (int) viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        for (int x = -doubleWorldWidth; x < doubleWorldWidth; x+= cellSize) {
           renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
        }
        for (int y = -doubleWorldHeight; y < doubleWorldHeight; y+= cellSize) {
            renderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }
        // draw xy axis red
        renderer.setColor(Color.RED);
        renderer.line(0, -doubleWorldHeight, 0, doubleWorldHeight);
        renderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);
        // draw world bounds
        renderer.setColor(Color.GREEN);
        renderer.line(0, worldHeight, worldWidth, worldHeight);
        //renderer.line(-worldWidth, -worldHeight, worldWidth, -worldHeight);
        renderer.line(worldWidth, 0, worldWidth, worldHeight);
        //renderer.line(-worldWidth, -worldHeight, -worldWidth, worldHeight);

        renderer.end();

        renderer.setColor(oldColor);
    }

    public static void debugPixelPerUnit(Viewport viewport) {
        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // get pixels per (world) unit
        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        LOG.debug(String.format(LOCALE,"xPPU = %f yPPU = %f", xPPU, yPPU));


    }

    private ViewportUtils(){}

}
