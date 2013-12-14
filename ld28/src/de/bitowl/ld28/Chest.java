package de.bitowl.ld28;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Chest extends ItemObject{
	boolean open;
	public Chest(IngameScreen pScreen) {
		super(pScreen,pScreen.atlas.findRegion("chest"));
	}

	@Override
	public void hitBy(GameObject gameObject) {
		if(gameObject instanceof Player){
			if(!open){
				// open the chest :)
				setDrawable(new TextureRegionDrawable(screen.atlas.findRegion("chest", 1)));
				open = true;
				collidable=false;
			}	
		}
	}
}
