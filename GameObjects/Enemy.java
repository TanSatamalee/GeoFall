package com.mygame.gdx.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;


public class Enemy extends Actor {

    /* Features of Main Sprite */
    private Polygon bounds;

    /* Imports Texture for Main Sprite */
    private Texture texture;

    /* Geo reference for collision detection */
    private Geo geo;

    public Enemy(Geo geo, int type, int level) {
        this.geo = geo;
        float[] enemy = {0f, 0f, 0, 0};
        float[] enemy1 = {50f, 50f, 50, 50};
        float[] enemy2 = {1030f, 50f, 50, 50};
        float[] enemy3 = {MathUtils.random(20f, 1060f), 0f, 50, 50};

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
            /* Enemy starts off on the left and moves horizontally */
            enemy = enemy1;
            SequenceAction sa = new SequenceAction(Actions.moveBy(980f, 0f, realTime, Interpolation.fade), Actions.moveBy(-980, 0f, realTime, Interpolation.fade));
            ra.setAction(sa);
        } else if (type == 2) {
            /* Enemy starts off on the right and moves horizontally */
            enemy = enemy2;
            SequenceAction sa = new SequenceAction(Actions.moveBy(-980f, 0f, realTime, Interpolation.fade), Actions.moveBy(980f, 0f, realTime, Interpolation.fade));
            ra.setAction(sa);
        } else if (type == 3) {
            /* Enemy starts off at the bottom of the screen and moves vertically */
            enemy = enemy3;
            MoveByAction ma = Actions.moveBy(0f, 2000f, realTime);
            ra.setAction(ma);
        } else if (type == 4) {
            /* Enemy starts off at the bottom of the screen and moves diagonally */
            enemy = enemy3;
            ParallelAction pa = Actions.parallel(Actions.moveBy(0f, 2000f, realTime), Actions.moveBy(MathUtils.random(-900f, 900f), 0f, realTime));
            ra.setAction(pa);
        }
        Enemy.this.addAction(ra);

        this.setX(enemy[0]);
        this.setY(enemy[1]);
        this.setWidth((int)enemy[2]);
        this.setHeight((int)enemy[3]);
        bounds = new Polygon(new float[]{0,0,getWidth(),0,getWidth(),getHeight(),0,getHeight()});
        bounds.setPosition(getX(), getY());
    }

    public void scroll(float y, float duration) {
        Enemy.this.addAction(Actions.moveBy(0f, y, duration));
    }

    public Polygon getBounds() {
        return bounds;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture,this.getX(),this.getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bounds.setPosition(getX(),getY());
        if (Intersector.overlapConvexPolygons(this.geo.getBounds(),this.getBounds())) {
            geo.death();
        }
    }

}
