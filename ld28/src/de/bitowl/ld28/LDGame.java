package de.bitowl.ld28;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class LDGame extends Game {
	AssetManager assets;
	boolean assetsLoaded;
	
	@Override
	public void create() {
		assets = new AssetManager();
		
		assets.setLoader(TiledMap.class, new AtlasTmxMapLoader(new InternalFileHandleResolver()));
		
		assets.load("maps/map1.tmx",TiledMap.class);
		assets.load("textures/textures.pack",TextureAtlas.class);
	}
	
	public void render() {
		if(assetsLoaded){
			super.render();
		}else{
			if(assets.update()){
				setScreen(new IngameScreen(this));
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
