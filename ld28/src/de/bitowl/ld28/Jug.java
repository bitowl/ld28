package de.bitowl.ld28;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Jug extends ItemObject{
	boolean destroyed;
	public Jug(IngameScreen pScreen) {
		super(pScreen,pScreen.atlas.findRegion("jug"));
	}

	
	@Override
	public void hitBy(GameObject gameObject) {
		if(gameObject instanceof Player || gameObject instanceof Arrow || gameObject instanceof Bomb){
			// collect
			if(!destroyed){
				// destroy dat jug
				setDrawable(new TextureRegionDrawable(screen.atlas.findRegion("jug", 1)));
				destroyed = true;
				collidable=false;
			}
		}
	}
}
