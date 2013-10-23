package com.itcj.oscarghouls.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.itcj.oscarghouls.OscarGhouls;
import com.itcj.oscarghouls.model.Ghost;
import com.itcj.oscarghouls.model.Oscar;
import com.itcj.oscarghouls.model.Stage;

public class Renderer {
	
	public static final long GHOST_FRECUENCY = 1000000000;		//Frecuencia en nanosegundos con la cual estaran apareciendo los fantasmas(1 seg)
	
	Stage stage;
	TextureAtlas screens = new TextureAtlas(Gdx.files.internal("screens.pack"));
	TextureAtlas actors = new TextureAtlas(Gdx.files.internal("actors.pack"));;
	TextureRegion oscarTexture = new TextureRegion();
	TextureRegion oscarLeft;
	TextureRegion fondoTexture = new TextureRegion();
	TextureRegion fantasma1 = new TextureRegion();
	TextureRegion fantasma2 = new TextureRegion();
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
		//Dibujar el Fondo
		batch.draw(fondoTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Dibujar a Oscar
		if(oscar.isFacingLeft()){
			batch.draw(oscarLeft, oscar.getPosition().x * ppuX , oscar.getPosition().y * ppuY , oscar.getWidth() * ppuX, oscar.getHeight() * ppuY);
		}
		else{
			batch.draw(oscarTexture, oscar.getPosition().x * ppuX , oscar.getPosition().y * ppuY , oscar.getWidth() * ppuX, oscar.getHeight() * ppuY);
		}
		//Dibujar Fantasmas
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
		oscarTexture = actors.findRegion("oscar");
		oscarLeft = new TextureRegion(oscarTexture);
		oscarLeft.flip(true, false);
		fondoTexture = screens.findRegion("stage");
		fantasma1 = actors.findRegion("fantasma1");
		fantasma2 = actors.findRegion("fantasma2");
	}
}
