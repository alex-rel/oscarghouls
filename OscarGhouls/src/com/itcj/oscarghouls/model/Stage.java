package com.itcj.oscarghouls.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.itcj.oscarghouls.OscarGhouls;

public class Stage {
	
		Oscar oscar;
		ArrayList<Ghost> fantasmas = new ArrayList<Ghost>();
		
		public Stage(){
			createStage();
		}
		
		private void createStage(){
			oscar = new Oscar(new Vector2(3,.5f));
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

		public void setOscar(Oscar oscar) {
			this.oscar = oscar;
		}
		
		public ArrayList<Ghost> getFantasmas() {
			return fantasmas;
		}

		public void setFantasmas(ArrayList<Ghost> fantasmas) {
			this.fantasmas = fantasmas;
		}
}
