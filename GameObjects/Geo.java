package com.mygame.gdx.GameObjects;

/**
 * Created by ssata_000 on 1/27/2017.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.math.Interpolation;
import com.mygame.gdx.GameWorld.GameWorld;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class Geo extends Actor{

    /* Gaming Variables*/
    private boolean isAlive;

    /* Keeps track of which side is hanging */
    private int hanger;

    /* Features of Main Sprite */
    private int widthGeo;
    private int heightGeo;

    float currAngle = 0f;
    Rectangle bounds;

    private GameWorld gw;

    /* Imports Texture for Main Sprite */
    private Texture texture = new Texture(Gdx.files.internal("Geo.png"));

    public Geo(float x, float y, int width, int height, GameWorld gw) {
        /* Set Geo's orientation and variables */
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setRotation(0);
        widthGeo = width;
        heightGeo = height;
        isAlive = true;
        hanger = 1;
        bounds = new Rectangle((int)x, (int)y, width, height);
        this.gw = gw;

        /* Starts swinging once sprite has been created */
        Geo.this.setOrigin(width/10, height/2);
        RepeatAction ra = new RepeatAction();
        ra.setCount(RepeatAction.FOREVER);
        SequenceAction sa = new SequenceAction(Actions.rotateBy(-180f, 3f, Interpolation.fade), Actions.rotateBy(180f, 3f, Interpolation.fade));
        ra.setAction(sa);
        Geo.this.addAction(ra);
    }

    /* Handles scrolling of sprite. Does not move if the distance change does not happen. */
    public float scroll(float duration) {
        RepeatAction ra = Geo.this.swing();
        if (ra == null) {
            return 0f;
        }
        Geo.this.clearActions();
        float dist = Geo.this.getY() - 1800f;
        if (dist < 0f) {
            Geo.this.addAction(Actions.parallel(Actions.moveBy(0f, -dist, duration), ra));
            return -dist;
        } else {
            Geo.this.addAction(ra);
            return 0f;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    /* Handles constant swinging motion */
    public RepeatAction swing() {
        if (Geo.this.getY() < 1500f) {
            Geo.this.addAction(Actions.moveBy(0f, 300f, 1f));
        }

        /* If game is over then dont allow any more screen touches */
        if (!isAlive) {
            return null;
        }

        /* Updates which side is swinging */
        hanger += 1;

        /* Changes the orientation before calculating the new rotation angle */
        float currentAngle = Geo.this.getRotation();
        currentAngle += 180f;
        while (currentAngle < 0f) {
            currentAngle += 360f;
        }
        currentAngle = currentAngle % 360f;
        if (!changeOrigin(hanger)) {
            return null;
        }
        System.out.println("HELLO" + currentAngle);
        currAngle = currentAngle;
        float angle;
        if (currentAngle < 90f) {
            angle = 2 * currentAngle + 180f;
        } else {
            angle = 2 * currentAngle - 540f;
        }
        RepeatAction ra = new RepeatAction();
        ra.setCount(RepeatAction.FOREVER);
        float time = 2f;
        float realTime = time / 180f * angle;
        if (realTime < 0f) {
            realTime = realTime * -1f;
        }
        SequenceAction sa = new SequenceAction(Actions.rotateBy(-angle, realTime, Interpolation.fade), Actions.rotateBy(angle, realTime, Interpolation.fade));
        ra.setAction(sa);
        return ra;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture,this.getX(),this.getY(),this.getOriginX(),this.getOriginY(),this.getWidth(),
                this.getHeight(),this.getScaleX(), this.getScaleY(),this.getRotation(),0,0,
                texture.getWidth(),texture.getHeight(),false,false);    }

    @Override
    public void act(float delta) {
        bounds.setX((int)getX());
        bounds.setY((int)getY());
        if (!isAlive) {
            return;
        }
        super.act(delta);
    }

    /* Used to signal if game is over */
    public void death() {
        isAlive = false;
        gw.endGame();
    }

    /* Changes Geo's origin so that he may swing from the correct side */
    private boolean changeOrigin(int hanger) {
        float currentAngle = Geo.this.getRotation();
        float lengthChange = widthGeo * 4 / 5;
        float posX = Geo.this.getX();
        float posY = Geo.this.getY();
        float newX = lengthChange * MathUtils.cosDeg(currentAngle) + posX;
        float newY = lengthChange * MathUtils.sinDeg(currentAngle) + posY;
        if (!screenMargin(newX, newY)) {
            return false;
        }
        Geo.this.setPosition(newX, newY);
        Geo.this.setRotation(currentAngle + 180f);
        return true;
    }

    /* Checks if the new X and Y coordinates are within the screen. */
    private boolean screenMargin(float x, float y) {
        if (x < 0f || x > 1080f) {
            return false;
        } else if (y < 0f || y > 2000f) {
            return false;
        }
        return true;
    }
}
