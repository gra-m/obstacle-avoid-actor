package com.obstacleavoid.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.screen.game.GameScreen;
import com.obstacleavoid.screen.game._old.GameScreenOld;

public class MenuScreen extends MenuScreenBase
{
    private static final Logger LOG = new Logger( MenuScreen.class.getName( ), Logger.DEBUG );


    public MenuScreen( ObstacleAvoidGame game )
    {
        super( game );
    }


    @Override
    protected Actor createUi()
    {
        Table table = new Table( );

        Skin uiskin = assetManager.get( AssetDescriptors.UI_SKIN );
        TextureAtlas gamePlayAtlas = assetManager.get( AssetDescriptors.GAMEPLAY_ATlAS );
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion( RegionNames.BACKGROUND );


        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );

        // playbutton
        TextButton playButton = new TextButton( "PLAY", uiskin );
        playButton.addListener( new ChangeListener( )
        {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                play( );
            }
        } );

        // high score button
        TextButton highScoreButton = new TextButton( "HIGHSCORE", uiskin );
        highScoreButton.addListener( new ChangeListener( )
        {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                showHighScore( );
            }
        } );

        // options button
        TextButton optionsButton = new TextButton( "OPTIONS", uiskin );
        optionsButton.addListener( new ChangeListener( )
        {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                showOptions( );
            }
        } );

        // quit button
        TextButton quitButton = new TextButton( "QUIT", uiskin );
        quitButton.addListener( new ChangeListener( )
        {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                quit( );
            }
        } );

        // button table setup
        Table buttonTable = new Table( uiskin );
        buttonTable.setBackground( RegionNames.PANEL );
        buttonTable.defaults( ).pad( 20 );
        buttonTable.add( playButton ).row( );
        buttonTable.add( highScoreButton ).row( );
        buttonTable.add( optionsButton ).row( );
        buttonTable.add( quitButton );
        buttonTable.center( );
        table.add( buttonTable );


        //  table setup
        table.center( );
        table.setFillParent( true );
        table.pack( );
        return table;

    }

    private void play()
    {
        LOG.debug( "play()" );
        obstacleAvoidGame.setScreen( new GameScreen( obstacleAvoidGame ) );
    }

    private void showHighScore()
    {
        LOG.debug( "showHighScore()" );
        obstacleAvoidGame.setScreen( new HighScoreScreen( obstacleAvoidGame ) );
    }

    private void showOptions()
    {
        LOG.debug( "showOptions()" );
        obstacleAvoidGame.setScreen( new OptionsScreen( obstacleAvoidGame ) );
    }

    private void quit()
    {
        LOG.debug( "quit()" );
        Gdx.app.exit( );
    }
}
