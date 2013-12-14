package de.bitowl.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class IngameScreen extends AbstractScreen{

	
	TextureAtlas atlas;
	
	// map stuff
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	TiledMapTileLayer colLayer;
	TiledMapTileLayer destLayer; // layer that is containing destroyable stuff
	
	
	Player player;
	
	int resized; //TODO remove this workaround and really fix it (e.g. with a menu :P)
	
	public IngameScreen(LDGame pGame) {
		super(pGame);
		
		
		map = game.assets.get("maps/map1.tmx",TiledMap.class);
		atlas = game.assets.get("textures/textures.pack", TextureAtlas.class);
		
		renderer = new OrthogonalTiledMapRenderer(map);
		renderer.setView((OrthographicCamera) stage.getCamera());
		colLayer = (TiledMapTileLayer) map.getLayers().get("collision");
		colLayer.setVisible(false);
		destLayer = (TiledMapTileLayer) map.getLayers().get("destroyable");
		
		
		player = new Player(this);
		player.setY(colLayer.getHeight()*colLayer.getTileHeight()-player.getHeight());
		stage.addActor(player);
		
		
		// handle input
		Gdx.input.setInputProcessor(new GameInputProcessor());
		

	}
	@Override
	public void render(float delta) {
		// TODO remove this i3-resize workaround
		if(resized < 10){

			// resize at the beginning to set the letterbox right
			resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			resized++;
		}
		
		// move all the stuff around and stuff
		stage.act(delta);
		
		// scroll camera to the player
		stage.getCamera().position.set(player.getX(),player.getY(),0);
		stage.getCamera().update();
		
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);  // TODO move this into an own method in AbstractScreen
		
		renderer.setView((OrthographicCamera) stage.getCamera());
		renderer.render();
		
		stage.draw();
		// super.render(delta);
	}

	@Override
	public void dispose() {
		super.dispose();
		renderer.dispose();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		renderer.setView((OrthographicCamera)stage.getCamera());
	}
	
	class GameInputProcessor extends InputAdapter{
		@Override
		public boolean keyDown(int keycode) {
			switch(keycode){
				case Keys.LEFT:
				case Keys.RIGHT:
					player.speedX= Gdx.input.isKeyPressed(Keys.RIGHT)?1:0-(Gdx.input.isKeyPressed(Keys.LEFT)?1:0);
					break;
				case Keys.UP:
				case Keys.SPACE:
					player.jump();
					break;
				case Keys.Q:
					colLayer.setCell((int)( (player.getX() +player.getWidth()/2)/colLayer.getTileWidth()), (int)(player.getY()/colLayer.getTileHeight())-1, null);
					destLayer.setCell((int)( (player.getX() +player.getWidth()/2)/colLayer.getTileWidth()), (int)(player.getY()/colLayer.getTileHeight())-1, null);
					//colLayer.getCell((int)(player.getX()/colLayer.getTileWidth()), (int)(player.getY()/colLayer.getTileHeight())).setTile();
					break;
			}
			
			return super.keyDown(keycode);
		}
		@Override
		public boolean keyUp(int keycode) {
			switch(keycode){
				case Keys.LEFT:
				case Keys.RIGHT:
					player.speedX= Gdx.input.isKeyPressed(Keys.RIGHT)?1:0-(Gdx.input.isKeyPressed(Keys.LEFT)?1:0);
					break;
			}
			return super.keyUp(keycode);
		}
	}
	
}
