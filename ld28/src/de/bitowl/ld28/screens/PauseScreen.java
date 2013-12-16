package de.bitowl.ld28.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitowl.ld28.LDGame;
import de.bitowl.ld28.StageInputProcessor;

public class PauseScreen extends AbstractScreen{

	IngameScreen screen;
	public PauseScreen(LDGame pGame, IngameScreen pScreen) {
		super(pGame);
		screen = pScreen;
		
		Table table = new Table();
		table.setSize(stage.getWidth(), stage.getHeight());
		
		Image title = new Image(screen.atlas.findRegion("title_break"));
		table.add(title).pad(30).row();
		
		Image continueB = new Image(screen.atlas.findRegion("button_continue"));
		continueB.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(screen);
			}
		});
		table.add(continueB).pad(30).row();
		
		Image quit = new Image(screen.atlas.findRegion("button_menu"));
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
		clear();
		super.render(delta);
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new StageInputProcessor(this){
			@Override
			public boolean keyDown(int keycode) {
				if(keycode == Keys.ESCAPE){
					game.setScreen(PauseScreen.this.screen);
				}
				return false;
			}
		});
	}
}
