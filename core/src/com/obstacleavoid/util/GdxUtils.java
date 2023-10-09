package com.obstacleavoid.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class GdxUtils {
    private GdxUtils(){}

    public static void clearScreen(){
        clearScreen(Color.BLACK);
    }

    public static void clearScreen(Color color) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // this is not needed, just use viewport.update(width, height, centre camera true)
    public static void setGdxWorldView(Camera camera, float WORLD_WIDTH, float WORLD_HEIGHT, int zoom) {
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();
    }

}
