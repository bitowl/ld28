package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.bitowl.ld28.screens.IngameScreen;

public class LifeBar extends Actor{
	IngameScreen screen;
	TextureRegion heart_full,heart_half,heart_empty;
	public LifeBar(IngameScreen pScreen){
		screen=pScreen;
		heart_empty=screen.atlas.findRegion("heart_empty");
		heart_half=screen.atlas.findRegion("heart_half");
		heart_full=screen.atlas.findRegion("heart_full");
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		float x = screen.stage.getCamera().position.x - screen.stage.getWidth()/2;
		float y = screen.stage.getCamera().position.y + screen.stage.getHeight()/2 - 32;
		
		for(int i=1;i<=screen.player.max_life;i++){
			if(i<screen.player.life+0.2f){
				batch.draw(heart_full,x,y);
			}else if(i<screen.player.life+1){
				batch.draw(heart_half,x,y);
			}else{
				batch.draw(heart_empty,x,y);
			}
			x += 32;
		}
		
	}
}
