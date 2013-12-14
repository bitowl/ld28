package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;

import de.bitowl.ld28.IngameScreen.Weapon;

public class WeaponBar extends Image{
	IngameScreen screen;
	TextureRegion heart_full,heart_half,heart_empty;
	Image ui_bomb;
	Image ui_sword;
	
	OrderedMap<IngameScreen.Weapon, Image> weapons;
	public WeaponBar(IngameScreen pScreen){
		super(pScreen.atlas.findRegion("ui_bg"));
		screen=pScreen;
		
		weapons = new OrderedMap<IngameScreen.Weapon, Image>();
		
		weapons.put(Weapon.SWORD,getImg("ui_sword"));
		weapons.put(Weapon.BOW,getImg("ui_bow"));
		weapons.put(Weapon.BOMBS,getImg("ui_bomb"));
		weapons.put(Weapon.SHOVEL,getImg("ui_shovel"));		
		weapons.put(Weapon.PICKAXE, getImg("ui_pickaxe"));
		
		// hide all except for the current one
		for(Entry<Weapon, Image> weapon: weapons.entries()){
			weapon.value.addAction(Actions.alpha(0));
		}
		weapons.get(screen.weapon).addAction(Actions.alpha(1));
	//	open();
	}
	public Image getImg(String pImg){
		
		return new Image(screen.atlas.findRegion(pImg));
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		for(Entry<Weapon, Image> weapon: weapons.entries()){
			weapon.value.act(delta);
		}
		
	}
	
	public void open(){
		int y=-64;
		for(Entry<Weapon, Image> weapon: weapons.entries()){
			if(weapon.key!=screen.weapon){
				weapon.value.addAction(Actions.alpha(1,0.5f));
				weapon.value.addAction(Actions.moveTo(0,y,0.5f));
				y-=64;
			}
		}
	}
	
	public void close(){
		for(Entry<Weapon, Image> weapon: weapons.entries()){
			if(weapon.key!=screen.weapon){
				weapon.value.addAction(Actions.alpha(0,0.5f));
			}
			weapon.value.addAction(Actions.moveTo(0,0,0.5f));
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {

		// setX(screen.stage.getCamera().position.x + screen.stage.getWidth()/2 - getWidth());
		// setY(screen.stage.getCamera().position.y + screen.stage.getHeight()/2 - getHeight());
		//batch.setTransformMatrix(Matrix4.)
		
	//	System.out.println(prjMat);
		batch.setTransformMatrix(batch.getTransformMatrix().trn(new Vector3(screen.stage.getCamera().position.x + screen.stage.getWidth()/2 - getWidth(),screen.stage.getCamera().position.y + screen.stage.getHeight()/2 - getHeight(),0)));
	//	batch.setTransformMatrix(prjMat);
		
		super.draw(batch, parentAlpha);
		for(Entry<Weapon, Image> weapon: weapons.entries()){
			weapon.value.draw(batch, parentAlpha);
		}
		batch.setTransformMatrix(new Matrix4());
		
	}
	public void select(float f) {
		System.err.println("select "+f);
		for(Entry<Weapon, Image> weapon: weapons.entries()){
			if(f>weapon.value.getY()-weapon.value.getHeight()&&f<weapon.value.getY()){
				System.out.println("HIT"+weapon.key);
				screen.weapon=weapon.key;
				return; 
			}
		}
		return;
	}
}
