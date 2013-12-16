package de.bitowl.ld28.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import de.bitowl.ld28.screens.IngameScreen;
import de.bitowl.ld28.screens.WinScreen;

public class Skeleton extends Enemy {
	
	SpriteBatch batch;
	Texture emptyT, fullT;
	NinePatch full, empty;
	BitmapFont font;
	
	public float cooldown;
	public Skeleton(IngameScreen pScreen) {
		super(pScreen, pScreen.atlas.findRegion("skelett"));
		life = 100;
		
		font=new BitmapFont();
		batch=new SpriteBatch();
		emptyT=new Texture(Gdx.files.internal("textures/bar.png"));
		fullT=new Texture(Gdx.files.internal("textures/bar_full.png"));
		empty=new NinePatch(new TextureRegion(emptyT,24,24),8,8,8,8);
		full=new NinePatch(new TextureRegion(fullT,24,24),8,8,8,8);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(cooldown>0){
			cooldown-=delta;
		}else{
			Bone bone=new Bone(screen);
			bone.setX(getX()+10);
			bone.setY(getY()+getHeight()/2+10);
			bone.gravity=0;
			// bone.defSpeedX=-10;
			screen.enemies.addActor(bone);
			
			if(life>50){
				cooldown=5;	
			}else if(life>40){
				cooldown=4;
			}else if(life>30){
				cooldown=3;
			}else if(life>5){
				cooldown=2;
			}else{
				cooldown=1;
			}
			
		}
		System.out.println(life);
		
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		float x = screen.stage.getCamera().position.x - screen.stage.getWidth()/2;
		float y = screen.stage.getCamera().position.y - screen.stage.getHeight()/2 + 30;
		batch.setTransformMatrix(batch.getTransformMatrix().trn(new Vector3(x,y,0)));
		
		empty.draw(batch, 40, 0, 720, 20);
		if(life>0){
			full.draw(batch, 40, 0, life/100*720, 20);
		}
		font.drawMultiLine(batch,"boss life: "+(int)life,400,247-225-5,0, BitmapFont.HAlignment.CENTER);
		
		batch.setTransformMatrix(new Matrix4());
	}
	
	@Override
	public boolean remove() {
		if(getParent()!=null){
			screen.game.setScreen(new WinScreen(screen.game));
		}
		System.out.println(life+" GOOOO DOWN");
		// 
		return super.remove();
		
	}
	@Override
	public Rectangle getRectangle() {
		return new Rectangle(getX()+65,getY(),getWidth()-65-51,getHeight());
	}
}
