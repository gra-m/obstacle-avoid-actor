package com.obstacleavoid.screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.common.GameManager;
import com.obstacleavoid.config.GameDifficulty;


public class OptionsScreen extends MenuScreenBase
{
    private static final Logger LOG = new Logger( OptionsScreen.class.getName( ), Logger.DEBUG );
    private ButtonGroup< CheckBox > checkBoxButtonGroup; // enables radiobutton functionality
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public OptionsScreen( ObstacleAvoidGame game )
    {
        super( game );
    }

    protected Actor createUi() {
        TextureAtlas gamePlayAtlas = assetManager.get( AssetDescriptors.GAMEPLAY_ATlAS );
        Skin uiSkin = assetManager.get( AssetDescriptors.UI_SKIN );
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion( RegionNames.BACKGROUND );

        Table table = new Table();
        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );
        table.defaults().pad(15);

        // label
        Label label = new Label( "DIFFICULTY", uiSkin );

        // checkboxes with listener into ButtonGroup
        easy = formattedCheckBox(GameDifficulty.EASY.name(), uiSkin);
        medium = formattedCheckBox(GameDifficulty.MEDIUM.name(), uiSkin);
        hard = formattedCheckBox(GameDifficulty.HARD.name(), uiSkin);

        checkBoxButtonGroup = new ButtonGroup<>( easy, medium, hard );
        GameDifficulty checkThisDifficulty = GameManager.INSTANCE.getGameDifficulty();
        checkBoxButtonGroup.setChecked( checkThisDifficulty.name() );

        ChangeListener difficultyChanged = new ChangeListener( )
        {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                difficultyChanged();
            }
        };

        easy.addListener( difficultyChanged );
        medium.addListener( difficultyChanged );
        hard.addListener( difficultyChanged );

        // back
        TextButton backButton = new TextButton( "BACK", uiSkin );
        backButton.addListener( new ChangeListener( )
        {
            @Override
            public void changed( ChangeEvent event, Actor actor )
            {
                back();
            }
        } );

        // uiSkin content table
        Table contentTable = new Table( uiSkin );
        contentTable.defaults().pad( 10 );
        contentTable.setBackground( RegionNames.PANEL );
        contentTable.add(label).row();
        contentTable.add(easy).row();
        contentTable.add(medium).row();
        contentTable.add(hard).row();
        contentTable.add( backButton );
        contentTable.pack();

        table.add( contentTable );
        table.center();
        table.setFillParent( true );
        table.pack(); // this alone does what table.center and setFillParent achieve turn them off to see. todo delete

        return table;

    }

    private static CheckBox formattedCheckBox( String name, Skin skin ) {
        CheckBox checkBox = new CheckBox( name, skin );
        checkBox.left().pad( 8 );
        checkBox.getLabelCell().padLeft( 8 );
        return checkBox;
    }

    private void difficultyChanged() {
        LOG.debug( "difficultyChanged()" );
        CheckBox checked = checkBoxButtonGroup.getChecked();

        if (checked == easy) {
            LOG.debug( "easy" );
            GameManager.INSTANCE.updateDifficulty( GameDifficulty.EASY );
        } else if (checked == medium) {
            LOG.debug( "medium" );
            GameManager.INSTANCE.updateDifficulty( GameDifficulty.MEDIUM );
        } else if (checked == hard) {
            LOG.debug( "hard" );
            GameManager.INSTANCE.updateDifficulty( GameDifficulty.HARD );
        }

    }

    private void back()
    {
        LOG.debug( "back()" );
        obstacleAvoidGame.setScreen( new MenuScreen( obstacleAvoidGame ) );
    }

}
