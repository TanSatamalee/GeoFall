package com.mygame.gdx;

import com.badlogic.gdx.Game;
import com.mygame.gdx.GameWorld.AssetLoader;
import com.mygame.gdx.GameWorld.GameScreen;

public class GeoFall extends Game {
	GameScreen gamescreen;
	
	@Override
	public void create () {
		AssetLoader.load();
		gamescreen = new GameScreen();
		setScreen(gamescreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
