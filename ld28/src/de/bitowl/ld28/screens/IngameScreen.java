package de.bitowl.ld28.screens;

import java.awt.Point;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.OrderedMap;

import de.bitowl.ld28.DialogLine;
import de.bitowl.ld28.Event;
import de.bitowl.ld28.GoldBar;
import de.bitowl.ld28.LDGame;
import de.bitowl.ld28.LifeBar;
import de.bitowl.ld28.ShopEvent;
import de.bitowl.ld28.WeaponBar;
import de.bitowl.ld28.objects.Chest;
import de.bitowl.ld28.objects.Enemy;
import de.bitowl.ld28.objects.HealthBottle;
import de.bitowl.ld28.objects.HeartContainer;
import de.bitowl.ld28.objects.Jug;
import de.bitowl.ld28.objects.Player;
import de.bitowl.ld28.objects.Slime;
import de.bitowl.ld28.objects.Spider;
import de.bitowl.ld28.objects.Spike;
import de.bitowl.ld28.objects.Weapon;
import de.bitowl.ld28.objects.Worm;

public class IngameScreen extends AbstractScreen{

	
	public TextureAtlas atlas;
	
	// map stuff
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	// public TiledMapTileLayer colLayer;
	public TiledMapTileLayer destLayer; // layer that is containing destroyable stuff
	public TiledMapTileLayer blkbgLayer;
	
	TiledMapTileLayer fgLayer;
	public TiledMapTileLayer bgLayer;
	
	OrderedMap<TiledMapTile, TiledMapTile> topTiles; // tiles that are on the blkbglayer on top of the "real" one
	
	public Player player;
	
	int resized; //TODO remove this workaround and really fix it (e.g. with a menu :P)
	
	/**
	 * how hard is a tile to destroy
	 */
	int[] destroyable;
	
	public Group items;
	public Group enemies;
	public Group ladders;
	
	public Weapon weapon;
	
	WeaponBar weaponbar;
	public DialogLine dialogLine;
	
	
	// touch down
	boolean usingWeapon;
	float cooldownTime;
	float waitTime;
	
	public ShopScreen shop;
	PauseScreen pause;
	
	public OrderedMap<Point, Event> trigger;

	public Rectangle viewRect;
	
	public Sound bow;
	public Sound dig;
	public Sound explode;
	public Sound gold;
	public Sound no_dig;
	public Sound ground;
	public Sound heart;
	public Sound bottle;
	public Sound hit;
	
	public IngameScreen(LDGame pGame) {
		super(pGame);
		
		
		map = game.assets.get("maps/map1.tmx",TiledMap.class);
		game.mapUsed = true; // before the next game is started, we should reset the map, so that all the destroyed things are back complete
		atlas = game.assets.get("textures/textures.pack", TextureAtlas.class);
		
		renderer = new OrthogonalTiledMapRenderer(map);
		renderer.setView((OrthographicCamera) stage.getCamera());
		
		destLayer = (TiledMapTileLayer) map.getLayers().get("destroyable");
		blkbgLayer = (TiledMapTileLayer) map.getLayers().get("blockbg");
		bgLayer = (TiledMapTileLayer) map.getLayers().get("background");
		fgLayer = (TiledMapTileLayer) map.getLayers().get("foreground");
		

		ladders=new Group(); // ladders should be behind the player
		stage.addActor(ladders);
		
		items = new Group();
		stage.addActor(items);
		
		
		player = new Player(this);
		
		player.setY(bgLayer.getHeight()*bgLayer.getTileHeight()-player.getHeight());
		stage.addActor(player);

		weapon = Weapon.SHOVEL;
		Weapon.player = player;
		Weapon.screen = this;
			
		
		
		
		// load audio
		dig = game.assets.get("audio/dig.ogg",Sound.class);
		explode = game.assets.get("audio/explode.ogg",Sound.class);
		bow = game.assets.get("audio/bow.ogg",Sound.class);
		gold = game.assets.get("audio/gold.ogg",Sound.class);
		no_dig = game.assets.get("audio/no_dig.ogg",Sound.class);
		ground = game.assets.get("audio/ground.ogg",Sound.class);
		heart = game.assets.get("audio/heart.ogg",Sound.class);
		bottle = game.assets.get("audio/bottle.ogg",Sound.class);
		hit = game.assets.get("audio/hit.ogg",Sound.class);
		
		
		
		
		
		// have the tiles properties?
		TiledMapTileSet tileset=map.getTileSets().getTileSet("tileset");
		
		
		int tssize=0;
		Iterator<TiledMapTile> it =tileset.iterator();
		while(it.hasNext()){ // count how many tiles there are
			TiledMapTile tile =it.next();
			if(tile.getId()>tssize){
				tssize=tile.getId();
			}
			
		}
		destroyable = new int[tssize+1];
		it = tileset.iterator();
		
		while(it.hasNext()){
			TiledMapTile tile = it.next();
			MapProperties properties = tile.getProperties();
			if(properties.get("dst")!=null){
				System.out.println(tile.getId()+": "+properties.get("dst"));
				try{
					destroyable[tile.getId()]=Integer.parseInt((String) properties.get("dst"));
					System.out.println("DEST: "+destroyable[tile.getId()]);
				}catch(NumberFormatException e){
					destroyable[tile.getId()]=1;
				}
			}
			/*Iterator<String> it =properties.getKeys();
			System.out.println(tile.getId());
			while(it.hasNext()){
				String key=it.next();
				System.out.println(key+":"+properties.get(key));
			}*/
		}
		
		
		trigger = new OrderedMap<Point,  Event>();
		enemies=new Group();
		
		// place enemies
		TiledMapTileLayer enemyLay=(TiledMapTileLayer) map.getLayers().get("enemies");
		for(int y=0;y<enemyLay.getHeight();y++){
			for(int x=0;x<enemyLay.getWidth();x++){
				if(enemyLay.getCell(x, y)!=null){
					
					int eventStartID=146;
					
					int tileID=enemyLay.getCell(x, y).getTile().getId();
					if(tileID>=eventStartID){
						System.err.println(tileID);
						// it's a trigger
						if(tileID==eventStartID){ // start pos
							player.setPosition(x*destLayer.getTileWidth(), y*destLayer.getTileHeight()+player.getHeight()-32);
						}else if(tileID==eventStartID+1){
							System.out.println("TRIGGER AT "+x+","+y);
							trigger.put(new Point(x,y), new ShopEvent());
						}else if(tileID==eventStartID+2){
							// NOT YET USED
						}else	if(tileID-eventStartID-2<Event.events.length){
							trigger.put(new Point(x,y),Event.events[tileID-eventStartID-2]);
						}else{
							System.err.println("event "+(tileID-eventStartID-2)+" not defined");
						}
						continue;
					}
					
					
					
					
					// System.out.println(enemyLay.getCell(x, y).getTile().getId());
					Enemy enemy;
					switch(tileID){
						case 3:
							enemy = new Worm(this);
							break;
						case 4:
							enemy = new Slime(this);
							break;
						case 5:
							enemy = new Spider(this);
							break;
						case 6:
							enemy = new Spike(this);
							break;
						case 7:
							Chest chest=new Chest(this);
							chest.setPosition(x*enemyLay.getTileWidth(), y*enemyLay.getTileHeight());
							items.addActor(chest);
							continue;
						case 8:
							Jug jug = new Jug(this);
							jug.setPosition(x*enemyLay.getTileWidth(), y*enemyLay.getTileHeight());
							items.addActor(jug);
							continue;
						case 9:
							HealthBottle hb = new HealthBottle(this);
							hb.setPosition(x*enemyLay.getTileWidth()+4, y*enemyLay.getTileHeight());
							items.addActor(hb);		
							continue;
						case 10:
							HeartContainer hc = new HeartContainer(this);
							hc.setPosition(x*enemyLay.getTileWidth()+4, y*enemyLay.getTileHeight());
							items.addActor(hc);
							continue;
						default:
							enemy = new Enemy(this);
							break;
					} 
					enemy.setPosition(x*enemyLay.getTileWidth(), y*enemyLay.getTileHeight());
					enemies.addActor(enemy);
				}
			}
		}
		
		stage.addActor(enemies);
		
		
		topTiles = new OrderedMap<TiledMapTile, TiledMapTile>();
		int topY=blkbgLayer.getHeight()-1;
		// find top tiles
		for(int i= 0;i<blkbgLayer.getWidth();i++){
			System.out.println("try "+i);
			if(blkbgLayer.getCell(i, topY)!=null){
				System.out.println("top tile for "+blkbgLayer.getCell(i, topY-1).getTile().getId()+" added");
				topTiles.put(blkbgLayer.getCell(i, topY-1 ).getTile(), blkbgLayer.getCell(i, topY).getTile());
				blkbgLayer.setCell(i, topY, null);
				blkbgLayer.setCell(i, topY-1, null);			
				
			}else{
				// no more tiles have toptiles
				break;
			}
		}
		
		
		
		// HUD

		dialogLine = new DialogLine(this);
		stage.addActor(dialogLine);
		
		GoldBar goldbar = new GoldBar(this);
		stage.addActor(goldbar);
		
		LifeBar lifebar=new LifeBar(this);
		stage.addActor(lifebar);
		
		weaponbar = new WeaponBar(this);
		stage.addActor(weaponbar);
		
		
		
		shop = new ShopScreen(game, this);
		pause = new PauseScreen(game, this);
		
		
		
		
	}
	
	@Override
	public void show() {

		// handle input
		Gdx.input.setInputProcessor(new GameInputProcessor());
		
	}
	@Override
	public void render(float delta) {
		// TODO remove this i3-resize workaround
		if(resized < 10){

			// resize at the beginning to set the letterbox right
			resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			resized++;
		}
		
		if(cooldownTime>0){
			cooldownTime-=delta;
		}
		// the user still presses on the screen
		if(usingWeapon){
			if(waitTime>0){
				waitTime-=delta;
			}else{
				if(Gdx.input.getX()>viewport.x&&Gdx.input.getX()<viewport.x+viewport.width&&Gdx.input.getY()>viewport.y&&Gdx.input.getY()<viewport.y+viewport.height){
					Vector3 touchPos = new Vector3();
					touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					stage.getCamera().unproject(touchPos,viewport.x,viewport.y,viewport.width,viewport.height);

					if(weapon.use(touchPos.x, touchPos.y)){
						cooldownTime=weapon.cooldown; 
						waitTime=weapon.wait;
					}
				}
			}
		}
		
		
		viewRect=new Rectangle(stage.getCamera().position.x-stage.getWidth()/2,stage.getCamera().position.y-stage.getHeight()/2,stage.getCamera().position.x+stage.getWidth(),stage.getCamera().position.y+stage.getHeight());
		
		// move all the stuff around and stuff
		stage.act(delta);

		
		// scroll camera to the player
		stage.getCamera().position.set(player.getX(),player.getY(),0);
		stage.getCamera().update();
		
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);  // TODO move this into an own method in AbstractScreen
		
		renderer.setView((OrthographicCamera) stage.getCamera());
		renderer.getSpriteBatch().begin();
		renderer.renderTileLayer(bgLayer);
		renderer.renderTileLayer(destLayer);
		renderer.renderTileLayer(blkbgLayer);
		renderer.getSpriteBatch().end();
		stage.draw();
		renderer.getSpriteBatch().begin();
		renderer.renderTileLayer(fgLayer);
		renderer.getSpriteBatch().end();
		// super.render(delta);
	}

	@Override
	public void dispose() {
		super.dispose();
		renderer.dispose();
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		renderer.setView((OrthographicCamera)stage.getCamera());
	}
	
	class GameInputProcessor extends InputAdapter{
				
		@Override
		public boolean keyDown(int keycode) {
			if(!Event.isEmpty()){return false;}
			switch(keycode){
				case Keys.LEFT:
				case Keys.RIGHT:
				case Keys.A:
				case Keys.D:
					player.speedX= Gdx.input.isKeyPressed(Keys.RIGHT)?1:0-(Gdx.input.isKeyPressed(Keys.LEFT)?1:0)+(Gdx.input.isKeyPressed(Keys.D)?1:0)-(Gdx.input.isKeyPressed(Keys.A)?1:0);
					player.walk();
					break;
				case Keys.UP:
				case Keys.SPACE:
				case Keys.W:
					player.jump();
					break;
				case Keys.DOWN:
				case Keys.S:
					player.descend();
					break;
			//	case Keys.E:
				//	game.setScreen(shop); // TODO only when going into house
					//break;
			/*	case Keys.S: // dig down
					digTile(player.getStandingX(),player.getStandingY(), 1);
					player.dig();
					//colLayer.getCell((int)(player.getX()/colLayer.getTileWidth()), (int)(player.getY()/colLayer.getTileHeight())).setTile();
					break;
				case Keys.A: // dig left
					digTile(player.getStandingX()-1,player.getMiddleY(), 1);
					break;
				case Keys.D: // dig right
					digTile(player.getStandingX()+1,player.getMiddleY(), 1);
					break;
				case Keys.Q: // deploy a bomb
					Bomb bomb =new Bomb(IngameScreen.this);
					bomb.setPosition(player.getX()+player.getWidth()/2-bomb.getWidth()/2, player.getY()+player.getHeight()/2-bomb.getHeight()/2);
					bomb.speedX = player.speedX*2;
					bomb.speedY = player.speedY;
					stage.addActor(bomb);
	*/
				/*case Keys.E:
					// swing your sword, mighty hero!
					player.sword(player.isFlipped);
					break;
				case Keys.Y:
					if(player.onGround || player.onLadder){
						// place a ladder						
						Ladder ladder=new Ladder(IngameScreen.this);
						ladder.setPosition(player.getStandingX()*destLayer.getTileWidth(), player.getY()+player.getHeight()/2);
						
						if(ladder.notOnLadder()){ // only place it, if there is no other ladder
							ladders.addActor(ladder);
						}
					}*/
					
				case Keys.NUM_1:
					weaponbar.selectId(0);
					break;
				case Keys.NUM_2:
					weaponbar.selectId(1);
					break;
				case Keys.NUM_3:
					weaponbar.selectId(2);
					break;
				case Keys.NUM_4:
					weaponbar.selectId(3);
					break;
				case Keys.NUM_5:
					weaponbar.selectId(4);
					break;
				case Keys.NUM_6:
					weaponbar.selectId(5);
					break;
				case Keys.ESCAPE:
					game.setScreen(pause);
					break;
				case Keys.R:
					resetMap(game);
					game.setScreen(new IngameScreen(game));
					break;
			}
			
			return super.keyDown(keycode);
		}
		@Override
		public boolean keyUp(int keycode) {
			
			switch(keycode){
				case Keys.LEFT:
				case Keys.RIGHT:
				case Keys.A:
				case Keys.D:
					player.speedX= Gdx.input.isKeyPressed(Keys.RIGHT)?1:0-(Gdx.input.isKeyPressed(Keys.LEFT)?1:0)+(Gdx.input.isKeyPressed(Keys.D)?1:0)-(Gdx.input.isKeyPressed(Keys.A)?1:0);
					player.walk();
					break;
				case Keys.UP:
				case Keys.DOWN:
				case Keys.W:
				case Keys.S:
					player.stopClimbing();
					break;
			}
			return super.keyUp(keycode);
		}
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {		
			if(!Event.isEmpty()){return false;}
			
			if(Gdx.input.getX()>viewport.x&&Gdx.input.getX()<viewport.x+viewport.width&&Gdx.input.getY()>viewport.y&&Gdx.input.getY()<viewport.y+viewport.height){
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				stage.getCamera().unproject(touchPos,viewport.x,viewport.y,viewport.width,viewport.height);

				
				usingWeapon = true;
				
				
				if(touchPos.x>stage.getCamera().position.x + stage.getWidth()/2 - 64){
					if(weaponbar.open){
						weaponbar.select(touchPos.y-stage.getCamera().position.y-stage.getHeight()/2);
						weaponbar.close();
					}else{
						weaponbar.open();
					}
					return false;
				}
				if(weaponbar.open){ // the user clicked somewhere else
					weaponbar.close();
				}
				
				
				
				// System.out.println("touch: "+touchPos.x+","+touchPos.y);
				// System.out.println("playa: "+player.getX()+","+player.getY());
				
				if(cooldownTime<=0&&weapon.use(touchPos.x, touchPos.y)){
					cooldownTime=weapon.cooldown;
					waitTime=weapon.wait;
				}else{
					waitTime=cooldownTime;
				}
		
				
			}
			return false;
		}
		
		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			usingWeapon=false;
			return super.touchUp(screenX, screenY, pointer, button);
		}
	
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param power
	 * @return if there was a tile that we could dig
	 */
	public boolean digTile(int x,int y, int power){
		return this.digTile(x,y,power,false);
	}
	public boolean digTile(int x,int y, int power, boolean sound){
		Cell dst=destLayer.getCell(x, y);
		if(dst != null){
			TiledMapTile tile = dst.getTile();
			if(tile != null){
				System.err.println("tile: "+tile.getId());
				
			/*	Iterator<String> it =tile.getProperties().getKeys();
				
				while(it.hasNext()){
					System.out.println(it.next());
				}*/
				if(destroyable[tile.getId()]>power){
					// dah. we don't have enough power
					if(sound){
						no_dig.play();
					}
					
					return false;
				}
				
				/*if(tile.getProperties().get("dst")!=null && tile.getProperties().get("dst").equals("no")){
					return;
				}*/
			}else{
				return false;
			}
		}else{
			return false;
		}
		// colLayer.setCell(x, y, null);
		blkbgLayer.setCell(x, y+1, null);
		destLayer.setCell(x, y, null);
		
		//place the top tile of the tile below
		if(destLayer.getCell(x, y-1)!=null && topTiles.containsKey(destLayer.getCell(x, y-1).getTile())){
			Cell cell=new Cell();
			cell.setTile(topTiles.get(destLayer.getCell(x, y-1).getTile()));
			blkbgLayer.setCell(x, y, cell);
			System.out.println("replace :)");
		}else{
			System.err.println("nothing found");
		}
		 
		
		
		return true;
	}

	public static void resetMap(LDGame game) {
		game.assets.unload("maps/map1.tmx");
		game.assets.load("maps/map1.tmx", TiledMap.class); // undo all the destruction the player has done :P
		game.assets.finishLoading();
	}
	
}
