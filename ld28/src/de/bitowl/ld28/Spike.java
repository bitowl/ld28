package de.bitowl.ld28;

public class Spike extends Enemy {

	public Spike(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("spikes"));
		life=Float.POSITIVE_INFINITY; // do not kill our spikes
	}

}
