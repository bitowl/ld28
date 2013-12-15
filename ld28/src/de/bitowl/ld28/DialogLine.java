package de.bitowl.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DialogLine extends Actor{
	IngameScreen screen;

	BitmapFont font;
	
	String text;
	float textTime;
	
	public DialogLine(IngameScreen pScreen){
		screen=pScreen;
		font = screen.game.assets.get("fonts/dialog.fnt",BitmapFont.class);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(text!=null){
			if(textTime>0){
				textTime-=delta;
				if(textTime<=0){
					text=null;
				}
			}
		}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(text == null){return;}
		
		if(screen.viewport.y>35){ // if the letterbox creates free space below the image, put the dialog lines there
			Gdx.gl.glViewport((int)screen.viewport.x,(int)screen.viewport.y-35,(int)screen.viewport.width,(int)screen.viewport.height);//Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		}
		float x = screen.stage.getCamera().position.x - screen.stage.getWidth()/2;
		float y = screen.stage.getCamera().position.y - screen.stage.getHeight()/2;
		batch.setTransformMatrix(batch.getTransformMatrix().trn(new Vector3(x,y,0)));

		font.drawWrapped(batch, text, 30,30, screen.stage.getWidth(), HAlignment.CENTER);
		batch.setTransformMatrix(new Matrix4());
		if(screen.viewport.y>35){
			Gdx.gl.glViewport((int)screen.viewport.x, (int)screen.viewport.y, (int)screen.viewport.width,(int)screen.viewport.height);
		}
		
	}
	public void display(String pText, float pTime){
		text = pText;
		textTime = pTime;
	}
}
