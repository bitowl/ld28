package de.bitowl.ld28.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;

import de.bitowl.ld28.AnimAction;
import de.bitowl.ld28.screens.IngameScreen;

public class Worm extends Enemy{

	public Worm(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("worm"));
		addAction(new AnimAction(new Animation(0.2f, pScreen.atlas.findRegions("worm")),true));
		defSpeedX=0.3f*(MathUtils.randomBoolean()?1:-1);
		life=3;
		setOriginX(getWidth()/2);
	}
	@Override
	public void hitLeft(){
		defSpeedX=0.3f;
		setScaleX(1);
	}
	@Override
	public void hitRight() {
		defSpeedX=-0.3f;
		setScaleX(-1);
	}

}
