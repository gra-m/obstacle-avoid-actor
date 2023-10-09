package com.obstacleavoid.screen.game._old;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.screen.menu.MenuScreen;

@Deprecated
public class GameScreenOld implements Screen
{
    private static final Logger LOG = new Logger(GameScreenOld.class.getName(), Logger.DEBUG);
    private final ObstacleAvoidGame obstacleAvoidGame;
    private final AssetManager assetManager;
    private GameControllerOld controller;
    private GameRendererOld renderer;

    public GameScreenOld(ObstacleAvoidGame game ) {
        this.obstacleAvoidGame = game;
        this.assetManager = obstacleAvoidGame.getAssetManager();
    }

    @Override
    public void show()
    {
        this.controller = new GameControllerOld(obstacleAvoidGame);
        this.renderer = new GameRendererOld(obstacleAvoidGame.getSpriteBatch(), assetManager, controller);
    }

    @Override
    public void render( float delta )
    {
        renderer.render( delta );
        controller.update( delta );

        if ( controller.isGameOver( ) ) {
            obstacleAvoidGame.setScreen( new MenuScreen( obstacleAvoidGame ) );
        }
    }

    @Override
    public void resize( int width, int height )
    {
        renderer.resize( width, height );
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {
        dispose();
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
    }
}
