package de.bitowl.ld28;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image{
	IngameScreen screen;
	
	
	float speedX,speedY;
	float speedFactorX=180;
	float speedFactorY=100;
	float gravity=10f;
	
	boolean onGround;
	
	public final static float JUMP_CONST=4f;
	
	public Player(IngameScreen pScreen){
		super(pScreen.atlas.findRegion("player"));
		screen = pScreen;
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// apply a very simple gravity
		speedY -= gravity*delta;
		
		// which tiles the hero will be standing on after this frame
		int xtile=(int) ( (getX()+ speedX * speedFactorX * delta) /screen.colLayer.getTileWidth());
		int ytile=(int) ( (getY()+ speedY * speedFactorY * delta) /screen.colLayer.getTileHeight());		

		getMyCorners(getX(),getY()+speedY * speedFactorY * delta);

		if(speedY>0){
			if(upleft&&upright){
				setY(getY()+speedY * speedFactorY * delta);
			}else{
				setY( (ytile+2)*screen.colLayer.getTileHeight()-getHeight());
				speedY = 0;
			}
		}else if(speedY<0){
			if(downleft&&downright){
				setY(getY()+speedY * speedFactorY * delta);
				onGround = false;
			}else{
				setY( (ytile+1)*screen.colLayer.getTileHeight());
				onGround = true;
				speedY = 0;
			}
		}
		
		
		getMyCorners(getX() + speedX*speedFactorX*delta, getY());

		if(speedX<0){
			if(downleft && upleft && midleft){
				setX(getX() + speedX*speedFactorX*delta);
			}else{
				setX( (xtile+1)*screen.colLayer.getTileWidth());
			}
		}else if(speedX>0){
			if(downright && upright && midright){
				setX(getX() + speedX*speedFactorX*delta);
				
			}else{
				setX( (xtile+1)*screen.colLayer.getTileWidth()-getWidth() );
				
			}
		}
		
		
		
		// don't leave the map
		if(getX() < 0){
			setX(0);
		}
		if(getY() < 0){
			setY(0);
			onGround = true;
			// TODO DIE A HARD DEATH
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
	 * 
	 * @param pX
	 * @param pY
	 * @return true, if the tile is empty
	 */
	public boolean isFree(int pX, int pY){
		return (screen.colLayer.getCell(pX, pY) == null);
	}
	
	public void jump(){
		if(onGround){
			speedY = JUMP_CONST;
			onGround = false;
		}
	}
}
