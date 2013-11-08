package com.itcj.oscarghouls.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Item {
	
	public enum Type{
		LIFEKIT, AMMO, SCOUT;
	}
	
	private float height;
	private float width;
	
	private float speed = 1f;
	private Type type;
	private int puntos;
	
	private int duration;
	
	private float stateTime = 0f;
	
	
	Vector2 position;
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Item(Vector2 position, Type type){
		this.position = position;
		this.setType(type);
		switch(type){
			case LIFEKIT:
				velocity.y = -speed;
				puntos=0;
				this.height = .5f;
				this.width = .5f;
				break;
			case AMMO:
				velocity.y = -speed;
				puntos=0;
				this.height = 1f;
				this.width = 1f;
				break;
			case SCOUT:
				velocity.y = -speed;
				puntos=500;
				this.height = 1f;
				this.width = 1.5f;
				break;
			default:
				break;
		}
		bounds.width = width;
		bounds.height = height;
		bounds.x = position.x;
		bounds.y = position.y;
		duration = 3;
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
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
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


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}



}