package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {
    private AssetManager manager;
    public GameScreen gamescreen;

    @Override
    public void create() {
        manager = new AssetManager();

        manager.load("player.png", Texture.class);
        manager.load("enemigo.png", Texture.class);
        manager.load("laserEnemigo.png", Texture.class);
        manager.load("aliado.png", Texture.class);

        manager.finishLoading();

        gamescreen = new GameScreen(this);

        setScreen(gamescreen);
    }

    public AssetManager getManager() {
        return this.manager;
    }

}
