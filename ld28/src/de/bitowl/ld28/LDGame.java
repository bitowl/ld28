package de.bitowl.ld28;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;

import de.bitowl.ld28.screens.MenuScreen;
import de.bitowl.ld28.screens.WinScreen;

public class LDGame extends Game {
	public AssetManager assets;
	boolean assetsLoaded;
	public boolean mapUsed;
	
	
	SpriteBatch batch;
	Texture emptyT, fullT;
	NinePatch full, empty;
	BitmapFont font;
	
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
		assets.load("audio/bought.ogg",Sound.class);
		assets.load("audio/shop.ogg",Sound.class);
		assets.load("audio/no_money.ogg",Sound.class);
		assets.load("audio/no_need.ogg",Sound.class);

		assets.load("audio/event1_1.ogg",Sound.class);
		assets.load("audio/event1_2.ogg",Sound.class);
		assets.load("audio/event1_3.ogg",Sound.class);
		assets.load("audio/event2.ogg",Sound.class);
		assets.load("audio/event3_1.ogg",Sound.class);
		assets.load("audio/event3_2.ogg",Sound.class);
		assets.load("audio/event4.ogg",Sound.class);
		assets.load("audio/event5_1.ogg",Sound.class);
		assets.load("audio/event5_2.ogg",Sound.class);
		assets.load("audio/event6_1.ogg",Sound.class);
		assets.load("audio/event6_2.ogg",Sound.class);
		assets.load("audio/event7_1.ogg",Sound.class);
		assets.load("audio/event7_2.ogg",Sound.class);
		assets.load("audio/event8_1.ogg",Sound.class);
		assets.load("audio/event8_2.ogg",Sound.class);
		assets.load("audio/event9_1.ogg",Sound.class);
		assets.load("audio/event9_2.ogg",Sound.class);
		
		// loading bar
		font=new BitmapFont();
		batch=new SpriteBatch();
		emptyT=new Texture(Gdx.files.internal("textures/bar.png"));
		fullT=new Texture(Gdx.files.internal("textures/bar_full.png"));
		empty=new NinePatch(new TextureRegion(emptyT,24,24),8,8,8,8);
		full=new NinePatch(new TextureRegion(fullT,24,24),8,8,8,8);

	}
	
	public void render() {
		if(assetsLoaded){
			super.render();
		}else{
			if(assets.update()){
				setScreen(new MenuScreen(this));
				assetsLoaded=true;
				System.out.println("finished loading");
				
				// dispose our loading only stuff
				batch.dispose();
				font.dispose();
				emptyT.dispose();
				fullT.dispose();
			}else{
				Gdx.gl.glClearColor(0, 0, 0, 1);
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				// draw loading bar
				batch.begin();
				empty.draw(batch, 40, 225, 720, 30);
				if(assets.getProgress()>0){
					full.draw(batch, 40, 225, assets.getProgress()*720, 30);
				}
				font.drawMultiLine(batch,(int)(assets.getProgress()*100)+"% loaded",400,247,0, BitmapFont.HAlignment.CENTER);
				batch.end();
			}
		}
	}
	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		
	}
	
}
