package de.bitowl.ld28;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image{
	IngameScreen screen;
	public Player(IngameScreen pScreen){
		super(pScreen.atlas.findRegion("player"));
		screen = pScreen;
	}
}
