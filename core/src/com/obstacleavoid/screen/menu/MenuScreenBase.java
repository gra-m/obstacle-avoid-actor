package com.obstacleavoid.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.util.GdxUtils;

public abstract class MenuScreenBase extends ScreenAdapter
{
    private static final Logger LOG = new Logger( MenuScreenBase.class.getName( ), Logger.DEBUG );
    protected final ObstacleAvoidGame obstacleAvoidGame;
    protected final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;

    protected MenuScreenBase( ObstacleAvoidGame game )
    {
        this.obstacleAvoidGame = game;
        this.assetManager = obstacleAvoidGame.getAssetManager( );
    }

    @Override
    public void show()
    {
        viewport = new FitViewport( GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT );
        stage = new Stage( viewport, obstacleAvoidGame.getSpriteBatch( ) );


        Gdx.input.setInputProcessor( stage );
        stage.addActor(createUi( ));
    }

    protected abstract Actor createUi();

    @Override
    public void render( float delta )
    {
        GdxUtils.clearScreen( );
        stage.act( );
        stage.draw( );
    }

    @Override
    public void resize( int width, int height )
    {
        viewport.update( width, height, true );

    }

    @Override
    public void hide()
    {
        dispose( );
    }

    @Override
    public void dispose()
    {
        stage.dispose( );
    }

    // Not required, use skins!
    protected static ImageButton createButton( TextureAtlas uiAtlas, String upRegionName, String downRegionName )
    {

        TextureRegion upRegion = uiAtlas.findRegion( upRegionName );
        TextureRegion downRegion = uiAtlas.findRegion( downRegionName );
        Drawable upDrawable = new TextureRegionDrawable( upRegion );
        Drawable downDrawable = new TextureRegionDrawable( downRegion );

        return new ImageButton( upDrawable, downDrawable );

    }

    // Not required, use skins!
    protected static ImageButton createButton( TextureAtlas uiAtlas, String upRegionName )
    {

        TextureRegion upRegion = uiAtlas.findRegion( upRegionName );
        Drawable upDrawable = new TextureRegionDrawable( upRegion );

        return new ImageButton( upDrawable );

    }
}
