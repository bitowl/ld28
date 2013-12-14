package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Worm extends Enemy{

	public Worm(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("worm"));
		addAction(new AnimAction(new Animation(0.2f, pScreen.atlas.findRegions("worm")),true));
		speedX=0.3f;
		life=3;
	}

}
