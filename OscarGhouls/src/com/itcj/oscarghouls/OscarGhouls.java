package com.itcj.oscarghouls;

import com.badlogic.gdx.Game;
import com.itcj.oscarghouls.screens.GameOverScreen;
import com.itcj.oscarghouls.screens.GameScreen;
import com.itcj.oscarghouls.screens.PresentationScreen;


public class OscarGhouls extends Game {
	
	public static final float CAMERA_WIDTH = 10f;
	public static final float CAMERA_HEIGHT = 6f;
	
	public GameScreen gameScreen = new GameScreen(this);
	public PresentationScreen presentationScreen = new PresentationScreen(this);
	public GameOverScreen gameOverScreen = new GameOverScreen(this);

	@Override
	public void create() {
		// TODO Auto-generated method stub
		setScreen(presentationScreen);
	}
	
}
