package com.mygame.gdx.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygame.gdx.GameObjects.Bg;
import com.mygame.gdx.GameObjects.Geo;

/**
 * Created by ssata_000 on 1/27/2017.
 */

public class GameWorld {

    private GameState currentState;

    private int runTime;

    private Stage start_screen;
    private Stage game_stage;
    private Stage end_screen;

    private Bg bg;
    private Geo geo;

    public enum GameState {
        READY, RESTART, RUNNING, END
    }

    public GameWorld() {
        /* NEEDS TO BE CHANGED TO READY WHEN START SCREEN IS PLACED. */
        currentState = GameState.READY;
        runTime = 0;
    }

    public void update(float delta) {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case RESTART:
                updateRestart(delta);
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
    private void updateReady(float delta) {
        /* Prepares the Start Screen. */
        start_screen = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(start_screen);

        /* Prepares the Game Screen to be played on. */
        game_stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(game_stage);
        geo = new Geo(300f, 1800f, 400, 50, this);
        bg = new Bg(0f, 0f, 1080, 2000, geo);
        game_stage.addActor(bg);
        game_stage.addActor(geo);

        /* Prepares the End Screen. */
        end_screen = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(end_screen);


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
        bg = new Bg(0f, 0f, 1080, 2000, geo);
        game_stage.addActor(bg);
        game_stage.addActor(geo);
    }

    private void updateRunning(float delta) {
        runTime += delta;
        if (delta > 0.15f) {
            delta = 0.15f;
        }
        game_stage = bg.levelManage(game_stage, delta, geo);
        game_stage.act();
        game_stage.draw();
    }

    /* Displays the end screen */
    private void updateEnd(float delta) {
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
}
