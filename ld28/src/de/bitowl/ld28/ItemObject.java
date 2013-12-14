package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class ItemObject extends GameObject {
	public ItemObject(IngameScreen pScreen, TextureRegion pRegion) {
		super(pScreen, pRegion);
	}

	public abstract void collected();
}
