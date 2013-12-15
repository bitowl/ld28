package de.bitowl.ld28;

import de.bitowl.ld28.screens.IngameScreen;

public class Event {
	public void happen(IngameScreen pScreen){}
	
	
	public static Event[] events = {
		new Event(){
			@Override
			public void happen(IngameScreen pScreen) {
				// TODO Auto-generated method stub
				
			}
		},
		 new Event() {
			@Override
			public void happen(IngameScreen pScreen) {
				pScreen.dialogLine.display("Might be useful to shop some things before your adventure", 1f);
			}
		}
	}; 
}
