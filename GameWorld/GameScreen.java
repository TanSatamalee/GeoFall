package com.mygame.gdx.GameWorld;

/**
 * Created by ssata_000 on 1/27/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygame.gdx.GameObjects.Bg;
import com.mygame.gdx.GameObjects.Geo;

public class GameScreen implements Screen {

    private GameWorld world;

    public GameScreen() {
        world = new GameWorld();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("GameScreen - resizing");
    }

    @Override
    public void show() {
        System.out.println("GameScreen - show called");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen - hide called");
    }

    @Override
    public void pause() {
        System.out.println("GameScreen - pause called");
    }

    @Override
    public void resume() {
        System.out.println("GameScreen - resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }
}
