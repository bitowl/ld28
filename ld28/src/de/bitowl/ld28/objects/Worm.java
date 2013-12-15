package de.bitowl.ld28.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

import de.bitowl.ld28.AnimAction;
import de.bitowl.ld28.screens.IngameScreen;

public class Worm extends Enemy{

	public Worm(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("worm"));
		addAction(new AnimAction(new Animation(0.2f, pScreen.atlas.findRegions("worm")),true));
		defSpeedX=0.3f;
		life=3;
	}

}
