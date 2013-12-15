package de.bitowl.ld28.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.bitowl.ld28.DialogLine;
import de.bitowl.ld28.GoldBar;
import de.bitowl.ld28.LDGame;
import de.bitowl.ld28.StageInputProcessor;
import de.bitowl.ld28.objects.Weapon;

public class ShopScreen extends AbstractScreen {

	IngameScreen screen;
	DialogLine line;
	public ShopScreen(LDGame pGame, IngameScreen pScreen) {
		super(pGame);
		screen = pScreen;
		
		
		Image backButton = new Image(screen.atlas.findRegion("button_back"));
		backButton.setX(stage.getWidth()-backButton.getWidth());
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				
				game.setScreen(screen);
			}
		});
		stage.addActor(backButton);
		
		
		Table table = new Table();
		table.debug();
		table.setSize(stage.getWidth(), stage.getHeight());
		
		Image title = new Image(screen.atlas.findRegion("shop"));
		table.add(title).colspan(6).row();
	
		
		WeaponButton swordButton = new WeaponButton(Weapon.SWORD,"ui_sword");
		table.add(swordButton).pad(25);
		
		WeaponButton bowButton = new WeaponButton(Weapon.BOW,"ui_bow");
		table.add(bowButton).pad(25);
		
		WeaponButton bombsButton = new WeaponButton(Weapon.BOMBS,"ui_bomb");
		table.add(bombsButton).pad(25);
		
		WeaponButton shovelButton = new WeaponButton(Weapon.SHOVEL,"ui_shovel");
		table.add(shovelButton).pad(25);
		
		WeaponButton pickaxeButton = new WeaponButton(Weapon.PICKAXE,"ui_pickaxe");
		table.add(pickaxeButton).pad(25);
		
		WeaponButton ladderButton = new WeaponButton(Weapon.LADDER,"ui_ladder");
		table.add(ladderButton).pad(25);
		
		stage.addActor(table);
		
		GoldBar goldBar = new GoldBar(screen);
		goldBar.camera= stage.getCamera(); // the gold bar should care about the shop camera, not about the ingame one
		stage.addActor(goldBar);
		
		line = new DialogLine(this);
		stage.addActor(line);
		
		
	}
	
	
	
	@Override
	public void render(float delta) {
		System.out.println("in shop :)");
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		super.render(delta);
		Table.drawDebug(stage);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new StageInputProcessor(this){
			@Override
			public boolean keyDown(int keycode) {
				if(keycode==Keys.ESCAPE){
					game.setScreen(screen);
				}
				return false;
			}
		});
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	class WeaponButton extends Image{
		Weapon weapon;
		TextureRegion background;
		TextureRegion gold;
		BitmapFont font;
		public WeaponButton(Weapon pWeapon, String pImg) {
			super(screen.atlas.findRegion(pImg));
			weapon = pWeapon;
			background = screen.atlas.findRegion("ui_bg");
			gold = screen.atlas.findRegion("gold");
			font = screen.game.assets.get("fonts/numbers.fnt",BitmapFont.class);
		}
		@Override
		protected void setParent(Group parent) {
			super.setParent(parent);
			addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(screen.player.gold >= weapon.ammoCost){
						if(weapon.curAmmo == weapon.maxAmmo){
							line.display("You have enough of this.", 1f);
							return;
						}
						line.display("You bought ", 1f);
						screen.player.gold -= weapon.ammoCost;
						weapon.curAmmo += weapon.ammoAmount;
						if(weapon.curAmmo>weapon.maxAmmo){
							weapon.curAmmo = weapon.maxAmmo;
						}
					}else{
						line.display("You do not have enough money!", 1f);
					}
				}
			});
		}
		@Override
		public float getPrefHeight() {
			if(font==null){
				return 0;
			}else{
				return background.getRegionHeight()+font.getLineHeight()*2;
			}
		}
		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(background, getX(), getY()+font.getLineHeight()*2);
			batch.draw(((TextureRegionDrawable)getDrawable()).getRegion(),getX(),getY()+font.getLineHeight()*2);

			
			
			if(weapon.curAmmo == weapon.maxAmmo){
				font.setColor(0,1,0,0.7f);
			}else{
				font.setColor(1,1,1,0.7f);	
			}
			font.draw(batch, weapon.curAmmo+"", getX()+5, getY()+font.getLineHeight()*3);
			
			if(screen.player.gold >= weapon.ammoCost){
				font.setColor(1,1,1,1);
			}else{
				font.setColor(1,0,0,1);
			}
			
			font.draw(batch, "+"+weapon.ammoAmount, getX()+5, getY()+2*font.getLineHeight());
			batch.draw(gold,getX()-15,getY()+5);
			font.draw(batch, ""+weapon.ammoCost, getX()+20, getY()+font.getLineHeight());
			
		}
	}
	
	
	

}
