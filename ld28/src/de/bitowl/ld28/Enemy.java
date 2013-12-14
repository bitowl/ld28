package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends GameObject {

	public Enemy(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("enem"));
	}

	public Enemy(IngameScreen pScreen, TextureRegion pRegion) {
		super(pScreen, pRegion);
	}

}
