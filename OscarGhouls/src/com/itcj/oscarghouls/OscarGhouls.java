package com.itcj.oscarghouls;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.itcj.oscarghouls.screens.GameOverScreen;
import com.itcj.oscarghouls.screens.GameScreen;
import com.itcj.oscarghouls.screens.PresentationScreen;


public class OscarGhouls extends Game {
	
	public static final float CAMERA_WIDTH = 10f;
	public static final float CAMERA_HEIGHT = 6f;
	
	public static Music bgSound;
	public static Sound hurt;
	public static Sound swing;
	public static Sound catched;
	
	public GameScreen gameScreen = new GameScreen(this);
	public PresentationScreen presentationScreen = new PresentationScreen(this);
	public GameOverScreen gameOverScreen = new GameOverScreen(this);

	@Override
	public void create() {
		// TODO Auto-generated method stub
	 bgSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/bgmusic.wav"));
	hurt = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.wav"));
	swing = Gdx.audio.newSound(Gdx.files.internal("sounds/swing.wav"));
	catched = Gdx.audio.newSound(Gdx.files.internal("sounds/catched.wav"));
		setScreen(presentationScreen);
	}
	
	public void dispose(){
		bgSound.dispose();
		hurt.dispose();
		swing.dispose();
	}
	
}
