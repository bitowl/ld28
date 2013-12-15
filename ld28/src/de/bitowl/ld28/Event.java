package de.bitowl.ld28;

import java.util.Queue;

import com.badlogic.gdx.utils.Array;

import de.bitowl.ld28.screens.IngameScreen;

public class Event {
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
				pScreen.dialogLine.display("Might be useful to shop some things before your adventure", 1f,true);
			}
		}
	}; 
}
