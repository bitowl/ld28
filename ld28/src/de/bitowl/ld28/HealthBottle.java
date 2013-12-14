package de.bitowl.ld28;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HealthBottle extends ItemObject{
	boolean consumed;
	public HealthBottle(IngameScreen pScreen) {
		super(pScreen,pScreen.atlas.findRegion("healthbottle"));
	}

	@Override
	public void collected() {
		if(!consumed){
			setDrawable(new TextureRegionDrawable(screen.atlas.findRegion("jug", 1)));
			screen.player.life+=3;
			if(screen.player.life > screen.player.max_life){
				screen.player.life = screen.player.max_life;
			}
			consumed = true;
		}
	}	
}
