package de.bitowl.ld28.objects;

import com.badlogic.gdx.math.MathUtils;

import de.bitowl.ld28.screens.IngameScreen;

public class Arrow extends GameObject {

	public Arrow(IngameScreen pScreen) {
		super(pScreen,pScreen.atlas.findRegion("arrow"));
		setOrigin(getWidth()/2,getHeight()/2);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		setRotation(MathUtils.radDeg*MathUtils.atan2(speedY, speedX));
		if(onGround){
			remove();
		}
	}

}
