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

public class Bg extends Actor {
    public Texture texture;

    private GameWorld gm;

    public Bg(float x, float y, int width, int height, GameWorld gm) {
        /* Set Geo's orientation and variables */
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setRotation(0);
        this.gm = gm;
        texture = new Texture(Gdx.files.internal("Background.png"));

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                scrollMaster();
                return true;
            }
        });

    }

    /* Calls the main scroll master to progress in game. */
    private void scrollMaster() {
        gm.scrollMaster();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture,this.getX(),this.getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
