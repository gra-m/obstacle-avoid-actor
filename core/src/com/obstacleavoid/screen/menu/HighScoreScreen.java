package com.obstacleavoid.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.common.GameManager;

public class HighScoreScreen extends MenuScreenBase
{
    private static final Logger LOG = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);


    public HighScoreScreen(ObstacleAvoidGame game) {
        super(game);
    }

    protected Actor createUi() {
    Table table = new Table(  );

        Skin uiskin = assetManager.get( AssetDescriptors.UI_SKIN );
        TextureAtlas gameplayAtlas = assetManager.get( AssetDescriptors.GAMEPLAY_ATlAS );
        BitmapFont font = assetManager.get(AssetDescriptors.UI_FONT_32);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion( RegionNames.BACKGROUND );



        // set mainTable
        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );
        table.center();
        table.setFillParent( true );

        // highScoreText Label
        Label highScoreText = new Label("HIGHSCORE", uiskin);

        // highScore Label
        Label highScore = new Label( GameManager.INSTANCE.getHighScoreString( ), uiskin);

        // backButton
        //ImageButton backButton = createButton( uiAtlas, RegionNames.BACK, RegionNames.BACK_PRESSED );
        TextButton backButton = new TextButton( "BACK", uiskin );
        backButton.addListener( new ChangeListener( )
        {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                back();
            }
        } );

        // set contentTable
        Table contentTable = new Table( uiskin );
        contentTable.setBackground( RegionNames.PANEL );
        contentTable.defaults().pad( 20 );
        contentTable.center();
        contentTable.add(highScoreText).row();
        contentTable.add(highScore).row();
        contentTable.add(backButton);


        // add contentTable to Table and Table to Stage
        table.add(contentTable);
        table.pack();
        return table;
    }

    protected void back() {
        LOG.debug( "back()" );
        obstacleAvoidGame.setScreen( new MenuScreen( obstacleAvoidGame ) );
    }

}
