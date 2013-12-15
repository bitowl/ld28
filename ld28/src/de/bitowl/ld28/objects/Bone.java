package de.bitowl.ld28.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.bitowl.ld28.screens.IngameScreen;

public class Bone extends Enemy {

	float aimCooldown=1f;
	public Bone(IngameScreen pScreen) {
		super(pScreen,pScreen.atlas.findRegion("bone"));
		setOrigin(getWidth()/2,getHeight()/2);
		addAction(Actions.forever(Actions.rotateBy(360,1f)));
		life=4;
		hitDamage=2;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		if(onGround){
			remove();
		}
		if(aimCooldown>0){
			aimCooldown-=delta;
			if(aimCooldown<0){
				float angle= MathUtils.atan2(screen.player.getCenterY() - getCenterY(), screen.player.getCenterX() - getCenterX());
				defSpeedX=MathUtils.cos(angle)*2;
				defSpeedY=MathUtils.sin(angle)*2;
			}
		}
	}

}
