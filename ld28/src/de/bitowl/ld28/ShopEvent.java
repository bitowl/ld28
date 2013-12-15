package de.bitowl.ld28;

import de.bitowl.ld28.screens.IngameScreen;

public class ShopEvent extends Event {

	@Override
	public void happen(IngameScreen pScreen) {
		pScreen.game.setScreen(pScreen.shop);
		Event.eventFinished(pScreen);
	}

}
