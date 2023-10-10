package com.obstacleavoid.screen.game;


import static com.obstacleavoid.config.GameConfig.OBSTACLE_SIZE;
import static com.obstacleavoid.config.GameConfig.PLAYER_SCORES_AFTER;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.common.GameManager;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.config.GameDifficulty;
import com.obstacleavoid.entity.ObstacleActor;
import com.obstacleavoid.entity.PlayerActor;
import com.obstacleavoid.screen.menu.MenuScreen;
import com.obstacleavoid.util.GdxUtils;
import com.obstacleavoid.util.ViewportUtils;
import com.obstacleavoid.util.debug.DebugCameraController;


/* As when using stage it is not possible to not mix model and view, this class will be a mix of view
(formerly GameRenderer)  and control (formerlyGameController) meaning this can be a v large class..
 */
public class GameScreen extends ScreenAdapter {
    private static final Logger LOG = new Logger(GameScreen.class.getName(),Logger.DEBUG);
    private final ObstacleAvoidGame game;
    private AssetManager assetManager;
    private final SpriteBatch batch;
    private final GlyphLayout layout = new GlyphLayout();
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage gameStage;
    private ShapeRenderer renderer;
    private OrthographicCamera uiCamera;
    private Viewport uiViewport;
    private BitmapFont font;
    private float obstacleTimer;
    private float scoreTimer;
    private int lives = GameConfig.PLAYER_INITIAL_LIVES;
    private int score;
    private int displayScore;

    private float startPlayerX = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
    private float startPlayerY = GameConfig.PLAYER_SIZE / 2f;

    private TextureAtlas gamePlayAtlas;
    private TextureRegion obstacleRegion;
    private TextureRegion backgroundRegion;
    private Sound crashSound;

    private DebugCameraController debugCameraController;

    private Pool<ObstacleActor> obstaclePool = Pools.get(ObstacleActor.class);
    private final Array<ObstacleActor> obstacles = new Array<>();
    private ObstacleActor obstacle;
    private PlayerActor player;

    // 19 fields!

    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATlAS);
        this.font = assetManager.get(AssetDescriptors.UI_FONT_32);
        this.crashSound = assetManager.get(AssetDescriptors.CRASH_WAV);
        this.batch = game.getSpriteBatch();
    }

    //


    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

        gameStage = new Stage(viewport, batch);
        gameStage.setDebugAll(true); // we want debug renders for everything in this stage.
        renderer = new ShapeRenderer();
        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, uiCamera);

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
        obstacleRegion = gamePlayAtlas.findRegion(RegionNames.OBSTACLE);
        backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        Image background = new Image(backgroundRegion);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

        // Player Actor
        player = new PlayerActor();
        player.setRegion(gamePlayAtlas.findRegion(RegionNames.PLAYER));
        player.setPosition(startPlayerX, startPlayerY);
        gameStage.addActor(background);
        gameStage.addActor(player);
    }

    //
    @Override
    public void render(float delta) {

        // handle debug camera input (all controls)
        debugCameraController.handleDebugInput(delta);
        // configure to camera:
        debugCameraController.applyTo(camera);
        update(delta);

        // Clear
        GdxUtils.clearScreen();

        // use gameplay viewport:
        viewport.apply();
        // render gameplay
        renderGamePlay();

        // use UI viewport:
        uiViewport.apply();
        // render UI
        renderUi();

        // use gameplay viewport:
        viewport.apply();
        // render debug
        renderDebug();

    }

    private void update(float delta) {

        if (isPlayerCollidingWithObstacle(player)) {
            LOG.debug("Collision detected");
            crashSound.play();
            lives--;
            if (isGameOver()) {
                LOG.debug("Game Over");
                GameManager.INSTANCE.updateHighScore(score);
                game.setScreen(new MenuScreen(game));
            } else {
                restart();
            }
        }

        if (!isGameOver()) {
            updateScore(delta);
            updateDisplayScore(delta);
        }

        createNewObstacleAddToStage(delta);
        removePassedObstacles();
    }

    private void removePassedObstacles() {
        if (obstacles.size > 0) {
            ObstacleActor first = obstacles.first();

            float minY = -first.getWidth();

            if (first.getY() <= minY) {
                obstacles.removeValue(first, true);

                // removes any actor from its parent BUT not immediate
                first.remove();
                // return to pool
                obstaclePool.free(first);
            }
        }
    }

    private void createNewObstacleAddToStage(float delta) {
        obstacleTimer += delta;

        if (obstacleTimer >= GameConfig.OBSTACLES_SPAWN_EVERY) {
            float min = 0;
            float max = GameConfig.WORLD_WIDTH - OBSTACLE_SIZE;
            float obstacleX = MathUtils.random(min, max);

            float obstacleY = GameConfig.WORLD_HEIGHT;

            ObstacleActor obstacle = obstaclePool.obtain();
            GameDifficulty difficultyLevel = GameManager.INSTANCE.getGameDifficulty();
            obstacle.setYSpeed(difficultyLevel.getObjectSpeed());
            obstacle.setPosition(obstacleX, obstacleY);
            obstacle.setRegion(obstacleRegion);

            obstacles.add(obstacle);
            gameStage.addActor(obstacle);
            obstacleTimer = 0f;
        }
    }


    // Always necessary to update viewports with width and height
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        uiViewport.update(width, height, true);

        ViewportUtils.debugPixelPerUnit(viewport);

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    // private
    private void renderGamePlay() {
        batch.setProjectionMatrix(camera.combined);

        // just 1 render call
        gameStage.act();
        gameStage.draw();

    }


    private void renderUi() {
        batch.setProjectionMatrix(uiCamera.combined);

        batch.begin();

        //create LIVES text
        String livesText = "LIVES " + lives;
        //add text to layout
        layout.setText(font, livesText);
        // use BitMapFont to draw itself with a SpriteBatch and layout and position from bottom left
        font.draw(batch, layout, GameConfig.HUD_PADDING, GameConfig.HUD_HEIGHT - layout.height);


        //create SCORE text
        String scoreText = "SCORE " + score;
        //add text to layout
        layout.setText(font, scoreText);
        // use BitMapFont to draw itself with a SpriteBatch and layout and position from bottom left
        font.draw(batch, layout, GameConfig.HUD_WIDTH - (layout.width + GameConfig.HUD_PADDING), GameConfig.HUD_HEIGHT - layout.height);


        batch.end();

    }

    private void renderDebug() {
        ViewportUtils.drawGrid(viewport, renderer);

    }

    // Game Logic (Now mixed in with rendering class);

    private void restart() {
        // thinking about it, his way achieves everything with only ONE loop, I suspect more than one used
        // eg. with freeAll:
        for (int i = 0; i > obstacles.size; i ++) {
            ObstacleActor obA = obstacles.get(i);
            //remove from parent == stage but not immediate
            //obA.remove();
            //So:
            gameStage.getActors().removeValue(obA, true);
            //free given obstacle from pool
            obstaclePool.free(obA);
            obstacles.removeIndex(i);
        }

        player.setPosition(startPlayerX, startPlayerY);

    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    private void updateScore( float delta ) {
        scoreTimer += delta;

        if (scoreTimer >= PLAYER_SCORES_AFTER ) {
            score += MathUtils.random( 1, 5 );
            scoreTimer = 0.0f;
        }
    }

    private void updateDisplayScore( float delta ) {
        if (displayScore < score) {
            displayScore =  Math.min( score,  displayScore + (int) (80 * delta) );
        }
    }

    private boolean isPlayerCollidingWithObstacle( PlayerActor player )
    {

        for ( ObstacleActor ob : obstacles ) {
            if ( ob.notHitAlready() && ob.isPlayerColliding( player ) ) {
                return true;
            }
        }
        return false;
    }

}
