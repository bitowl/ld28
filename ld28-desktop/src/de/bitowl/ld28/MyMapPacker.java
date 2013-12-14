package de.bitowl.ld28;

import java.io.File;

import com.badlogic.gdx.tiledmappacker.TiledMapPacker;

public class MyMapPacker {
	public static void main(String[] args){
		String[] args2 = {"/home/bitowl/git/ld28/ld28-stuff/maps", "/home/bitowl/git/ld28/ld28-android/assets/maps","--strip-unused"};
		
		// clean the old tilemaps
		for(File file: new File(args2[1]+"/tileset").listFiles()){
			file.delete();
		}
		
		TiledMapPacker.main(args2);
	}
}
