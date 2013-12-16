package de.bitowl.ld28.objects;

import de.bitowl.ld28.ItemObject;
import de.bitowl.ld28.screens.IngameScreen;


public class HeartContainer extends ItemObject{
	boolean consumed;
	public HeartContainer(IngameScreen pScreen) {
		super(pScreen,pScreen.atlas.findRegion("heart_full"));
	}
	
	@Override
	public void hitBy(GameObject gameObject) {
		if(gameObject instanceof Player || gameObject instanceof Arrow || gameObject instanceof Bomb){
			// consume
			if(!consumed){
				remove();
				screen.player.max_life++;
				screen.player.life=screen.player.max_life;

				consumed = true;
				collidable=false;
				screen.dialogLine.display("You got one more life.", 0.7f);
				screen.heart.play();
			}	
		}
	}
}
