package de.bitowl.ld28.objects;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.bitowl.ld28.ItemObject;
import de.bitowl.ld28.screens.IngameScreen;

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
				screen.player.gold+=5;
				screen.dialogLine.display("You found 5 gold!", 1);
			}	
		}
	}
}
