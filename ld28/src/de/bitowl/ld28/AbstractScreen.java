package de.bitowl.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen implements Screen{
	
	LDGame game;
	Stage stage;
	
	// letterbox camera
	Rectangle viewport;
	float virtualWidth=800;
	float virtualHeight=480;
	float virtualAspectRatio=virtualWidth/virtualHeight;
	
	public AbstractScreen(LDGame pGame){
		game = pGame;
		stage = new Stage(virtualWidth, virtualHeight);
		viewport = new Rectangle();
		
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);
		
		stage.act(delta);
		stage.draw();	
	}

	@Override
	public void resize(int width, int height) {
		resizeViewport(width,height);
	}

	public void resizeViewport(int width, int height){
		float aspectRatio=width/(float)height;

		float scale=1;
		float cropX=0;
		float cropY=0;

		// calculate scale and size of the letterbox
		if(aspectRatio > virtualAspectRatio){
			scale = (float)height/(float)virtualHeight;
			cropX = (width - virtualWidth*scale)/2f;
		}else if(aspectRatio < virtualAspectRatio){
			scale = (float)width/(float)virtualWidth;
			cropY = (height - virtualHeight*scale)/2f;
		}else{
			scale = (float)width/(float)virtualWidth;
		}

		// set the viewport
		viewport.set(cropX,cropY,virtualWidth*scale,virtualHeight*scale);
	}
	
	@Override
	public void show() {
	
		
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
