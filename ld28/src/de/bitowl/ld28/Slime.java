package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Slime extends Enemy {

	public Slime(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("slime"));
		Animation wobble=new Animation(0.08f, screen.atlas.findRegions("slime"));
		wobble.setPlayMode(Animation.LOOP_PINGPONG);
		addAction(new AnimAction(wobble));
		speedX=-0.2f;
	}

}
