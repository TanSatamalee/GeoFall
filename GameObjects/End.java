package com.mygame.gdx.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygame.gdx.GameWorld.GameWorld;

/**
 * Created by ssata_000 on 2/22/2017.
 */

public class End extends Actor {
    public Texture texture;

    public GameWorld gw;

    public End(float x, float y, int width, int height, GameWorld gw) {
        /* Set Geo's orientation and variables */
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setRotation(0);
        this.gw = gw;
        texture = new Texture(Gdx.files.internal("startScreen.png"));

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                endGame();
                return true;
            }
        });
    }

    /* Changes the game state to RESTART. */
    private void endGame() {
        gw.restartGame();
    }


    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}