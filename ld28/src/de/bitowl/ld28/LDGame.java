package de.bitowl.ld28;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;

import de.bitowl.ld28.screens.LoadingScreen;

public class LDGame extends Game {
	public AssetManager assets;
	public boolean assetsLoaded;
	public boolean mapUsed;


	private LoadingScreen loadingScreen;

	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		
		assets = new AssetManager();

		assets.setLoader(TiledMap.class, new AtlasTmxMapLoader(
				new InternalFileHandleResolver()));

		assets.load("maps/map1.tmx", TiledMap.class);
		assets.load("textures/textures.pack", TextureAtlas.class);
		assets.load("fonts/numbers.fnt", BitmapFont.class);
		assets.load("fonts/dialog.fnt", BitmapFont.class);

		// audio
		assets.load("audio/bow.ogg", Sound.class);
		assets.load("audio/explode.ogg", Sound.class);
		assets.load("audio/dig.ogg", Sound.class);
		assets.load("audio/gold.ogg", Sound.class);
		assets.load("audio/no_dig.ogg", Sound.class);
		assets.load("audio/heart.ogg", Sound.class);
		assets.load("audio/bottle.ogg", Sound.class);
		assets.load("audio/ground.ogg", Sound.class);
		assets.load("audio/hit.ogg", Sound.class);
		assets.load("audio/bought.ogg", Sound.class);
		assets.load("audio/shop.ogg", Sound.class);
		assets.load("audio/no_money.ogg", Sound.class);
		assets.load("audio/no_need.ogg", Sound.class);

		assets.load("audio/event1_1.ogg", Sound.class);
		assets.load("audio/event1_2.ogg", Sound.class);
		assets.load("audio/event1_3.ogg", Sound.class);
		assets.load("audio/event2.ogg", Sound.class);
		assets.load("audio/event3_1.ogg", Sound.class);
		assets.load("audio/event3_2.ogg", Sound.class);
		assets.load("audio/event4.ogg", Sound.class);
		assets.load("audio/event5_1.ogg", Sound.class);
		assets.load("audio/event5_2.ogg", Sound.class);
		assets.load("audio/event6_1.ogg", Sound.class);
		assets.load("audio/event6_2.ogg", Sound.class);
		assets.load("audio/event7_1.ogg", Sound.class);
		assets.load("audio/event7_2.ogg", Sound.class);
		assets.load("audio/event8_1.ogg", Sound.class);
		assets.load("audio/event8_2.ogg", Sound.class);
		assets.load("audio/event9_1.ogg", Sound.class);
		assets.load("audio/event9_2.ogg", Sound.class);
	}

	public void render() {
		if (assetsLoaded) {
			super.render();
		} else {
			loadingScreen.render(0);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
	}

}
