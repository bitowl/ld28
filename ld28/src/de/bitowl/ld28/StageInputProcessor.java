package de.bitowl.ld28;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * input processor that does only care about ClickListeners
 * and projects the touch position correctly with the letterbox 
 * 
 * @author bitowl
 *
 */
class StageInputProcessor extends InputAdapter{
	AbstractScreen screen;
	public StageInputProcessor(AbstractScreen pScreen){
		screen = pScreen;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer,
			int button) {
		
		// project the touch position
		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		screen.stage.getCamera().unproject(touchPos,screen.viewport.x,screen.viewport.y,screen.viewport.width,screen.viewport.height);
		
		Actor hit= screen.stage.hit(touchPos.x, touchPos.y, true);
		if(hit!=null){
			for(EventListener listener:hit.getListeners()){
				if(listener instanceof ClickListener){
					((ClickListener)listener).clicked(null, touchPos.x, touchPos.y);
				}
			}
		}

		
		return false;
	}
}