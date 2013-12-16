package de.bitowl.ld28.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.bitowl.ld28.LDGame;

public class LoadingScreen extends AbstractScreen {

	SpriteBatch batch;
	Texture emptyT, fullT;
	NinePatch full, empty;
	BitmapFont font;
	int resized; 
	
	public LoadingScreen(LDGame pGame) {
		super(pGame);

		
		batch=(SpriteBatch) stage.getSpriteBatch();
		// loading bar
		font = new BitmapFont();
	//	batch = new SpriteBatch();
		emptyT = new Texture(Gdx.files.internal("textures/bar.png"));
		fullT = new Texture(Gdx.files.internal("textures/bar_full.png"));
		empty = new NinePatch(new TextureRegion(emptyT, 24, 24), 8, 8, 8, 8);
		full = new NinePatch(new TextureRegion(fullT, 24, 24), 8, 8, 8, 8);
	}

	@Override
	public void show() {

	}
	@Override
	public void render(float delta) {
		clear();
		setViewport();
		// workaround 
		if(resized < 10){

			// resize at the beginning to set the letterbox right
			resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			resized++;
		}
		if (game.assets.update()) {
			game.setScreen(new MenuScreen(game));
			game.assetsLoaded = true;
			System.out.println("finished loading");

			// dispose our loading only stuff
			//batch.dispose();
			font.dispose();
			emptyT.dispose();
			fullT.dispose();
		} else {
			// draw loading bar
			//batch.setProjectionMatrix(stage.getCamera().combined);
			batch.begin();
			
			empty.draw(batch, 40, 225, 720, 30);
			if (game.assets.getProgress() > 0) {
				full.draw(batch, 40, 225, game.assets.getProgress() * 720, 30);
			}
			font.drawMultiLine(batch, (int) (game.assets.getProgress() * 100)
					+ "% loaded", 400, 247, 0, BitmapFont.HAlignment.CENTER);
			batch.end();
		}
//		super.render(delta);
	}
}
