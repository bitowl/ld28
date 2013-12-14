package de.bitowl.ld28;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen implements Screen{
	LDGame game;
	Stage stage;
	public AbstractScreen(LDGame pGame){
		game = pGame;
		stage = new Stage(800,480);
	}
	
	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO implement letterbox camera and one fixed resolution?
		stage.getCamera().viewportWidth=width;
		stage.getCamera().viewportHeight=height;
		// stage.getCamera().position.set(stage.getWidth(),stage.getHeight()/2, 0);
		stage.getCamera().update();
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
	
}
