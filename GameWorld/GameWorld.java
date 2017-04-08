package com.mygame.gdx.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygame.gdx.GameObjects.Bg;
import com.mygame.gdx.GameObjects.End;
import com.mygame.gdx.GameObjects.Enemy;
import com.mygame.gdx.GameObjects.Geo;
import com.mygame.gdx.GameObjects.Scorer;
import com.mygame.gdx.GameObjects.Start;

public class GameWorld {

    /* Current state of the app. */
    private GameState currentState;

    /* Current runTime of the app. */
    private int runTime;

    /* Screens that will be displayed within app. */
    private Stage start_screen;
    private Stage game_stage;
    private Stage end_screen;

    /* Actors that will be on the Game Stage. */
    private Start start;
    private End end;
    private Bg bg;
    private Geo geo;
    private Scorer sc;
    private Array<Enemy> enemies = new Array<Enemy>();

    /* Variables that involve scrolling in game. */
    private float travelled = 0f;
    private float duration;
    private float dist;

    /* Variables involved in game mechanics and scoring. */
    private int level = 0;
    private int enemyPass = 0;
    private int score;
    private int highScore;
    private Preferences prefs = Gdx.app.getPreferences("GeoFall");

    /* States that the game may be in. */
    public enum GameState {
        STARTUP, READY, RESTART, RUNNING, END
    }

    public GameWorld() {
        currentState = GameState.STARTUP;
        runTime = 0;
    }

    public void update(float delta) {
        switch (currentState) {
            case STARTUP:
                updateStart(delta);
                currentState = GameState.READY;
                break;

            case READY:
                updateReady(delta);
                break;

            case RESTART:
                updateRestart(delta);
                currentState = GameState.RUNNING;
                break;

            case RUNNING:
                updateRunning(delta);
                break;

            case END:
                updateEnd(delta);
                break;
        }
    }

    /* Prepares all stages that is needed for the game to run. */
    private void updateStart(float delta){
        /* Prepares the Start Screen. */
        start_screen = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(start_screen);
        start = new Start(0f, 0f, 1080, 2000, this);
        start_screen.addActor(start);

        /* Prepares the Game Screen to be played on. */
        game_stage = new Stage(new ScreenViewport());
        geo = new Geo(300f, 1800f, 400, 50, this);
        bg = new Bg(0f, 0f, 1080, 2000, this);
        sc = new Scorer(0);
        game_stage.addActor(bg);
        game_stage.addActor(geo);
        game_stage.addActor(sc);

        /* Prepares the End Screen. */
        end_screen = new Stage(new ScreenViewport());
        end = new End(0f, 0f, 1080, 2000, this);
        end_screen.addActor(end);
    }

    /* Displays the start screen. */
    private void updateReady(float delta) {
        /* Displays the Start Screen. */
        runTime += delta;
        if (delta > 0.15f) {
            delta = 0.15f;
        }
        start_screen.act();
        start_screen.draw();
    }

    /* On a restart we don't need to recreate the start/end screen again. */
    private void updateRestart(float delta) {
        game_stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(game_stage);
        geo = new Geo(300f, 1800f, 400, 50, this);
        bg = new Bg(0f, 0f, 1080, 2000, this);
        sc = new Scorer(0);
        game_stage.addActor(bg);
        game_stage.addActor(geo);
        game_stage.addActor(sc);
        runGame();
    }

    /* Acts and draws the game stage. */
    private void updateRunning(float delta) {
        Gdx.input.setInputProcessor(game_stage);
        runTime += delta;
        if (delta > 0.15f) {
            delta = 0.15f;
        }
        levelManage();
        game_stage.act();
        game_stage.draw();
    }

    /* Displays the end screen */
    private void updateEnd(float delta) {
        Gdx.input.setInputProcessor(end_screen);
        runTime += delta;
        if (delta > 0.15f) {
            delta = 0.15f;
        }
        end_screen.act();
        end_screen.draw();
    }

    /* Used by Start Actor to set the game in motion. */
    public void runGame() {
        currentState = GameState.RUNNING;
    }

    /* Used by Bg Actor to stop the game when Geo dies. */
    public void endGame() {
        currentState = GameState.END;
    }

    /* Used by Restart Actor to reset the game. */
    public void restartGame() {
        currentState = GameState.RESTART;
    }

    public int getRunTime() {
        return runTime;
    }

    /* Handles all level balances and creation. */
    public void levelManage() {
        if (level == 0) {
            if (!prefs.contains("HighScore")) {
                prefs.putInteger("HighScore", 0);
                highScore = 0;
            } else {
                highScore = prefs.getInteger("HighScore");
            }
            level += 1;
        } else {
            if (enemyPass < level * 10) {
                if (enemies.size < MathUtils.round(travelled / 800)) {
                    Enemy temp = new Enemy(geo, MathUtils.random(1,4), level);
                    enemies.add(temp);
                    game_stage.addActor(temp);
                }
            } else {
                level += 1;
            }
        }
    }

    /* Handles the game mechanics involving swinging, enemy management, distance travelled, etc. */
    public void scrollMaster() {
        duration = 2f;
        dist = geo.scroll(duration);
        travelled += dist;

        /* Increases score for distance travelled. */
        addScore(MathUtils.round(dist/100f));

        /* Checks to see if any enemies are past the screen and if they are then remove them from the stage. */
        if (enemies != null && enemies.size > 0) {
            for (Enemy enemy : enemies) {
                if (enemy.getY() > 2000f) {
                    enemy.remove();
                    enemies.removeValue(enemy, true);
                    enemyPass += 1;
                    /* Increases score for each enemy passed. */
                    addScore(10);
                } else {
                    enemy.scroll(dist, duration);
                }
            }
        }
        sc.changeScore(score);
    }

    /* Allows geo to update score */
    public void addScore(int newScore) {
        if (newScore >= 0) {
            score += newScore;
        }
    }
}
