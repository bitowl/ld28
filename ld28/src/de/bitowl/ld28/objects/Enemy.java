package de.bitowl.ld28.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.bitowl.ld28.screens.IngameScreen;

public class Enemy extends GameObject {

	// speed this enemy should always have
	float defSpeedX;
	float defSpeedY;
	
	public Enemy(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("enem"));
	}

	public Enemy(IngameScreen pScreen, TextureRegion pRegion) {
		super(pScreen, pRegion);
	}

	@Override
	public void act(float delta) {
		if(defSpeedX!=0 && speedX!=defSpeedX){
			speedX=defSpeedX;
		}
		if(defSpeedY!=0 && speedY!=defSpeedY){
			speedY=defSpeedY;
		}
		
		if(screen.viewRect.contains(getRectangle())){
			System.out.println("move enemy");
			super.act(delta);
		}
	}
}
