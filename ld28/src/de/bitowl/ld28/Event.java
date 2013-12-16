package de.bitowl.ld28;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

import de.bitowl.ld28.objects.Skeleton;
import de.bitowl.ld28.screens.IngameScreen;

public class Event {
	public static final Event BOSS_SPAWN_EVENT =new Event(){
		@Override
		public void happen(IngameScreen pScreen) {
			super.happen(pScreen);
			// spawn dat badass skeleton
			Skeleton skeleton = new Skeleton(pScreen);
			skeleton.setX(278*32);
			skeleton.setY(5*32);
			pScreen.enemies.addActor(skeleton);
			
			Event.eventFinished(pScreen);
			
		}
	};
	public boolean happend;
	
	public void happen(IngameScreen pScreen){
		happend=true;
	}
	
	
	//// MANAGER START
	// put in the same class to write things quicker
	public static Array<Event> queue=new Array<Event>();
	
	public static boolean isEmpty(){
		return queue.size==0;
	}
	public static void doIt(Event pEvent, IngameScreen pScreen){
		queue.add(pEvent);
		if(queue.size==1){
			pEvent.happen(pScreen);
		}
	}
	
	public static void eventFinished(IngameScreen pScreen){
		System.err.println("EVENT FINISHED");
		queue.removeIndex(0);
		if(!isEmpty()){
			queue.get(0).happen(pScreen);
		}
	}
	
	//// MANAGER END
	
	public static Event[] events = {
		new Event(){
			@Override
			public void happen(IngameScreen pScreen) {
				
			}
		},
		 new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				super.happen(pScreen);
				doIt(new DialogEvent("This is the shop. it might be useful to", 2.5f,"event1_1"),pScreen);
				doIt(new DialogEvent("buy some things before your adventure", 2.5f,"event1_2"),pScreen);
				doIt(new DialogEvent("Remember: You only got one gold.", 3f,"event1_3"),pScreen);
				eventFinished(pScreen); // this meta event finished
			}
		},
		 new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				super.happen(pScreen);
				if(!events[1].happend){
					doIt(new DialogEvent("Why not go left and check out the shop?", 2.3f,"event2"),pScreen);	
				}
				eventFinished(pScreen);
			}
		},
		 new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				super.happen(pScreen);
				doIt(new DialogEvent("Oh no, the mine collapsed!", 2.6f,"event3_1"),pScreen);
				doIt(new DialogEvent("You need something to dig away the dirt.", 2.3f,"event3_2"),pScreen);
			
				eventFinished(pScreen);
			}
		},
		 new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				super.happen(pScreen);
				doIt(new DialogEvent("WARNING: Might be dangerous to fall down there!", 3f,"event4"),pScreen);
			
				eventFinished(pScreen);
			}
		},
		 new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				super.happen(pScreen);
				doIt(new DialogEvent("Do you see that shiny thing?", 1.9f,"event5_1"),pScreen);
				doIt(new DialogEvent("You should collect it!", 1.5f,"event5_2"),pScreen);
			
				eventFinished(pScreen);
			}
		}, 
		
				
		
		new Event() {
			@Override
			public void happen(final IngameScreen pScreen) {
				super.happen(pScreen);
				final Image friend = new Image(pScreen.atlas.findRegion("friend_down"));

				friend.setX(136*32);
				friend.setY(120*32);
				pScreen.stage.addActor(friend);
				pScreen.player.addAction(Actions.sequence(Actions.moveBy(-11*32, 0,0.7f),new Action() {
					public boolean act(float delta) {
						eventFinished(pScreen);
						return true;
					}
				}));

				doIt(new DialogEvent("Thanks for saving me!", 1.5f,"event6_1"),pScreen);
				doIt(new DialogEvent("Go on and save the others!", 2f,"event6_2"),pScreen);
				doIt(new Event(){
					public void happen(final IngameScreen pScreen){
						AnimAction anim=new AnimAction(new Animation(0.1f, pScreen.atlas.findRegions("friend_go")),true);
						friend.addAction(anim);
						friend.addAction(Actions.sequence(Actions.moveBy(20*32,0,1.5f),new Action() {
							public boolean act(float delta) {
								friend.remove();
								eventFinished(pScreen);
								return true;
							}
						}));
					}
				}, pScreen);
			}
		},
		new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				super.happen(pScreen);
				doIt(new DialogEvent("WATCH OUT", 1f,"event7_1"),pScreen);
				doIt(new DialogEvent("These monsters might hurt you!", 2.3f,"event7_2"),pScreen);
			
				eventFinished(pScreen);
			}
		}, 
		new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				super.happen(pScreen);
				doIt(new DialogEvent("Want a secret tipp?", 1.7f,"event8_1"),pScreen);
				doIt(new DialogEvent("Explore before you continue", 2.3f,"event8_2"),pScreen);
			
				eventFinished(pScreen);
			}
		}, 
		new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				super.happen(pScreen);
				doIt(new DialogEvent("Actually, well, I'm not sure", 3.1f,"event9_1"),pScreen);
				doIt(new DialogEvent("if you really want to go down there to DIE!", 3.5f,"event9_2"),pScreen);
			
				eventFinished(pScreen);
			}
		}, 
	}; 
	
	class DialogEvent extends Event{
		String text;
		float time;
		String sound;
		public DialogEvent(String pText, float pTime){
			text = pText;
			time = pTime;
		}
		public DialogEvent(String pText, float pTime, String pSound){
			text = pText;
			time = pTime;
			sound = pSound;		
		}
		@Override
		public void happen(IngameScreen pScreen) {
			super.happen(pScreen);
			if(sound!=null){
				Sound snd=pScreen.game.assets.get("audio/"+sound+".ogg",Sound.class);
				snd.play();
			}
			pScreen.dialogLine.display(text, time, true);
			
		}
	}
}
