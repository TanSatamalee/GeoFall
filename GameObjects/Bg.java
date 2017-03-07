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

/**
 * Created by ssata_000 on 2/22/2017.
 */

public class Bg extends Actor {
    public Texture texture;

    private Geo geo;
    private Array<Enemy> enemies = new Array<Enemy>();

    private float travelled = 0f;

    public Bg(float x, float y, int width, int height, Geo geo) {
        /* Set Geo's orientation and variables */
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        this.setRotation(0);
        this.geo = geo;
        texture = new Texture(Gdx.files.internal("Background.png"));

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                scrollMaster();
                return true;
            }
        });

    }

    private void scrollMaster() {
        float duration = 2f;
        float dist = geo.scroll(duration);
        travelled += dist;
        if (enemies != null && enemies.size > 0) {
            for (Enemy enemy : enemies) {
                if (enemy.getY() > 2000f) {
                    enemy.remove();
                    enemies.removeValue(enemy, true);
                    return;
                }
                enemy.scroll(dist, duration);
            }
        }
    }

    private int level = 0;
    private int levelScore = 100;
    private int score;
    private Preferences prefs = Gdx.app.getPreferences("GeoFall");

    /* Handles all level balances and creation. */
    public Stage levelManage(Stage stage, float delta, Geo geo) {
        Rectangle geoBounds = geo.getBounds();
        for (Enemy enemy : enemies) {
            if (geoBounds.overlaps(enemy.getBounds())) {
                geo.death();
                return stage;
            }
        }
        if (level == 0) {
            if (!prefs.contains("HighScore")) {
                prefs.putInteger("HighScore", 0);
            } else {
                score = prefs.getInteger("HighScore");
            }
            if (score < levelScore) {
                if (enemies.size < MathUtils.round(travelled / 1000)) {
                    Enemy temp = new Enemy(MathUtils.random(1,2), level);
                    enemies.add(temp);
                    stage.addActor(temp);
                }
            } else {
                level += 1;
            }
        } else if (level < 5) {
            if (score < levelScore * level + levelScore) {
                if (enemies.size < MathUtils.round(travelled / 900)) {
                    Enemy temp = new Enemy(MathUtils.random(1,2), level);
                    enemies.add(temp);
                    stage.addActor(temp);
                }
            } else {
                level += 1;
            }
        } else if (level < 10) {
            if (score < levelScore * level + levelScore) {
                if (enemies.size < MathUtils.round(travelled / 800)) {
                    Enemy temp = new Enemy(MathUtils.random(1,2), level);
                    enemies.add(temp);
                    stage.addActor(temp);
                }
            } else {
                level += 1;
            }
        }
        return stage;
    }

    /* Allows geo to update score */
    public void addScore(int newScore) {
        if (newScore >= 0) {
            score += newScore;
        }
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
