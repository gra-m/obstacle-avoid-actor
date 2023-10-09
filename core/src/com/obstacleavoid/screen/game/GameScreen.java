package com.obstacleavoid.screen.game;

import static com.obstacleavoid.util.Common.MAX_PLAYER_SPEED;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.assets.RegionNames;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.entity.PlayerActor;
import com.obstacleavoid.util.GdxUtils;
import com.obstacleavoid.util.ViewportUtils;
import com.obstacleavoid.util.debug.DebugCameraController;


/* As when using stage it is not possible to not mix model and view, this class will be a mix of view
(formerly GameRenderer)  and control (formerlyGameController) meaning this can be a v large class..
 */
public class GameScreen extends ScreenAdapter {
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
   private Sound hitSound;

   private float startPlayerX = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
   private float startPlayerY = GameConfig.PLAYER_SIZE / 2f;

   private DebugCameraController debugCameraController;
   private PlayerActor player;

   // 19 fields!

    public GameScreen(ObstacleAvoidGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.font = assetManager.get(AssetDescriptors.UI_FONT_32);
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

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATlAS);
        // Actors
        player = new PlayerActor();
        player.setRegion(gamePlayAtlas.findRegion(RegionNames.PLAYER));
        player.setPosition(startPlayerX, startPlayerY);
        gameStage.addActor(player);
    }

    //
    @Override
    public void render(float delta) {

        // handle debug camera input (all controls)
        debugCameraController.handleDebugInput(delta);
        // configure to camera:
        debugCameraController.applyTo(camera);

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

        batch.begin();

        drawGamePlay();

        batch.end();

        // Stage calls its own begin/end automatically.
        gameStage.act();
        gameStage.draw();

    }

    private void drawGamePlay() {
    }

    private void renderUi() {
        batch.setProjectionMatrix(uiCamera.combined);

        batch.begin();

        //create LIVES text
        String livesText = "LIVES " +  lives;
        //add text to layout
        layout.setText(font,livesText);
        // use BitMapFont to draw itself with a SpriteBatch and layout and position from bottom left
        font.draw(batch, layout, GameConfig.HUD_PADDING , GameConfig.HUD_HEIGHT - layout.height);


        //create SCORE text
        String scoreText = "SCORE " +  score;
        //add text to layout
        layout.setText(font, scoreText);
        // use BitMapFont to draw itself with a SpriteBatch and layout and position from bottom left
        font.draw(batch, layout, GameConfig.HUD_WIDTH - (layout.width + GameConfig.HUD_PADDING), GameConfig.HUD_HEIGHT - layout.height);


        batch.end();


    }
    private void renderDebug() {
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();
        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer);

    }

    private void drawDebug() {
    }


}
