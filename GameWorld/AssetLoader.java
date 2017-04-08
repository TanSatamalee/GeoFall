package com.mygame.gdx.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    private static Texture texture;
/*
    public static TextureRegion one, two, threeA, threeB, fourA, fourB, fourC, fourD
*/
    private static Preferences prefs;

    public static void load() {
        /*
        texture = new Texture(Gdx.files.internal("FILE_NAME"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        one = new TextureRegion(texture, X, Y, W, H);
        two = new TextureRegion(texture, X, Y, W, H);
        threeA = new TextureRegion(texture, X, Y, W, H);
        threeB = new TextureRegion(texture, X, Y, W, H);
        fourA = new TextureRegion(texture, X, Y, W, H);
        fourB = new TextureRegion(texture, X, Y, W, H);
        fourC = new TextureRegion(texture, X, Y, W, H);
        fourD = new TextureRegion(texture, X, Y, W, H);
        */

        prefs = Gdx.app.getPreferences("GeoFall");

        if (!prefs.contains("highscore")) {
            prefs.putInteger("highscore", 0);
        }
    }

    public static void setHighScore(int score) {
        prefs.putInteger("highscore", score);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highscore");
    }

    public static void dispose() {
        texture.dispose();
    }
}
