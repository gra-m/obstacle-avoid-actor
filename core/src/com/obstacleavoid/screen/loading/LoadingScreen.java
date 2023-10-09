package com.obstacleavoid.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.screen.menu.MenuScreen;
import com.obstacleavoid.util.GdxUtils;

public class LoadingScreen extends ScreenAdapter
{
    private static final Logger LOG = new Logger(LoadingScreen.class.getName(), Logger.DEBUG);
    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH/2;
    private static final  float PROGRESS_BAR_HEIGHT = 60f;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private float progress;
    private float waitTime = 0.75f; // pause after assetManager.update()
    private boolean changeScreen;

    private final ObstacleAvoidGame game;
    private final AssetManager assetManager;

    public LoadingScreen(ObstacleAvoidGame obstacleAvoidGame) {
        this.game = obstacleAvoidGame;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show( )
    {
        camera = new OrthographicCamera(  );
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer(  );

        assetManager.load(AssetDescriptors.UI_FONT_32);
        assetManager.load(AssetDescriptors.GAMEPLAY_ATlAS);
        assetManager.load( AssetDescriptors.UI_SKIN );  // atlas/skin/json files should have same name 'uiskin'
        assetManager.load( AssetDescriptors.CRASH_WAV );


    }

    @Override
    public void render( float delta )
    {
        update(delta);

        GdxUtils.clearScreen(  );
        viewport.apply();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        shapeRenderer.end();

        if (changeScreen) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void draw( ) {
        float progressBarX = (GameConfig.HUD_WIDTH -PROGRESS_BAR_WIDTH) / 2f;
        float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) /2f;

        shapeRenderer.rect(
                progressBarX,
                progressBarY,
                progress * PROGRESS_BAR_WIDTH,
                PROGRESS_BAR_HEIGHT);
    }

    private void update( float delta ) {
       waitMillis(10);

       progress = assetManager.getProgress();

       // == checkin with assetManager == true on completed load
       if (assetManager.update()) {
           waitTime -= delta;

           if (waitTime <= 0) {
               changeScreen = true;
           }
       }
    }

    @Override
    public void resize( int width, int height )
    {
        viewport.update(width, height, true);
    }

    @Override
    public void hide( )
    {
    dispose();
    }

    @Override
    public void dispose( )
    {
        shapeRenderer.dispose();
    }

    // private
    private static void waitMillis(long millis) {

        try {
            Thread.sleep(millis);
        } catch ( InterruptedException e ) {
            LOG.debug(String.format("Log wait interrupted @ waitMillis {} ",  e));
        }
    }

}
