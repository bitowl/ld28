package de.bitowl.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * if you perform this action on an actor, his drawable will be changed
 * sproingie (irc)
 * 
 * @author bitowl
 * 
 */
public class AnimAction extends Action {
	Animation animation;
	private float stateTime;
	boolean keepAfterEnd;
	 @Override
	public void restart() {
		super.restart();
		stateTime=0;
	}
	 
	public AnimAction(Animation pAnim) {
		animation = pAnim;
	}
	public AnimAction(Animation pAnim, boolean pLoop){
		this(pAnim);
		if(pLoop){
			animation.setPlayMode(Animation.LOOP);
		}
	}

	@Override
	public boolean act(float delta) {
		stateTime += Gdx.graphics.getDeltaTime();
		if (actor instanceof Image) {
			Image img = (Image) actor;
			TextureRegionDrawable draw = (TextureRegionDrawable) img.getDrawable();
			draw.setRegion(animation.getKeyFrame(stateTime));
		}
		if(isOver() && !keepAfterEnd){
			return true;
		}
		return false;
	}
	public boolean isOver(){
		if(animation.getPlayMode()==Animation.NORMAL){ 
		return animation.isAnimationFinished(stateTime);
		}else{
			return false;
		}
	}
}