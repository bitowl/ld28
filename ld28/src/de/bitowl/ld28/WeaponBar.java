package de.bitowl.ld28;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;

import de.bitowl.ld28.objects.Weapon;
import de.bitowl.ld28.screens.IngameScreen;

public class WeaponBar extends Image{
	IngameScreen screen;
	TextureRegion heart_full,heart_half,heart_empty;
	Image ui_bomb;
	Image ui_sword;
	
	public boolean open;
	
	OrderedMap<Weapon, WeaponBarImage> weapons;
	public WeaponBar(IngameScreen pScreen){
		super(pScreen.atlas.findRegion("ui_bg"));
		screen=pScreen;
		
		weapons = new OrderedMap<Weapon, WeaponBarImage>();
		
		createWeaponImg(Weapon.SWORD,"ui_sword");

		createWeaponImg(Weapon.BOW,"ui_bow");
		createWeaponImg(Weapon.BOMBS,"ui_bomb");
		createWeaponImg(Weapon.SHOVEL,"ui_shovel");		
		createWeaponImg(Weapon.PICKAXE, "ui_pickaxe");
		createWeaponImg(Weapon.LADDER, "ui_ladder");
		
		// hide all except for the current one
		for(Entry<Weapon, WeaponBarImage> weapon: weapons.entries()){
			weapon.value.addAction(Actions.alpha(0));
		}
		weapons.get(screen.weapon).addAction(Actions.alpha(1));
	}
	
	public void createWeaponImg(Weapon pWeapon, String pImg){
		weapons.put(pWeapon,new WeaponBarImage(pWeapon,screen.atlas.findRegion(pImg)));	
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		for(Entry<Weapon, WeaponBarImage> weapon: weapons.entries()){
			weapon.value.act(delta);
		}
	}
	
	public void open(){
		int y=-64;
		for(Entry<Weapon, WeaponBarImage> weapon: weapons.entries()){
			if(weapon.key!=screen.weapon){
				weapon.value.addAction(Actions.alpha(1,0.5f));
				weapon.value.addAction(Actions.moveTo(0,y,0.5f));
				y-=64;
			}
		}
		open=true;
	}
	
	public void close(){
		for(Entry<Weapon, WeaponBarImage> weapon: weapons.entries()){
			if(weapon.key!=screen.weapon){
				weapon.value.addAction(Actions.alpha(0,0.5f));
			}
			weapon.value.addAction(Actions.moveTo(0,0,0.5f));
		}
		open=false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setTransformMatrix(batch.getTransformMatrix().trn(new Vector3(screen.stage.getCamera().position.x + screen.stage.getWidth()/2 - getWidth(),screen.stage.getCamera().position.y + screen.stage.getHeight()/2 - getHeight(),0)));
		
		super.draw(batch, parentAlpha);
		for(Entry<Weapon, WeaponBarImage> weapon: weapons.entries()){
			weapon.value.draw(batch, parentAlpha);
		}
		
		batch.setTransformMatrix(new Matrix4());
	}
	public void select(float f) {
		for(Entry<Weapon, WeaponBarImage> weapon: weapons.entries()){
			if(f>weapon.value.getY()-weapon.value.getHeight()&&f<weapon.value.getY()){
				screen.weapon=weapon.key;
				return; 
			}
		}
		return;
	}
	public void selectId(int nr) {
		
		if(open){
			close();
		}
		
		int i=0;
		for(Entry<Weapon, WeaponBarImage> weapon: weapons.entries()){
			if(i==nr && weapon.key!=screen.weapon){
				for(Entry<Weapon, WeaponBarImage> weapon2: weapons.entries()){
					if(weapon2.key==screen.weapon){
						weapon2.value.addAction(Actions.alpha(0,0.5f));
						break;
					}
				}
				screen.weapon=weapon.key;
				weapon.value.addAction(Actions.alpha(1,0.5f));
				break;
			}
			i++;
		}
	}
	
	class WeaponBarImage extends Image{
		Weapon weapon;
		BitmapFont font;
		public WeaponBarImage(Weapon pWeapon, TextureRegion pRegion) {
			super(pRegion);
			weapon=pWeapon;
			font = screen.game.assets.get("fonts/numbers.fnt",BitmapFont.class);
			font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		@Override
		public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
			font.setColor(1, 1, 1, getColor().a*0.7f);
			font.draw(batch, weapon.curAmmo+"", 5, getY()+font.getLineHeight());
		}
	}
}
