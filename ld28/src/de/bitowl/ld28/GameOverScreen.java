package de.bitowl.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverScreen extends AbstractScreen{

	TextureAtlas atlas;
	public GameOverScreen(LDGame pGame) {
		super(pGame);
		atlas=game.assets.get("textures/textures.pack",TextureAtlas.class);
		
		Table table = new Table();
		table.setSize(stage.getWidth(), stage.getHeight());
		
		Image title = new Image(atlas.findRegion("shop"));
		table.add(title).pad(30).row();
		
		Image restart = new Image(atlas.findRegion("button_restart"));
		restart.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				IngameScreen.resetMap(game);
				game.setScreen(new IngameScreen(game));
			}
		});
		table.add(restart).pad(10).row();
		
		Image quit = new Image(atlas.findRegion("button_menu"));
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
			}
		});
		table.add(quit).pad(10).row();
		
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		super.render(delta);
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new StageInputProcessor(this){
			@Override
			public boolean keyDown(int keycode) {
				if(keycode == Keys.ESCAPE){
					game.setScreen(new MenuScreen(game));
				}
				return false;
			}
		});
	}
}
