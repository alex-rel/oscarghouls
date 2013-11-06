package com.itcj.oscarghouls.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.itcj.oscarghouls.OscarGhouls;
import com.itcj.oscarghouls.model.Button;

public class GameOverScreen implements Screen, InputProcessor{
	OscarGhouls mainGame;
	Texture texturaFondo;
	OrthographicCamera cam;
	SpriteBatch batch;
	Button play;
	Button options;
	Button help;
	
	TextureAtlas actors;
	TextureAtlas screens;
	TextureRegion screen = new TextureRegion();
	
	int width;
	int height;
	float ppuX;
	float ppuY;
	
	public GameOverScreen(OscarGhouls mainGame){
		this.mainGame = mainGame;
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		//Limpiamos constantemente la pantalla antes de insertar cualquier elemento
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
			batch.draw(screen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		ppuX = width / OscarGhouls.CAMERA_WIDTH;
		ppuY = height / OscarGhouls.CAMERA_HEIGHT;
	}

	@Override
	public void show() {
		actors = new TextureAtlas(Gdx.files.internal("actors.pack"));
		screens = new TextureAtlas(Gdx.files.internal("screens.pack"));
		screen= screens.findRegion("gameover");
		cam = new OrthographicCamera(OscarGhouls.CAMERA_WIDTH, OscarGhouls.CAMERA_HEIGHT);
		cam.position.set(OscarGhouls.CAMERA_WIDTH/2, OscarGhouls.CAMERA_HEIGHT/2, 0);
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		play = new Button(new Vector2(0,1), 2f, 1f, Button.Type.PLAY);
		options = new Button(new Vector2(2.1f,1), 2f, 1f, Button.Type.OPTIONS);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(play.touched(screenX, screenY, width, height)){
			mainGame.setScreen(mainGame.gameScreen);
		}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
