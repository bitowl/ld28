package de.bitowl.ld28;

public class Enemy extends GameObject {

	public Enemy(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("enem"));
	}
	
}
