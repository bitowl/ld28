package de.bitowl.ld28;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GoldBar extends Actor{
	IngameScreen screen;
	TextureRegion gold;
	
	BitmapFont font;
	public GoldBar(IngameScreen pScreen){
		screen=pScreen;
		gold=screen.atlas.findRegion("gold");
		font = screen.game.assets.get("fonts/numbers.fnt",BitmapFont.class);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		float x = screen.stage.getCamera().position.x - screen.stage.getWidth()/2;
		float y = screen.stage.getCamera().position.y - screen.stage.getHeight()/2;
		batch.setTransformMatrix(batch.getTransformMatrix().trn(new Vector3(x,y,0)));
		
		batch.draw(gold,0,0);
		font.setColor(1,1,1,1);
		font.draw(batch, screen.player.gold+"", 30,23);
		batch.setTransformMatrix(new Matrix4());
	}
}
