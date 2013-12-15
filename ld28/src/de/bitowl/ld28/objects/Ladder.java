package de.bitowl.ld28.objects;

import de.bitowl.ld28.screens.IngameScreen;

public class Ladder extends GameObject {

	public Ladder(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("ladder"));
	}
}
