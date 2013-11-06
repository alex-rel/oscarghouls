package com.itcj.oscarghouls.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ghost {
	
	
	private float height;
	private float width;
	
	private float speed = 1f;
	private int type;
	private int puntos;
	
	private float stateTime = 0f;
	
	
	Vector2 position;
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Ghost(Vector2 position, int type){
		this.position = position;
		this.height = 1f;
		this.width = 1f;
		bounds.width = width;
		bounds.height = height;
		bounds.x = position.x;
		bounds.y = position.y;
		this.setType(type);
		switch(type){
			case 1:
				velocity.x = speed;
				puntos=100;
				break;
			case 2:
				velocity.x = -speed;
				puntos=200;
				break;
			default:
				break;
		}
	}
	
	
	public int getPuntos() {
		return puntos;
	}


	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}


	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setPositionX(float x) {
		this.position.x = x;
	}
	
	public void stop(){
		this.velocity.x = 0f;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public void update(float delta){
		setStateTime(getStateTime() + delta);
		position.add(velocity.cpy().scl(delta));
	}


	public float getStateTime() {
		return stateTime;
	}


	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}



}