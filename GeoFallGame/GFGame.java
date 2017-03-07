package com.mygame.gdx.GeoFallGame;

/**
 * Created by ssata_000 on 1/27/2017.
 */

import com.badlogic.gdx.Game;
import com.mygame.gdx.GameWorld.GameScreen;
import com.mygame.gdx.GameWorld.AssetLoader;

public class GFGame extends Game {

    @Override
    public void create() {
        System.out.println("Game Created!");
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }

}
