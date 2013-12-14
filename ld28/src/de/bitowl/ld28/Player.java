package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends GameObject{
	public final static float JUMP_CONST=4f;
	AnimAction walking;
	AnimAction sword;
	AnimAction dig;
	boolean isWalking;
	boolean isFlipped;
	public int max_life=10;
	
	float SWORD_DAMAGE=3;
	
	public Player(IngameScreen pScreen){
		super(pScreen,pScreen.atlas.findRegion("player"));
		life = 5.5f;
		hitDamage=0;
		doNotStopOnWalls = true; // so we can smoothly jump, when we stand at a wall
		Animation walk=new Animation(0.1f,screen.atlas.findRegions("player"));
		//walk.setPlayMode(Animation.LOOP_PINGPONG);
		walking = new AnimAction(walk,true);
		
		sword = new AnimAction(new Animation(0.05f,screen.atlas.findRegions("sword")));
		dig = new AnimAction(new Animation(0.05f,screen.atlas.findRegions("dig")));
		
		setOriginX(getWidth()/2); // center point for flipping
	}
	
	public void jump(){
		if(onLadder){
			speedY=2;
			return;
		}
		if(onGround){
			speedY = JUMP_CONST;
			onGround = false;
		}
	}

	public void descend() {
		if(onLadder){
			speedY=-2;
			System.out.println("descend read");
		}
		System.out.println("descend");
		
	}


	public void stopClimbing() {
		if(onLadder){
			speedY=0;
		}
		
	}

	
	// what tile the player is standing on
	public int getStandingX(){
		return (int)( (getX() +getWidth()/2)/screen.colLayer.getTileWidth());
	}
	public int getStandingY(){
		return (int)(getY()/screen.colLayer.getTileHeight())-1;
	}
	public int getMiddleY(){
		return (int)((getY()+getHeight()/2)/screen.colLayer.getTileHeight());
	}

	public void walk() {
		if(speedX==0 && isWalking){
			removeAction(walking);
			isWalking=false;
			
		}else if(speedX!=0 && !isWalking){
			addAction(walking);
			isWalking=true;
		}
		if(speedX<0 && !isFlipped){
			setScaleX(-1);
			isFlipped=true;
		}else if(speedX>0 && isFlipped){
			setScaleX(1);
			isFlipped=false;				
		}
		
	}
	public void sword(){
		if(sword.getActor()==null){
			sword.reset();
			addAction(sword);
			
			// hit anything?
			for(Actor actor:screen.enemies.getChildren().items){
				Enemy enemy=(Enemy) actor;
				if(enemy == null){
					// this enemy is already deaaaad
					continue;
				}
				if(enemy.getRectangle().overlaps(getSwordRectangle())){
					enemy.addDamage(SWORD_DAMAGE);
				}
			}
		}
	}
	private Rectangle getSwordRectangle() {
		if(isFlipped){
			return new Rectangle(getX()-7,getY(),15,getHeight());
		}else{
			return new Rectangle(getX()+13,getY(),15,getHeight());
		}
	}
	public Rectangle getFootRectangle(){
		if(isFlipped){
			return new Rectangle(getX()+5,getY(),15,9);
		}else{
			return new Rectangle(getX(),getY(),15,9);
		}
	}
	
	public void dig(){
		if(dig.getActor()==null){
			dig.reset();
			addAction(dig);

		}
	}
	
}
