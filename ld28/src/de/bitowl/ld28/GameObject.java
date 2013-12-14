package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameObject extends Image{
	IngameScreen screen;

	
	public GameObject(IngameScreen pScreen) {
		screen = pScreen;
	}
	public GameObject(IngameScreen pScreen, TextureRegion pRegion){
		super(pRegion);
		screen = pScreen;
	}
	
	
	
	float speedX,speedY;
	float speedFactorX=180;
	float speedFactorY=100;
	float gravity=10f;
	
	boolean onGround;
	boolean onLadder;
	
	boolean doNotStopOnWalls;
	
	public float life=1;
	public float hitDamage=2;
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// apply a very simple gravity
		if(!onLadder){
			speedY -= gravity*delta;	
		}
		
		// which tiles this object standing on after this frame
		int xtile=(int) ( (getX()+ speedX * speedFactorX * delta) /screen.colLayer.getTileWidth());
		int ytile=(int) ( (getY()+ speedY * speedFactorY * delta) /screen.colLayer.getTileHeight());		

		if(noEnemies(getX(),getY()+speedY * speedFactorY * delta)){
		
			getMyCorners(getX(),getY()+speedY * speedFactorY * delta);
	
			if(speedY>=0 && onLadder&&notOnLadder()){// we moved away from the ladder
				onLadder=false;
			}
			if(speedY>0){
				if(upleft&&upright){
					setY(getY()+speedY * speedFactorY * delta);
				}else{
					setY( (ytile+1)*screen.colLayer.getTileHeight()-getHeight()%screen.colLayer.getTileHeight());
					speedY = 0;
				}

			}else if(speedY<0){
					if(downleft&&downright){
						if(notOnLadder()){
							setY(getY()+speedY * speedFactorY * delta);
							onGround = false;
							onLadder=false;
						}else{
							if(!onLadder){ // first time hit on the ladder
								speedY=0;
							}
							onLadder=true;
							setY(getY()+speedY * speedFactorY * delta);
							//  onGround=true;
							
						}
					}else{
						setY( (ytile+1)*screen.colLayer.getTileHeight());
						onGround = true;
						// hit the ground
						// check on falldamage
						// TODO tweak numbers
						if(speedY<-10){
							System.err.println("too fast: "+speedY);
							addDamage(-(speedY+9)/3);
						}
						
						speedY = 0;
					}
				
			}
		
		}else{
			if(speedY<=0){onGround=true;}
			speedY=0;
		}
		
		
		if(noEnemies(getX() + speedX*speedFactorX*delta, getY())){
			getMyCorners(getX() + speedX*speedFactorX*delta, getY());
	
			if(speedX<0){
				if(downleft && upleft && midleft){
					setX(getX() + speedX*speedFactorX*delta);
				}else{
					setX( (xtile+1)*screen.colLayer.getTileWidth());
					if(!doNotStopOnWalls){speedX=0;}
				}
			}else if(speedX>0){
				if(downright && upright && midright){
					setX(getX() + speedX*speedFactorX*delta);
					
				}else{
					setX( (xtile+1)*screen.colLayer.getTileWidth()-getWidth() );
					if(!doNotStopOnWalls){speedX=0;}
					
				}
			}
		
		}else{
			speedX=0;
		}
		
		// don't leave the map
		if(getX() < 0){
			setX(0);
		}
		if(getY() < 0){
			setY(0);
			onGround = true;
			// TODO maybe fall into eternity instead?
		}
		if(getX() > screen.colLayer.getWidth()*screen.colLayer.getTileWidth() - getWidth()){
			setX(screen.colLayer.getWidth()*screen.colLayer.getTileWidth() - getWidth());
		}
		if(getY() > screen.colLayer.getHeight()*screen.colLayer.getTileHeight() - getHeight()){
			setY(screen.colLayer.getHeight()*screen.colLayer.getTileHeight() - getHeight());
		}
	}
	

	boolean upleft,downleft,upright,downright,midleft, midright;

	/**
	 * calculate the corners
	 * @param pX
	 * @param pY
	 */
	public void getMyCorners(float pX,float pY){

		// calculate corner coordinates
		int downY=(int) Math.floor((pY)/screen.colLayer.getTileHeight());
		int midY=(int) Math.floor((pY+getHeight()/2)/screen.colLayer.getTileHeight());
		int upY=(int) Math.floor((pY+getHeight()-1)/screen.colLayer.getTileHeight());
		int leftX=(int) Math.floor((pX)/screen.colLayer.getTileWidth());
		int rightX=(int) Math.floor((pX+getWidth()-1)/screen.colLayer.getTileWidth());
		
		// check if the in the corner is a wall
		upleft=isFree(leftX,upY);
		downleft=isFree(leftX,downY);
		upright=isFree(rightX,upY);
		downright=isFree(rightX,downY);
		midleft=isFree(leftX,midY);
		midright=isFree(rightX,midY);
	}
	
	
	/**
	 * this move would not hit any enemies?
	 * @param pX
	 * @param pY
	 * @return
	 */
	public boolean noEnemies(float pX, float pY){
		if(speedX>0){pX++;}else if(speedX<0){pX--;}
		// if(speedY>0){pY++;}else if(speedY<0){pY--;}
		
		
		for(Actor actor:screen.enemies.getChildren().items){
			if(actor == null){
				continue;
			}
			Enemy enemy=(Enemy) actor;
			if(enemy != this && enemy.getRectangle().overlaps(new Rectangle(pX,pY,getWidth(),getHeight()))){// oh no, we've hit an enemy
				enemy.addDamage(hitDamage);
				addDamage(enemy.hitDamage);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param pX
	 * @param pY
	 * @return true, if the tile is empty
	 */
	public boolean isFree(int pX, int pY){
		return (screen.destLayer.getCell(pX, pY) == null);
	}
	
	public boolean notOnLadder() {
		for(Actor actor:screen.ladders.getChildren().items){
			if(actor == null){
				continue;
			}
			Ladder ladder=(Ladder)actor;
			if(ladder != this &&getFootRectangle().overlaps(ladder.getRectangle())&& getY()>= ladder.getY()){
				return false;
			}
		}
		return true;
	}


	public Rectangle getFootRectangle(){
		return getRectangle();
	}
	
	
	// TODO only build when it changes?
	public Rectangle getRectangle(){
		return new Rectangle(getX(),getY(),getWidth(),getHeight());
	}
	
	public void addDamage(float pDamage){
		System.out.println("damage: "+pDamage);
		life -= pDamage;
		if(life < 0 ){ // dis thing is dead
			remove();
		}
	}
	
	
	
}
