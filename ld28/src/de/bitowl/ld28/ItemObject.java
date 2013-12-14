package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class ItemObject extends GameObject {
	public ItemObject(IngameScreen pScreen, TextureRegion pRegion) {
		super(pScreen, pRegion);
		life = Float.POSITIVE_INFINITY;
		hitDamage = 0;
	}

	public abstract void collected();
}
