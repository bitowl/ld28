package de.bitowl.ld28;

public class Ladder extends GameObject {

	public Ladder(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("ladder"));
	}
}
