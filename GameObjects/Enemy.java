package com.mygame.gdx.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;


public class Enemy extends Actor {

    /* Features of Main Sprite */
    private Rectangle bounds;

    /* Imports Texture for Main Sprite */
    private Texture texture;

    /* Geo reference for collision detection */
    private Geo geo;

    public Enemy(Geo geo, int type, int level) {
        this.geo = geo;
        float[] enemy = {0f, 0f, 0, 0};
        float[] enemy1 = {50f, 50f, 50, 50};
        float[] enemy2 = {1030f, 50f, 50, 50};

        /* Set Geo's orientation and variables */
        this.setRotation(0);
        texture = new Texture(Gdx.files.internal("Enemy" + type + ".png"));

        RepeatAction ra = new RepeatAction();
        ra.setCount(RepeatAction.FOREVER);
        float realTime;
        if (level < 10) {
            realTime = 4f;
        } else if (level < 20) {
            realTime = 3f;
        } else if (level < 30) {
            realTime = 2f;
        } else {
            realTime = 2f;
        }
        if (type == 1) {
            enemy = enemy1;
            SequenceAction sa = new SequenceAction(Actions.moveBy(980f, 0f, realTime, Interpolation.fade), Actions.moveBy(-980, 0f, realTime, Interpolation.fade));
            ra.setAction(sa);
        } else if (type == 2) {
            enemy = enemy2;
            SequenceAction sa = new SequenceAction(Actions.moveBy(-980f, 0f, realTime, Interpolation.fade), Actions.moveBy(980f, 0f, realTime, Interpolation.fade));
            ra.setAction(sa);
        }
        Enemy.this.addAction(ra);

        this.setX(enemy[0]);
        this.setY(enemy[1]);
        this.setWidth((int)enemy[2]);
        this.setHeight((int)enemy[3]);
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void scroll(float y, float duration) {
        Enemy.this.addAction(Actions.moveBy(0f, y, duration));
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture,this.getX(),this.getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);    }

    @Override
    public void act(float delta) {
        if (this.geo.getBounds().overlaps(this.getBounds())) {
            geo.death();
        }
        bounds.setX((int)getX());
        bounds.setY((int)getY());
        super.act(delta);
    }

}
