package com.obstacleavoid;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.obstacleavoid.screen.loading.LoadingScreen;

public class ObstacleAvoidGame extends Game {
    private AssetManager assetManager;
    private SpriteBatch spriteBatch;
    private static final Logger LOG = new Logger(ObstacleAvoidGame.class.getName(), Logger.DEBUG);

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
        assetManager = new AssetManager();
        assetManager.getLogger().setLevel( Logger.DEBUG );
        spriteBatch = new SpriteBatch();
		setScreen(new LoadingScreen(this));
	}

    public AssetManager getAssetManager()
    {
        return assetManager;
    }

    public SpriteBatch getSpriteBatch()
    {
        return spriteBatch;
    }
    @Override
    public void dispose()
    {
        assetManager.dispose();
        spriteBatch.dispose();
    }
}
