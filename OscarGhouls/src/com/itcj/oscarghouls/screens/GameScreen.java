package com.itcj.oscarghouls.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

import com.itcj.oscarghouls.OscarGhouls;
import com.itcj.oscarghouls.model.Oscar;
import com.itcj.oscarghouls.model.Stage;
import com.itcj.oscarghouls.view.Renderer;


public class GameScreen implements Screen, InputProcessor{
	OscarGhouls mainGame;	//Referencia al mismo juego
	Stage stage;
	Renderer stageRenderer;

	//Constructor de GameScreen para hacer referencia al mismo juego y poder movernos entre
	//pantallas
	public GameScreen(OscarGhouls mainGame){
		this.mainGame = mainGame;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stageRenderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stageRenderer.resize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		stageRenderer = new Renderer(stage);
		Gdx.input.setInputProcessor(this);
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
		// TODO Auto-generated method stub
		System.out.println(screenX + "," + screenY);
		stage.getOscar().setState(Oscar.States.WALKING);
		if(screenX < Gdx.graphics.getWidth()/2){
			stage.getOscar().setFacingLeft(true);
			stage.getOscar().retroceder();
		}
		else{
			stage.getOscar().setFacingLeft(false);
			stage.getOscar().avanzar();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		stage.getOscar().setState(Oscar.States.IDLE);
		stage.getOscar().stop();
		return true;
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
