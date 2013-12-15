package de.bitowl.ld28.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.bitowl.ld28.AnimAction;
import de.bitowl.ld28.screens.IngameScreen;

public class Explosion extends Image{
	AnimAction explAnim;
	public Explosion(IngameScreen pScreen) {
		super(pScreen.atlas.findRegion("expl"));
		explAnim = new AnimAction(new Animation(0.05f, pScreen.atlas.findRegions("expl")));
		addAction(explAnim);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(explAnim.isOver()){
			remove();
		}
	}
}
