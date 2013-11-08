package com.itcj.oscarghouls.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.itcj.oscarghouls.OscarGhouls;

public class Oscar {
	
	public enum States{
		IDLE, WALKING, CATCHING, HURT;
	}
	
	private float height;
	private float width;
	
	private boolean facingLeft;
	
	private float speed = 2f;
	float stateTime = 0;
	
	private States state;
	
	private int stamina;
	
	Vector2 position;
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Oscar(Vector2 position){
		this.position = position;
		this.height = 1.8f;
		this.width = 1.5f;
		bounds.width = width;
		bounds.height = height;
		bounds.x = position.x;
		bounds.y = position.y;
		facingLeft = false;
		state = States.IDLE;
		stamina = 5;
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
	
	public boolean isFacingLeft(){
		return facingLeft;
	}
	
	public void setFacingLeft(boolean facingLeft){
		this.facingLeft = facingLeft;
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
		if(position.x - OscarArm.WIDTH < 0 && isFacingLeft()){
			position.x = OscarArm.WIDTH - .3f;
		}
		if(position.x - .3f < 0 && !isFacingLeft()){
			position.x = 0f;
		}
		if(position.x + OscarArm.WIDTH  > (OscarGhouls.CAMERA_WIDTH - width) && !isFacingLeft()){
			position.x = OscarGhouls.CAMERA_WIDTH - width - OscarArm.WIDTH + .3f;
		}
		if(position.x + .3f  > (OscarGhouls.CAMERA_WIDTH - width) && isFacingLeft()){
			position.x = OscarGhouls.CAMERA_WIDTH - width;
		}
	}


	public int getStamina() {
		return stamina;
	}


	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	

	
}
