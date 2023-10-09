package com.obstacleavoid.screen.game._old;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.assets.AssetDescriptors;
import com.obstacleavoid.common.GameManager;
import com.obstacleavoid.config.GameConfig;
import com.obstacleavoid.config.GameDifficulty;
import com.obstacleavoid.entity._old.Background;
import com.obstacleavoid.entity._old.Obstacle;
import com.obstacleavoid.entity._old.Player;
import com.obstacleavoid.util.Common;

import static com.obstacleavoid.config.GameConfig.OBSTACLE_SIZE;
import static com.obstacleavoid.config.GameConfig.PLAYER_SCORES_AFTER;
import static com.obstacleavoid.util.Common.MAX_PLAYER_SPEED;

@Deprecated
public class GameControllerOld
{
    // constants
    private static final Logger LOG = new Logger(GameControllerOld.class.getName(), Logger.DEBUG);

    // fields
    private Player player;
    private int lives = GameConfig.PLAYER_INITIAL_LIVES;
    private Array< Obstacle > obstacles = new Array<>( );
    private float obstacleTimer;
    private float scoreTimer;
    private int score;
    private int displayScore;
    private Background background;
    private Pool<Obstacle> obstaclePool;
    private float startPlayerX = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2;
    private float startPlayerY = 1 - Common.DRAW_ADJUST_HALF_PLAYER;
    private Sound crashSound;

    private final ObstacleAvoidGame obstacleAvoidGame;
    private final AssetManager assetManager;

    // constructors
    public GameControllerOld(ObstacleAvoidGame game ){
        this.obstacleAvoidGame = game;
        this.assetManager = obstacleAvoidGame.getAssetManager();
        init();
    }

    private void init() {
        player = new Player( );
        background = new Background();
        background.setPosition(0f, 0f);
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        crashSound = assetManager.get( AssetDescriptors.CRASH_WAV );

        // position player
        player.setPosition( startPlayerX, startPlayerY );
        // create Obstacle Pool
        obstaclePool = Pools.get(Obstacle.class, 40);
    }


    // public methods
    public void update( float delta )
    {

        if ( isPlayerCollidingWithObstacle( player ) ) {
            LOG.debug( "Collision detected" );
            crashSound.play();
            lives--;
            if ( isGameOver( ) ) {
                LOG.debug( "Game Over" );
                GameManager.INSTANCE.updateHighScore( score );
            } else {
                restart( );
            }
        }
        
        if (!isGameOver()) {
            updatePlayer( );
            updateObstacles( delta );
            updateScore( delta );
            updateDisplayScore( delta );
        }

    }

    private void restart() {
        obstaclePool.freeAll( obstacles );
        obstacles.clear();
        player.setPosition( startPlayerX, startPlayerY );

    }

    public boolean isGameOver()
    {
        return lives <= 0;
    }

    // private methods
    private boolean isPlayerCollidingWithObstacle( Player player )
    {

        for ( Obstacle ob : obstacles ) {
            if ( ob.notHitAlready() && ob.isPlayerColliding( player ) ) {
                return true;
            }
        }
        return false;
    }

    private void updatePlayer()
    {
        float xSpeed = 0;

        if ( Gdx.input.isKeyPressed( Input.Keys.RIGHT)) {
            xSpeed = MAX_PLAYER_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -MAX_PLAYER_SPEED;
        }
        
        player.setX (player.getX() + xSpeed);
        blockPlayerFromLeavingTheWorld( );
    }

    private void blockPlayerFromLeavingTheWorld()
    {
        float playerX = MathUtils.clamp( player.getX( ), 0f ,
                ( GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE ) );

        player.setPosition( playerX, player.getY( ) );
    }

    private void updateObstacles( float delta )
    {
        for ( Obstacle o : obstacles ) {
            o.update( );
        }
        createNewObstacle( delta );
        removePassedObstacles();
    }

    private void removePassedObstacles( )
    {
        if ( obstacles.size > 0 ) {
            Obstacle first = obstacles.first( );

            float minY = -first.getWidth( );

            if ( first.getY( ) <= minY ) {
                obstacles.removeValue(first, true);
                obstaclePool.free(first);
            }
        }
    }

    private void createNewObstacle( float delta )
    {
        obstacleTimer += delta;

        if ( obstacleTimer >= GameConfig.OBSTACLES_SPAWN_EVERY ) {
            float min = 0;
            float max = GameConfig.WORLD_WIDTH - OBSTACLE_SIZE;
            float obstacleX = MathUtils.random( min, max );

            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = obstaclePool.obtain();
            GameDifficulty difficultyLevel = GameManager.INSTANCE.getGameDifficulty( );
            obstacle.setDifficulty(difficultyLevel.getObjectSpeed());
            obstacle.setPosition( obstacleX, obstacleY );

            obstacles.add( obstacle );
            obstacleTimer = 0f;
        }
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

    // Getters
    public Player getPlayer()
    {
        return player;
    }

    public int getLives()
    {
        return lives;
    }

    public Array< Obstacle > getObstacles()
    {
        return obstacles;
    }

    public Background getBackground() {
        return background;
    }

    public int getDisplayScore()
    {
        return displayScore;
    }
}
