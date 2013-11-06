package com.itcj.oscarghouls.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.itcj.oscarghouls.OscarGhouls;

public class Stage {
	
		Oscar oscar;
		OscarArm arm;
		Button buttonLeft;
		

		Button buttonRight;
		Button action1;
		Button action2;
		
		long score;
		
		public long getScore() {
			return score;
		}

		public void setScore(long score) {
			this.score = score;
		}

		Stamina stamina;
		Array<Ghost> fantasmas = new Array<Ghost>();
		Array<NetBullet> netBullets = new Array<NetBullet>();
		
		public Stage(){
			createStage();
		}
		
		private void createStage(){
			oscar = new Oscar(new Vector2(3f,.5f));
			arm = new OscarArm(new Vector2(4.2f,0f));
			buttonLeft = new Button(new Vector2(0,.1f), 1f, 1f, Button.Type.LEFT);
			buttonRight = new Button(new Vector2(1,.1f), 1f, 1f, Button.Type.RIGHT);
			action1 = new Button(new Vector2(8,.1f), 1f, 1f, Button.Type.ACTION);
			action2 = new Button(new Vector2(9,.1f), 1f, 1f, Button.Type.ACTION);
			stamina = new Stamina(new Vector2(0,5));
			score = 0;
		}
		
		public Stamina getStamina() {
			return stamina;
		}

		public void setStamina(Stamina stamina) {
			this.stamina = stamina;
		}

		public OscarArm getArm() {
			return arm;
		}

		public void setArm(OscarArm arm) {
			this.arm = arm;
		}

		public Oscar getOscar() {
			return oscar;
		}
		
		public void sendGhost(){
			int type = 1 + (int)(Math.random() * ((2 - 1) + 1));
			switch(type){
				case 1:
					fantasmas.add(new Ghost(new Vector2(0, 1), type));
					break;
				case 2:
					fantasmas.add(new Ghost(new Vector2(OscarGhouls.CAMERA_WIDTH, 1), type));
					break;
				default:
					break;
			}
		}
		
		public void netShot(Vector2 position, boolean facingLeft){
			netBullets.add(new NetBullet(position, facingLeft));
		}

		public void setOscar(Oscar oscar) {
			this.oscar = oscar;
		}
		
		public Array<Ghost> getFantasmas() {
			return fantasmas;
		}

		public void setFantasmas(Array<Ghost> fantasmas) {
			this.fantasmas = fantasmas;
		}
		
		public Button getButtonLeft() {
			return buttonLeft;
		}

		public Button getButtonRight() {
			return buttonRight;
		}

		public Button getAction1() {
			return action1;
		}

		public Button getAction2() {
			return action2;
		}
		
		//Movimiento de los personajes
		public void moveOscar(float delta){
			oscar.update(delta);
		}
		
		public void moveArm(float delta){
			overlapArm();
			arm.update(delta);
		}
		
		public void moveNetBullets(float delta){
			for(NetBullet netBullet : netBullets){
				overlapNetBullets(netBullet);
				netBullet.update(delta);
			}
		}
		
		public Array<NetBullet> getNetBullets() {
			return netBullets;
		}

		public void setNetBullets(Array<NetBullet> netBullets) {
			this.netBullets = netBullets;
		}

		public void moveGhosts(float delta){
			for(Ghost ghost : getFantasmas()){
				
				overlapGhosts(ghost);
				ghost.update(delta);
				limitsGhosts(ghost);
			}
		}
		
		public void limitsGhosts(Ghost ghost){
			switch(ghost.getType()){
				case 1:
					System.out.println("En case");
					if(ghost.getPosition().x == oscar.getPosition().x - 1f){
						ghost.setPositionX(oscar.getPosition().x - 1f);
						ghost.stop();
						System.out.println("Detenido");
					}
					break;
				case 2:
					if(ghost.getPosition().x == (oscar.getPosition().x + oscar.getWidth()) + 1f){
						ghost.stop();
						System.out.println("Detenido");
						ghost.setPositionX(oscar.getPosition().x + oscar.getWidth() + 1f);
					}
					break;
				default:
					break;
			}
		}
		
		public void overlapArm(){
			
			Rectangle armLimit = new Rectangle(arm.getPosition().x, arm.getPosition().y, arm.getWidth(), arm.getHeight());
			
			if (arm.getState() == OscarArm.States.CATCHING){
				for(Ghost ghost : getFantasmas()){
					Rectangle ghostLimit = new Rectangle(ghost.getPosition().x, ghost.getPosition().y, ghost.getWidth(), ghost.getHeight());
					if(ghostLimit.overlaps(armLimit)) {
						fantasmas.removeValue(ghost, true);
						OscarGhouls.catched.play();
						score += ghost.getPuntos();
						System.out.println(score);
						
					}
				}
			}
		}
		
		//Overlap de los fantasmas
		public void overlapGhosts(Ghost ghost){
			Rectangle ghostLimit = new Rectangle(ghost.getPosition().x, ghost.getPosition().y, ghost.getWidth(), ghost.getHeight());
			Rectangle oscarLimit = new Rectangle(oscar.getPosition().x, oscar.getPosition().y, oscar.getWidth(), oscar.getHeight());
			
			if(ghostLimit.overlaps(oscarLimit)) {
				oscar.setStamina(oscar.getStamina() - 1);
				fantasmas.removeValue(ghost, true);
				OscarGhouls.hurt.play();
				
			}
		}
		
		public void overlapNetBullets(NetBullet netBullet){
			Rectangle netBulletLimit = new Rectangle(netBullet.getPosition().x, netBullet.getPosition().y, netBullet.getWidth(), netBullet.getHeight());
			
			for (Ghost ghost : fantasmas){
				Rectangle ghostLimit = new Rectangle(ghost.getPosition().x, ghost.getPosition().y, ghost.getWidth(), ghost.getHeight());
				if(ghostLimit.overlaps(netBulletLimit)) {
					System.out.println("herido");
					fantasmas.removeValue(ghost, true);
					netBullets.removeValue(netBullet, true);
					OscarGhouls.catched.play();
					score += ghost.getPuntos();
					System.out.println(score);
				}
			}
			
			
			
		
		}
}
