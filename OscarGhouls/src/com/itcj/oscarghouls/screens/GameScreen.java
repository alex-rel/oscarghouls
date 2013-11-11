package com.itcj.oscarghouls.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

import com.itcj.oscarghouls.OscarGhouls;
import com.itcj.oscarghouls.model.Oscar;
import com.itcj.oscarghouls.model.OscarArm;
import com.itcj.oscarghouls.model.Stage;
import com.itcj.oscarghouls.view.Renderer;


public class GameScreen implements Screen, InputProcessor{
	OscarGhouls mainGame;	//Referencia al mismo juego
	Stage stage;
	Renderer stageRenderer;
	int width;
	int height;
	


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
		if (stageRenderer.isGameOver()){
			OscarGhouls.bgSound.stop();
			mainGame.setScreen(mainGame.gameOverScreen);
		}
		stageRenderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stageRenderer.resize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		stageRenderer = new Renderer(stage);
		Gdx.input.setInputProcessor(this);
		OscarGhouls.bgSound.setLooping(true);
		OscarGhouls.bgSound.play();
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
		if(keycode == Keys.LEFT){
			stage.getOscar().setState(Oscar.States.WALKING);
			stage.getOscar().setFacingLeft(true);
			stage.getArm().setPositionX(stage.getOscar().getPosition().x - stage.getArm().getWidth() + .3f);
			stage.getOscar().retroceder();
			stage.getArm().retroceder();
		}
		if(keycode == Keys.RIGHT){
			stage.getOscar().setState(Oscar.States.WALKING);	
			stage.getOscar().setFacingLeft(false);
			stage.getArm().setPositionX(stage.getOscar().getPosition().x + stage.getOscar().getWidth() - .3f);
			stage.getOscar().avanzar();
			stage.getArm().avanzar();
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		stage.getOscar().setState(Oscar.States.IDLE);
		stage.getArm().setState(OscarArm.States.IDLE);
		stage.getOscar().stop();
		stage.getArm().stop();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	
		//Boton Izquierdo Presionado
		if(stage.getButtonLeft().touched(screenX, screenY, width, height)){
			stage.getOscar().setState(Oscar.States.WALKING);
			stage.getOscar().setFacingLeft(true);
			stage.getArm().setPositionX(stage.getOscar().getPosition().x - stage.getArm().getWidth() + .3f);
			stage.getOscar().retroceder();
			stage.getArm().retroceder();
		}
		//Boton Derecho Presionado
		if(stage.getButtonRight().touched(screenX, screenY, width, height)){
			stage.getOscar().setState(Oscar.States.WALKING);	
			stage.getOscar().setFacingLeft(false);
			stage.getArm().setPositionX(stage.getOscar().getPosition().x + stage.getOscar().getWidth() - .3f);
			stage.getOscar().avanzar();
			stage.getArm().avanzar();
		}
		
		//Botones de Accion Presionados
		
		if(stage.getAction1().touched(screenX, screenY, width, height)){
			stage.getArm().setState(OscarArm.States.CATCHING);
			OscarGhouls.swing.play();
		}
		
		if(stage.getAction2().touched(screenX, screenY, width, height)){
			stage.getArm().setState(OscarArm.States.SHOOTING);
			float posX = stage.getArm().getPosition().x;
			float posY = 1.4f;
			if(stage.getOscar().isFacingLeft()){
				stage.netShot(new Vector2(posX + stage.getArm().getWidth()  - 1.5f, posY), true);
			}
			else{
				stage.netShot(new Vector2(posX + .8f, posY), false);
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		stage.getOscar().setState(Oscar.States.IDLE);
		stage.getArm().setPreviousState(stage.getArm().getState());
		stage.getArm().setState(OscarArm.States.IDLE);
		stage.getOscar().stop();
		stage.getArm().stop();
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
