package de.bitowl.ld28.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.bitowl.ld28.AnimAction;
import de.bitowl.ld28.screens.IngameScreen;

public class Bomb extends GameObject{

	float bombTimer;
	float afterExplTimer;
	float GROUND_FRICTION=4;
	float AIR_FRICTION=1.5f;
	
	public Bomb(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("bomb"));
		Animation anim =new Animation(0.2f, screen.atlas.findRegions("bomb"));
		anim.setPlayMode(Animation.LOOP);
		addAction(new AnimAction(anim)); // fuse lightning
		
		bombTimer = 3;
		afterExplTimer = 0.3f;
		life = Float.POSITIVE_INFINITY;
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
				
				
				// make less loud if far away
				float volume=1-distSound(screen.player.getX(),screen.player.getY(),getX(),getY())/600;
				if(volume>0){
					if(volume>1){volume=1;}
					screen.explode.play(volume);
				}
			
			}
			afterExplTimer -= delta;
			
			if(afterExplTimer<0){ // the explosion REALLY STARTS
				remove(); // remove the bomb from screen
				
				// do some damage on terrain
				int deployX=(int) (getX()/screen.bgLayer.getTileWidth());
				int deployY=(int) (getY()/screen.bgLayer.getTileHeight());

				int RADIUS=4;
				
				for(int y = deployY - RADIUS;y<=deployY+RADIUS;y++){
					for(int x = deployX-RADIUS;x<=deployX+RADIUS;x++){
						screen.digTile(x, y, dist(x,y,deployX,deployY));
					}
				}
				
				// damage to near things
				
				// hit player?
				if((screen.player.getX()/screen.destLayer.getTileWidth()-deployX)*(screen.player.getX()/screen.destLayer.getTileWidth()-deployX) + (screen.player.getY()/screen.destLayer.getTileHeight()-deployY)*(screen.player.getY()/screen.destLayer.getTileHeight()-deployY) < RADIUS*RADIUS){
					screen.player.addDamage(dist((int)(screen.player.getX()/screen.destLayer.getTileWidth()),(int)(screen.player.getY()/screen.destLayer.getTileHeight()),deployX,deployY));
				}
				
				// hit enemies
				for(Actor actor:screen.enemies.getChildren().items){
					if(actor == null){
						continue;
					}
					Enemy enemy=(Enemy) actor;
					if((enemy.getX()/screen.destLayer.getTileWidth()-deployX)*(enemy.getX()/screen.destLayer.getTileWidth()-deployX) + (enemy.getY()/screen.destLayer.getTileHeight()-deployY)*(enemy.getY()/screen.destLayer.getTileHeight()-deployY) < RADIUS*RADIUS){
						enemy.addDamage(dist((int)(enemy.getX()/screen.destLayer.getTileWidth()),(int)(enemy.getY()/screen.destLayer.getTileHeight()),deployX,deployY));
					}
				}
			}
		}
	}
	
	
	/**
	 * @return the strength that the bomb will have at that distance
	 */
	public int dist(int pX1,int pY1, int pX2,int pY2){
		int dmg=3-(int) ( MathUtils.random(-0.7f,0.7f)+Math.sqrt((pX2-pX1)*(pX2-pX1) + (pY2-pY1)*(pY2-pY1)));
		return dmg;
	}
	
	public float distSound(float pX1,float pY1, float pX2,float pY2){
		return (float) (Math.sqrt((pX2-pX1)*(pX2-pX1) + (pY2-pY1)*(pY2-pY1)));
	}
	@Override
	public Rectangle getFootRectangle() {
		return new Rectangle(); // bombs don't care about ladders
	}
	
}
