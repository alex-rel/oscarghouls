package com.itcj.oscarghouls.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.itcj.oscarghouls.OscarGhouls;

public class Stamina {
	
	private float height;
	private float width;
	
	Vector2 position;
	Rectangle bounds = new Rectangle();
	
	public Stamina(Vector2 position){
			this.position = position;
			this.width = 4f;
			this.height= .5f;
			this.bounds.width = width;
			this.bounds.height = height;
			this.bounds.x = position.x;
			this.bounds.y = position.y;
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
	
	
	
}
