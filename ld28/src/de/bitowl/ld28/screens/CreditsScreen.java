package de.bitowl.ld28.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.bitowl.ld28.LDGame;

public class CreditsScreen extends AbstractScreen {

	public CreditsScreen(LDGame pGame) {
		super(pGame);;
		TextureAtlas atlas = pGame.assets.get("textures/textures.pack",TextureAtlas.class);
		stage.addActor(new Image(atlas.findRegion("credits")));
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean keyDown(int keycode) {
				game.setScreen(new MenuScreen(game));
				return super.keyDown(keycode);
			}
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				game.setScreen(new MenuScreen(game));
				return super.touchDown(screenX, screenY, pointer, button);
			}
		});
		
	}

}
