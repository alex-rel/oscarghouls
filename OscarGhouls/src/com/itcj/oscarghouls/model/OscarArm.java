package com.itcj.oscarghouls.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.itcj.oscarghouls.OscarGhouls;

public class OscarArm {
	
	public static final float WIDTH = 2.5f;
	public static final float HEIGHT = 2.7f;
	
	public enum States{
		IDLE, WALKING, CATCHING, SHOOTING;
	}
	
	private float height;
	private float width;
	
	private float speed = 2f;
	float stateTime = 0;
	
	private States state;
	
	Vector2 position;
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public OscarArm(Vector2 position){
		this.position = position;
		this.height = HEIGHT;
		this.width = WIDTH;
		bounds.width = width;
		bounds.height = height;
		bounds.x = position.x;
		bounds.y = position.y;
		state = States.IDLE;
	}
	
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public void setPositionX(float x){
		this.position.x = x;
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
	
	public void avanzar(){
		velocity.x = speed;
	}
	
	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}
	
	public float getStateTime() {
		return stateTime;
	}


	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	
	public void retroceder(){
		velocity.x = -speed;
	}
	
	public void stop(){
		velocity.x = 0;
	}
	
	public void update(float delta){
		stateTime += delta;
		position.add(velocity.cpy().scl(delta));
		if(position.x < 0.38f){
			position.x = 0;
		}
		if(position.x > (OscarGhouls.CAMERA_WIDTH - width) - .38f){
			position.x = OscarGhouls.CAMERA_WIDTH - width;
		}
	}
	

	
}
