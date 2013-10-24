package com.itcj.oscarghouls.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
	public static final float WALKING_FRAME_DURATION = 0.08f;   //Tiempo que durara cada Keyframe de la animacion
	//Actores y Stages
	Stage stage;
	Oscar oscar;
	//Atlas
	TextureAtlas screens = new TextureAtlas(Gdx.files.internal("screens.pack"));
	TextureAtlas actors = new TextureAtlas(Gdx.files.internal("actors.pack"));
	//Textures
	TextureRegion oscarTexture = new TextureRegion();
	TextureRegion oscarLeft;
	TextureRegion fondoTexture = new TextureRegion();
	TextureRegion fantasma1 = new TextureRegion();
	TextureRegion fantasma2 = new TextureRegion();
	TextureRegion oscarFrame = new TextureRegion();
	TextureRegion fantasmaFrame = new TextureRegion();
	//Animations
	private Animation walkRightAnimation;
	private Animation walkLeftAnimation;
	private Animation ghostAnimation;
	
	SpriteBatch batch;
	OrthographicCamera cam;
	
	long lastGhost;					//Guarda el tiempo en que se envio el ultimo fantasma
	
	int viewWidth;
	int viewHeight;
	float ppuX;
	float ppuY;
	
	public Renderer(Stage stage){
		this.stage = stage;
		oscar = stage.getOscar();
		cam = new OrthographicCamera(OscarGhouls.CAMERA_WIDTH, OscarGhouls.CAMERA_HEIGHT);
		cam.position.set(OscarGhouls.CAMERA_WIDTH/2, OscarGhouls.CAMERA_HEIGHT/2, 0);
		batch = new SpriteBatch();
		loadTextures();
		lastGhost = TimeUtils.nanoTime();
	}
	
	public void render(float delta){
		
		if(TimeUtils.nanoTime() - lastGhost > GHOST_FRECUENCY * 4){
			stage.sendGhost();
			lastGhost = TimeUtils.nanoTime();
		}
		batch.begin();
		//Dibujar el Fondo
		batch.draw(fondoTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Dibujar a Oscar
		drawOscar();
		//Dibujar Fantasmas
		drawGhosts();
		batch.end();
		//Actualizar el Delta(posicion en el tiempo
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
		
		fondoTexture = screens.findRegion("stage");
		
		
		//Carga de Texturas y animacion de Oscar
		oscarTexture = actors.findRegion("oscar1");
		oscarLeft = new TextureRegion(oscarTexture);
		oscarLeft.flip(true, false);
		TextureRegion[] walkRightFrames = new TextureRegion[3];
		for(int i=0;i<3;i++){
			walkRightFrames[i] = actors.findRegion("oscar" + (i +1));
		}
		walkRightAnimation = new Animation(WALKING_FRAME_DURATION, walkRightFrames);
		TextureRegion[] walkLeftFrames = new TextureRegion[3];
		for(int i=0;i<3;i++){
			walkLeftFrames[i] = new TextureRegion(walkRightFrames[i]);
			walkLeftFrames[i].flip(true, false);
		}
		walkLeftAnimation = new Animation(WALKING_FRAME_DURATION, walkLeftFrames);
		
		// Carga de Texturas y animacion de los fantasmas
		fantasma1 = actors.findRegion("fantasma");
		fantasma2 = actors.findRegion("fantasma");
		TextureRegion[] ghostFrames = new TextureRegion[2];
		for(int i=0;i<2;i++){
			ghostFrames[i] = actors.findRegion("fantasma" + (i +1));
		}
		ghostAnimation = new Animation(WALKING_FRAME_DURATION, ghostFrames);
		
	}
	
	private void drawOscar(){
		if(oscar.isFacingLeft()){
			oscarFrame = oscarLeft;
		}
		else{
			oscarFrame = oscarTexture;
		}
		//Dibujar Animacion de Oscar si esta Caminando
		if(oscar.getState().equals(Oscar.States.WALKING)){
			oscarFrame = oscar.isFacingLeft() ? walkLeftAnimation.getKeyFrame(oscar.getStateTime(), true) : walkRightAnimation.getKeyFrame(oscar.getStateTime(),true);
		}
		batch.draw(oscarFrame, oscar.getPosition().x * ppuX , oscar.getPosition().y * ppuY , oscar.getWidth() * ppuX, oscar.getHeight() * ppuY);
	}
	
	private void drawGhosts(){
		for(Ghost ghost : stage.getFantasmas()){
			fantasmaFrame = ghostAnimation.getKeyFrame(ghost.getStateTime(), true);
			switch(ghost.getType()){
				case 1:
					batch.draw(fantasmaFrame, ghost.getPosition().x * ppuX , ghost.getPosition().y * ppuY , ghost.getWidth() * ppuX, ghost.getHeight() * ppuY);
					break;
				case 2:
					batch.draw(fantasmaFrame, ghost.getPosition().x * ppuX , ghost.getPosition().y * ppuY , ghost.getWidth() * ppuX, ghost.getHeight() * ppuY);
					break;
				default:
					break;
			}
		}
	}
}
