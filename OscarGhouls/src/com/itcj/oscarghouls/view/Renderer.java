package com.itcj.oscarghouls.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.itcj.oscarghouls.OscarGhouls;
import com.itcj.oscarghouls.model.Ghost;
import com.itcj.oscarghouls.model.Oscar;
import com.itcj.oscarghouls.model.Stage;

public class Renderer {
	
	public static final long GHOST_FRECUENCY = 1000000000;		//Frecuencia en nanosegundos con la cual estaran apareciendo los fantasmas(1 seg)
	
	Stage stage;
	Texture oscarTexture;
	Texture fondoTexture;
	Texture fantasma1;
	Texture fantasma2;
	SpriteBatch batch;
	OrthographicCamera cam;
	
	long lastGhost;					//Guarda el tiempo en que se envio el ultimo fantasma
	
	int viewWidth;
	int viewHeight;
	float ppuX;
	float ppuY;
	
	public Renderer(Stage stage){
		this.stage = stage;
		cam = new OrthographicCamera(OscarGhouls.CAMERA_WIDTH, OscarGhouls.CAMERA_HEIGHT);
		cam.position.set(OscarGhouls.CAMERA_WIDTH/2, OscarGhouls.CAMERA_HEIGHT/2, 0);
		batch = new SpriteBatch();
		loadTextures();
		lastGhost = TimeUtils.nanoTime();
	}
	
	public void render(float delta){
		Oscar oscar = stage.getOscar();
		if(TimeUtils.nanoTime() - lastGhost > GHOST_FRECUENCY * 4){
			stage.sendGhost();
			lastGhost = TimeUtils.nanoTime();
		}
		batch.begin();
		batch.draw(fondoTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(oscarTexture, oscar.getPosition().x * ppuX , oscar.getPosition().y * ppuY , oscar.getWidth() * ppuX, oscar.getHeight() * ppuY);
		for(Ghost ghost : stage.getFantasmas()){
			switch(ghost.getType()){
				case 1:
					batch.draw(fantasma1, ghost.getPosition().x * ppuX , ghost.getPosition().y * ppuY , ghost.getWidth() * ppuX, ghost.getHeight() * ppuY);
					break;
				case 2:
					batch.draw(fantasma2, ghost.getPosition().x * ppuX , ghost.getPosition().y * ppuY , ghost.getWidth() * ppuX, ghost.getHeight() * ppuY);
					break;
				default:
					break;
			}
		}
		batch.end();
		oscar.update(delta);
		//Actualizamos los fantasmas
		for(Ghost ghost : stage.getFantasmas()){
			ghost.update(delta);
		}
	}
	
	public void resize(int width, int height){
		viewWidth = width;
		viewHeight = height;
		ppuX = viewWidth / OscarGhouls.CAMERA_WIDTH;
		ppuY = viewHeight / OscarGhouls.CAMERA_HEIGHT;
	}
	
	private void loadTextures(){
		oscarTexture = new Texture(Gdx.files.internal("oscar.png"));
		fondoTexture = new Texture(Gdx.files.internal("stage.png"));
		fantasma1 = new Texture(Gdx.files.internal("fantasma2.png"));
		fantasma2 = new Texture(Gdx.files.internal("fantasma2.png"));
	}
}
