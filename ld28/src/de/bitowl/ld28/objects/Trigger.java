package de.bitowl.ld28.objects;

import de.bitowl.ld28.Event;


public class Trigger {
	public int x, y;
	public Event event;
	public Trigger(int pX, int pY, Event pEvent){
		x=pX;
		y=pY;
		event=pEvent;
	}
}
