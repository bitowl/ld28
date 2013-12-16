package de.bitowl.ld28.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.bitowl.ld28.LDGame;

public class InstructionsScreen extends AbstractScreen {

	public InstructionsScreen(LDGame pGame) {
		super(pGame);
		TextureAtlas atlas = pGame.assets.get("textures/textures.pack",TextureAtlas.class);
		Image image=new Image(atlas.findRegion("instructions"));
		image.setX(50);
		image.setY(85);
		stage.addActor(image);
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean keyDown(int keycode) {
				game.setScreen(new IngameScreen(game));
				return super.keyDown(keycode);
			}
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button) {
				game.setScreen(new IngameScreen(game));
				return super.touchDown(screenX, screenY, pointer, button);
			}
		});

	}
	@Override
	public void render(float delta) {
		clear();
		super.render(delta);
	}
	

}
