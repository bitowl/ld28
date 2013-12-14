package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;

public class Bomb extends GameObject{

	float bombTimer;
	float afterExplTimer;
	float GROUND_FRICTION=3;
	float AIR_FRICTION=1;
	
	public Bomb(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("bomb"));
		Animation anim =new Animation(0.2f, screen.atlas.findRegions("bomb"));
		anim.setPlayMode(Animation.LOOP);
		// System.out.println(anim.animationDuration);
		addAction(new AnimAction(anim)); // fuse lightning
		
		bombTimer = 3;
		afterExplTimer = 0.3f;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// ground friction
		float FRICTION;
		if(onGround){
			FRICTION=GROUND_FRICTION;
		}else{
			FRICTION=AIR_FRICTION;
		}
		
		if(speedX>0){
			speedX-=delta*FRICTION;
			if(speedX<0){speedX=0;}
		}
		if(speedX<0){
			speedX+=delta*FRICTION;
			if(speedX>0){speedX=0;}
		}
		
		bombTimer -= delta;
		if(bombTimer<0){
			if(afterExplTimer == 0.3f){
				Explosion expl=new Explosion(screen);
				expl.setX(getX()+getWidth()/2-expl.getWidth()/2);
				expl.setY(getY()+getHeight()/2-expl.getHeight()/2);
				screen.stage.addActor(expl);
			
			}
			afterExplTimer -= delta;
			
			if(afterExplTimer<0){
				remove(); // remove the bomb from screen
				// do some damage
				int deployX=(int) (getX()/screen.colLayer.getTileWidth());
				int deployY=(int) (getY()/screen.colLayer.getTileHeight());
				
				for(int y = deployY - 2;y<=deployY+2;y++){
					for(int x = deployX-2;x<deployX+3;x++){
						screen.digTile(x,y,MathUtils.random(1,5));
					}
				}
			}
			
		}
	}
	
	
	
}
