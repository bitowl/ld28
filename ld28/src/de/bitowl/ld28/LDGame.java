package de.bitowl.ld28;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;

import de.bitowl.ld28.screens.GameOverScreen;

public class LDGame extends Game {
	public AssetManager assets;
	boolean assetsLoaded;
	public boolean mapUsed;
	
	
	@Override
	public void create() {
		assets = new AssetManager();
		
		assets.setLoader(TiledMap.class, new AtlasTmxMapLoader(new InternalFileHandleResolver()));
		
		assets.load("maps/map1.tmx",TiledMap.class);
		assets.load("textures/textures.pack",TextureAtlas.class);
		assets.load("fonts/numbers.fnt",BitmapFont.class);
		assets.load("fonts/dialog.fnt",BitmapFont.class);
		
		// audio
		assets.load("audio/bow.ogg",Sound.class);
		assets.load("audio/explode.ogg",Sound.class);
		assets.load("audio/dig.ogg",Sound.class);
		assets.load("audio/gold.ogg",Sound.class);
		assets.load("audio/no_dig.ogg",Sound.class);
		assets.load("audio/heart.ogg",Sound.class);
		assets.load("audio/bottle.ogg",Sound.class);
		assets.load("audio/ground.ogg",Sound.class);
		assets.load("audio/hit.ogg",Sound.class);
		
	}
	
	public void render() {
		if(assetsLoaded){
			super.render();
		}else{
			if(assets.update()){
				setScreen(new GameOverScreen(this));
				assetsLoaded=true;
				System.out.println("finished loading");
			}else{
				// TODO draw loading bar
			}
		}
	}
	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
	}
	
}
