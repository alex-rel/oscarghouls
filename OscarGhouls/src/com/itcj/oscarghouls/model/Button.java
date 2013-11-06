package com.itcj.oscarghouls.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.itcj.oscarghouls.OscarGhouls;

public class Button {
	
	public enum Type{
		LEFT, RIGHT, ACTION, SUPER, PAUSE, START, OPTIONS, HELP, PLAY;
	}
	
	private float height;
	private float width;
	
	private Type type;
	
	private boolean touched;
	
	
	Vector2 position;
	Rectangle bounds = new Rectangle();
	
	public Button(Vector2 position, float width,  float height, Type type){
			this.position = position;
			this.width = width;
			this.height= height;
			this.bounds.width = width;
			this.bounds.height = height;
			this.bounds.x = position.x;
			this.bounds.y = position.y;
			this.touched = false;
			this.type = type;
	}
	
	
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public boolean touched(int posX, int posY, int screenWidth, int screenHeight){
		float camViewX = (float)screenWidth / OscarGhouls.CAMERA_WIDTH;
		float internalX = (float)posX/camViewX;
		
		float camViewY = (float)screenHeight / OscarGhouls.CAMERA_HEIGHT;
		float internalY = (float)OscarGhouls.CAMERA_HEIGHT - ((float)posY/camViewY);

		if(this.getBounds().contains(internalX, internalY)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void unTouched( ){
		this.touched =false;
	}
	
}
