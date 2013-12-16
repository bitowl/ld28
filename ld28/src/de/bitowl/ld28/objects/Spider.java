package de.bitowl.ld28.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.bitowl.ld28.AnimAction;
import de.bitowl.ld28.screens.IngameScreen;

public class Spider extends Enemy {
	int anchorX;
	int anchorY;
	
	int goDownTo;
	
	enum State{UP, GODOWN, DOWN, GOUP};
	
	State state=State.UP;
	
	float speed=70;
	
	TextureRegion web;
	
	float SEE_DISTANCE = 5*32;
	
	public Spider(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("spider"));
		web=screen.atlas.findRegion("web");
		
		Animation wobble=new Animation(0.08f, screen.atlas.findRegions("spider"));
		wobble.setPlayMode(Animation.LOOP_PINGPONG);
		addAction(new AnimAction(wobble));
		gravity=0;
		
		state=State.UP;
	}
	@Override
	public void setPosition(float x, float y) {
		// the spider ankers at the block on top of this one
		anchorX=(int) (x/screen.destLayer.getTileWidth());
		anchorY=(int) (y/screen.destLayer.getTileHeight())+1;
		super.setPosition(anchorX*screen.destLayer.getTileWidth()+6, anchorY*screen.destLayer.getTileHeight()-getHeight());
		goDownTo=10;
		for(int i=1;i<=10;i++){
			if(screen.destLayer.getCell(anchorX, anchorY-i)!=null){
				goDownTo=i-1;
				break;
			}
		}
	}
	@Override	
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion region = ((TextureRegionDrawable)getDrawable()).getRegion();
		batch.draw(region, getX() , getY(), region.getRegionWidth(), region.getRegionHeight());
		float diff=getY()-anchorY*screen.destLayer.getTileHeight()+region.getRegionHeight(); // length of the web
		batch.draw(web, anchorX*screen.destLayer.getTileWidth(), anchorY*screen.destLayer.getTileHeight(),32,diff);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// die if anchorblock is deleted
		if(screen.destLayer.getCell(anchorX, anchorY)==null){
			remove();
		}
		
		switch(state){
			case GODOWN:
				setY(getY()-delta*speed);
				setHeight(getHeight()+delta*speed);
				if(getY()<(anchorY-goDownTo)*screen.destLayer.getTileHeight()){
					setY((anchorY-goDownTo)*screen.destLayer.getTileHeight());
					state=State.DOWN;
				}
				break;
			case GOUP:
				setY(getY()+delta*speed);
				setHeight(getHeight()-delta*speed);
				if(getY()>anchorY*screen.destLayer.getTileHeight() -16){
					setY(anchorY*screen.destLayer.getTileHeight() -16);
					state=State.UP;
				}
				break;
			case UP:
				if((screen.player.getX()-getX())*(screen.player.getX()-getX()) + (screen.player.getY()-getY())*(screen.player.getY()-getY()) < SEE_DISTANCE*SEE_DISTANCE){
					state=State.GODOWN;
				}
				break;
			case DOWN:
				if((screen.player.getX()-getX())*(screen.player.getX()-getX()) + (screen.player.getY()-getY())*(screen.player.getY()-getY()) > SEE_DISTANCE*SEE_DISTANCE){
					state=State.GOUP;
				}
				break;
		}
	}

}
