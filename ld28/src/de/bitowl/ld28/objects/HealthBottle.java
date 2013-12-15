package de.bitowl.ld28.objects;

import de.bitowl.ld28.ItemObject;
import de.bitowl.ld28.screens.IngameScreen;


public class HealthBottle extends ItemObject{
	boolean consumed;
	public HealthBottle(IngameScreen pScreen) {
		super(pScreen,pScreen.atlas.findRegion("healthbottle"));
	}

	
	@Override
	public void hitBy(GameObject gameObject) {
		if(gameObject instanceof Player || gameObject instanceof Arrow || gameObject instanceof Bomb){
			// consume
			if(!consumed){
				remove();
				screen.player.life+=3;
				if(screen.player.life > screen.player.max_life){
					screen.player.life = screen.player.max_life;
				}
				consumed = true;
				collidable=false;
				screen.dialogLine.display("Some of you health is restored.", 0.7f);
				screen.bottle.play();
			}	
		}
	}
}
