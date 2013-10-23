package com.itcj.oscarghouls.model;

import com.badlogic.gdx.math.Vector2;
import com.itcj.oscarghouls.OscarGhouls;

public class Background {
	float width;
	float height;
	
	Vector2 position;
	
	public Background(){
		position = new Vector2(0,0);
		width = OscarGhouls.CAMERA_WIDTH;
		height = OscarGhouls.CAMERA_HEIGHT;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public Vector2 getPosition(){
		return position;
	}

}
