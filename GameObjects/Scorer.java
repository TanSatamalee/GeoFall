package com.mygame.gdx.GameObjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Scorer extends Actor {

    BitmapFont font;
    int score;

    public Scorer(int score) {
        font = new BitmapFont();
        font.setColor(0.5f, 0.4f, 0, 1);
        font.getData().setScale(4f);
        this.score = score;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        font.draw(batch, "Score: " + Integer.toString(score), 10f, 1900f);
        System.out.println(score);
    }

    public void changeScore(int score) {
        this.score = score;
    }

}
