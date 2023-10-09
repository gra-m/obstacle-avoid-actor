package com.obstacleavoid.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.obstacleavoid.ObstacleAvoidGame;
import com.obstacleavoid.config.GameDifficulty;

// saved in userprofile dir .prefs on WINDOWS systems
public class GameManager
{
    public static final GameManager INSTANCE = new GameManager();
    private static final String HIGH_SCORE_KEY = "highscore";
    private static final String DIFFICULTY_KEY = "difficulty";
    private Preferences prefs;
    private int highScore;
    private GameDifficulty gameDifficulty;

    private GameManager(){
        prefs = Gdx.app.getPreferences( ObstacleAvoidGame.class.getSimpleName() );
        highScore = prefs.getInteger( HIGH_SCORE_KEY, 0 );
        String difficultyName = prefs.getString( DIFFICULTY_KEY, GameDifficulty.MEDIUM.name( ) );
        gameDifficulty = GameDifficulty.valueOf( difficultyName );
    }

    public void updateHighScore(int score) {
        if (score > highScore) {
            prefs.putInteger( HIGH_SCORE_KEY, score );
            prefs.flush(); // save prefs
            highScore = score;
        }
    }

    public void updateDifficulty(GameDifficulty difficulty) {
        if (difficulty != gameDifficulty) {
            prefs.putString( DIFFICULTY_KEY, difficulty.name( ) );
            prefs.flush();
            gameDifficulty = difficulty;
        }
    }

    public String getHighScoreString() {
        return String.valueOf( highScore );
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }
}
