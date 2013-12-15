package de.bitowl.ld28;

import java.awt.Point;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class Weapon{
	public static Player player;
	public static IngameScreen screen;
	public final int ID;
	int maxAmmo;
	int curAmmo;
	boolean bought;

	float cooldown=0.3f;
	// if you keep the mouse pressed, when will the next use happen
	float wait=0.5f;
	
	// buy new ammo
	int ammoCost;
	int ammoAmount;
	
	public final static Weapon SWORD = new Weapon(100,0.3f,0.5f,  20,2 ){
		@Override
		public boolean doSth(float pX, float pY) {
			player.sword(pX<player.getX()+player.getWidth()/2);
			return true;
		}
	};
	public final static Weapon SHOVEL = new Weapon(50,0.5f,0.8f, 30,1 ){
		@Override
		public boolean doSth(float pX, float pY) {

			Point digPos=convertToTile(pX,pY);
			
			if(screen.digTile(digPos.x,digPos.y, 1)){
				player.dig();
				return true;
			}
			return false;
		}
	};
	public final static Weapon PICKAXE = new Weapon(40,0.2f,0.4f, 20,20){
		@Override
		public boolean doSth(float pX, float pY) {
			Point digPos=convertToTile(pX,pY);
			
			if(screen.digTile(digPos.x,digPos.y, 3)){
				player.dig();
				return true;
			}
			
			return false;
		}
	};
	public final static Weapon BOMBS = new Weapon(40,0.5f,0.9f, 10,20){
		@Override
		public boolean doSth(float pX, float pY) {
			Bomb bomb =new Bomb(screen);
			bomb.setPosition(player.getX()+player.getWidth()/2-bomb.getWidth()/2, player.getY()+player.getHeight()/2-bomb.getHeight()/2);
		/*	bomb.speedX = 0;
			bomb.speedY = 0;
			
			if(touchPos.x<player.getX() - SPAN_X){
				bomb.speedX=-2;
			}else if(touchPos.x>player.getX()+player.getWidth() + SPAN_X){
				bomb.speedX=2;
			}
			
			if(touchPos.y<player.getY() - SPAN_Y){
				bomb.speedY=-3;
			}else if(touchPos.y>player.getY()+player.getHeight() + SPAN_Y){
				bomb.speedY=3;
			}*/
			// now you can throw bombs in all directions

			Vector2 ang=convertToAngle(pX, pY);
			
			float bombSpeed=Math.min(ang.y/32,4);
			bomb.speedX = MathUtils.cos(ang.x)*bombSpeed;
			bomb.speedY = MathUtils.sin(ang.x)*bombSpeed;
					
			screen.stage.addActor(bomb);
			return true;
		}
	};
	public final static Weapon BOW = new Weapon(30,0.15f,0.4f, 50,10){
		public boolean doSth(float pX, float pY) {
			player.bow(pX<player.getX()+player.getWidth()/2);
			
			Vector2 ang=convertToAngle(pX, pY);
			
			Arrow arrow= new Arrow(screen);
			arrow.setPosition(player.getX()+player.getOriginX()-arrow.getWidth()/2, player.getY()+player.getHeight()/2 -arrow.getHeight()/2);
			float arrowSpeed=Math.min(ang.y/32,5);
			arrow.speedX = MathUtils.cos(ang.x)*arrowSpeed;
			arrow.speedY = MathUtils.sin(ang.x)*arrowSpeed;
			screen.stage.addActor(arrow);
			return true;
		};
	};
	public final static Weapon LADDER = new Weapon(10,0.3f,0.5f, 1,3){
		@Override
		public boolean doSth(float pX, float pY) {
			if(player.onGround || player.onLadder){
				Point ladderPos=convertToTile(pX,pY);
				
				// place a ladder						
				Ladder ladder=new Ladder(screen);
				ladder.setPosition(ladderPos.x*screen.destLayer.getTileWidth(), ladderPos.y*screen.destLayer.getTileHeight());
				
				if(ladder.notOnLadder()){ // only place it, if there is no other ladder
					screen.ladders.addActor(ladder);
					return true;
				}
			}
			return false;
		}
	};
	
	private static int idC=0;
	
	public Weapon(int pMaxAmmo, float pCooldown, float pWait, int pAmount, int pCost){
		ID = idC;
		idC++;
		maxAmmo=pMaxAmmo;
		// curAmmo=maxAmmo;
		curAmmo = 1; // you only get one
		cooldown = pCooldown;
		wait = pWait;
		ammoAmount=pAmount;
		ammoCost=pCost;
	}

	public boolean use(float pX, float pY) {
		if(curAmmo!=0){
			if(doSth(pX, pY)){
				curAmmo--;
			}
			return true;
		}
		return false;
	}
	
	public abstract boolean doSth(float pX, float pY);
	
	private static Point convertToTile(float pX, float pY){
		
		int digX=player.getStandingX();
		int digY=player.getMiddleY();
		
		for(int i=0;i<Player.REACH;i++){
			if(i<1 || player.isFree(digX, digY)){
				if(pX<digX*screen.destLayer.getTileWidth()){
					digX--;
				}else if(pX>(digX+1)*screen.destLayer.getTileWidth()){
					digX++;
				}
			}
		}
		
		for(int i=0;i<Player.REACH;i++){
			if(i<1 || player.isFree(digX, digY)){
				if(pY<digY*screen.destLayer.getTileHeight()){
					digY--;
				}else if(pY>(digY+1)*screen.destLayer.getTileHeight()){
					digY++;
				}
			}
		}
	/*	int digX=player.getStandingX();
		int digY=player.getMiddleY();
		

		if(pX<player.getX()){
			digX=player.getStandingX()-1;
		}else if(pX>player.getX()+player.getWidth()){
			digX=player.getStandingX()+1;
		}
		
		if(pY<player.getY()){
			digY=player.getStandingY();
		}else if(pY>player.getY()+player.getHeight()){
			digY=player.getMiddleY()+1;
		}
		return new Point(digX, digY);*/
		
		// int x = (int) (pX/screen.destLayer.getTileWidth());
		// int y = (int) (pY/screen.destLayer.getTileHeight());
		return new Point(digX, digY);
	}
	
	/**
	 * converts a touch point to an angle and a dist relatively to the player
	 * @param pX
	 * @param pY
	 * @return (angle, distance)
	 */
	private static Vector2 convertToAngle(float pX, float pY){
		float xdiff = pX - player.getX()+player.getOriginX();
		float ydiff =pY - player.getY()+player.getHeight()/2;
		
		float degree = MathUtils.atan2(ydiff, xdiff);
		float dist=(float) Math.sqrt(xdiff*xdiff + ydiff*ydiff);
		return new Vector2(degree, dist);
	}
}
