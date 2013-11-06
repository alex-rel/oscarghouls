package com.itcj.oscarghouls.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class NetBullet {
	
	
	private float height;
	private float width;
	
	private float speed = 1f;
	
	private float stateTime = 0f;
	
	private boolean facingLeft;
	
	
	Vector2 position;
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public NetBullet(Vector2 position, boolean facingLeft){
		this.position = position;
		this.height = .7f;
		this.width = .7f;
		bounds.width = width;
		bounds.height = height;
		bounds.x = position.x;
		bounds.y = position.y;
		this.facingLeft = facingLeft;
		if(facingLeft){
			velocity.x = -speed;
		}
		else{
			velocity.x = speed;
		}
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
	
	public void update(float delta){
		position.add(velocity.cpy().scl(delta));
	}


	public float getStateTime() {
		return stateTime;
	}


	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}


	public boolean isFacingLeft() {
		return facingLeft;
	}


	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}



}