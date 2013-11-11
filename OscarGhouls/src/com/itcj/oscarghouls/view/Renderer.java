package com.itcj.oscarghouls.view;

import java.util.ArrayList;

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
import com.itcj.oscarghouls.model.Item;
import com.itcj.oscarghouls.model.Item.Type;
import com.itcj.oscarghouls.model.NetBullet;
import com.itcj.oscarghouls.model.Oscar;
import com.itcj.oscarghouls.model.OscarArm;
import com.itcj.oscarghouls.model.Stage;


public class Renderer {
	
	public static final long SECOND = 1000000000;		//Frecuencia en nanosegundos con la cual estaran apareciendo los fantasmas(1 seg)
	public static final float WALKING_FRAME_DURATION = .08f;   //Tiempo que durara cada Keyframe de la animacion
	//Actores y Stages
	Stage stage;
	Oscar oscar;
	OscarArm arm;
	//Atlas
	TextureAtlas screens = new TextureAtlas(Gdx.files.internal("screens.pack"));
	TextureAtlas actors = new TextureAtlas(Gdx.files.internal("actors.pack"));
	//Textures
	TextureRegion oscarTexture = new TextureRegion();
	TextureRegion oscarLeft;
	TextureRegion oscarArmTexture = new TextureRegion();
	TextureRegion oscarArmLeft;
	TextureRegion gunTexture = new TextureRegion();
	TextureRegion gunLeftTexture;
	TextureRegion netBulletTexture = new TextureRegion();
	TextureRegion netBulletLeftTexture;
	TextureRegion netBulletFrame;
	TextureRegion fondoTexture = new TextureRegion();
	TextureRegion fantasma1 = new TextureRegion();
	TextureRegion fantasma2 = new TextureRegion();
	TextureRegion oscarFrame = new TextureRegion();
	TextureRegion fantasmaFrame = new TextureRegion();
	//Textura de Botones
	TextureRegion buttonLeft = new TextureRegion();
	TextureRegion buttonRight = new TextureRegion();
	TextureRegion action = new TextureRegion();
	TextureRegion action2 = new TextureRegion();
	TextureRegion lifeKit = new TextureRegion();
	TextureRegion ammoKit = new TextureRegion();
	TextureRegion scout = new TextureRegion();
	TextureRegion itemFrame = new TextureRegion();
	TextureRegion by = new TextureRegion();
	
	//Texturas de Stamina
	TextureRegion[] texStamina = new TextureRegion[6];
	//Animations
	private Animation walkRightAnimation;
	private Animation walkLeftAnimation;
	private Animation armLeftAnimation;
	private Animation armRightAnimation;
	private Animation ghostAnimation;
	
	SpriteBatch batch;
	OrthographicCamera cam;
	
	long lastGhost;					//Guarda el tiempo en que se envio el ultimo fantasma
	long itemsTime;					//Maneja el segundero de los items
	
	int viewWidth;
	int viewHeight;
	float ppuX;
	float ppuY;
	private TextureRegion oscarArmFrame;
	
	boolean isGameOver;
	
	

	public Renderer(Stage stage){
		this.stage = stage;
		oscar = stage.getOscar();
		arm = stage.getArm();
		cam = new OrthographicCamera(OscarGhouls.CAMERA_WIDTH, OscarGhouls.CAMERA_HEIGHT);
		cam.position.set(OscarGhouls.CAMERA_WIDTH/2, OscarGhouls.CAMERA_HEIGHT/2, 0);
		batch = new SpriteBatch();
		loadTextures();
		lastGhost = TimeUtils.nanoTime();
		itemsTime = TimeUtils.nanoTime();
		isGameOver = false;
	}
	
	public void render(float delta){
		
		if(TimeUtils.nanoTime() - lastGhost > SECOND * 4){
			stage.sendGhost();
			lastGhost = TimeUtils.nanoTime();
			
		}
		if(TimeUtils.nanoTime() - itemsTime > SECOND){
			stage.checkItemDuration();
			itemsTime = TimeUtils.nanoTime();
		}
		
		batch.begin();
		//Dibujar el Fondo
		batch.draw(fondoTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//Dibujar a Oscar
		drawOscar();
		drawOscarArm();
		//Dibujar Fantasmas
		drawGhosts();
		//Dibujar Stamina}
		drawStamina();
		//Dibujar netBullets
		drawNetBullets();
		drawItems();
		//Dibujar Botones
		drawButtons();
		//Dibujar Score
		if(stage.getScore() != 0){
			drawNumber(stage.getScore(), 8,5,.5f,.5f);
		}
		
		//Dibujar Info de scouts Rescatados
		batch.draw(scout, 4f * ppuX , 5 * ppuY , .5f * ppuX, .5f * ppuY);
		batch.draw(by, 4.6f * ppuX , 5 * ppuY , .3f * ppuX, .3f * ppuY);
		drawNumber(stage.getSavedScouts(), 5, 5, .3f,.3f);
		//Dibujar Info de Ammo
		batch.draw(ammoKit, 5.5f * ppuX , 5 * ppuY , .5f * ppuX, .5f * ppuY);
		batch.draw(by, 6f * ppuX , 5 * ppuY , .3f * ppuX, .3f * ppuY);
		drawNumber(stage.getAmmo(), 6.3f, 5, .3f,.3f);
		
		batch.end();
		//Actualizar el Delta(posicion en el tiempo
		stage.moveOscar(delta);
		stage.moveArm(delta);
		//Actualizamos los fantasmas
		stage.moveGhosts(delta);
		stage.moveNetBullets(delta);
		stage.checkItems();
		if(oscar.getStamina() <= 0){
			isGameOver = true;
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
		//Carga de texturas de los botones
		buttonLeft = actors.findRegion("arrow");
		buttonRight = new TextureRegion(buttonLeft);
		buttonRight.flip(true, false);
		action = actors.findRegion("ghostbutton");
		action2 = actors.findRegion("gunbutton");
		
		//Carga de Texturas y animacion de Oscar
		oscarTexture = actors.findRegion("oscar1");
		oscarLeft = new TextureRegion(oscarTexture);
		oscarLeft.flip(true, false);
		
		lifeKit = actors.findRegion("lifekit");
		ammoKit = actors.findRegion("balas");
		scout = actors.findRegion("scout");
		by = actors.findRegion("x");
		
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
		//Carga de texturas y animacion del brazo de Oscar
		oscarArmTexture = actors.findRegion("net1");
		oscarArmLeft = new TextureRegion(oscarArmTexture);
		oscarArmLeft.flip(true, false);
		
		gunTexture = actors.findRegion("gun");
		gunLeftTexture = new TextureRegion(gunTexture);
		gunLeftTexture.flip(true, false);
		
		netBulletTexture = actors.findRegion("web");
		netBulletLeftTexture = new TextureRegion(netBulletTexture);
		netBulletLeftTexture.flip(true, false);
		
		TextureRegion[] armRightFrames = new TextureRegion[3];
		for(int i=0;i<3;i++){
			armRightFrames[i] = actors.findRegion("net" + (i +1));
		}
		armRightAnimation = new Animation(WALKING_FRAME_DURATION, armRightFrames);
		
		TextureRegion[] armLeftFrames = new TextureRegion[3];
		for(int i=0;i<3;i++){
			armLeftFrames[i] = new TextureRegion(armRightFrames[i]);
			armLeftFrames[i].flip(true, false);
		}
		armLeftAnimation = new Animation(WALKING_FRAME_DURATION, armLeftFrames);
		
		// Carga de Texturas y animacion de los fantasmas
		fantasma1 = actors.findRegion("fantasma");
		fantasma2 = actors.findRegion("fantasma");
		
		TextureRegion[] ghostFrames = new TextureRegion[2];
		for(int i=0;i<2;i++){
			ghostFrames[i] = actors.findRegion("fantasma" + (i +1));
		}
		ghostAnimation = new Animation(WALKING_FRAME_DURATION, ghostFrames);
		
		//Carga de Texturas de la stamina
		for(int i= 0; i<=5;i++){
			texStamina[i] = actors.findRegion("stamina" + i);
		}
		
		
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
	
	private void drawNetBullets(){
		for(NetBullet netBullet : stage.getNetBullets()){
				netBulletFrame = oscar.isFacingLeft() ? netBulletLeftTexture : netBulletTexture;
				batch.draw(netBulletFrame,netBullet.getPosition().x * ppuX , netBullet.getPosition().y * ppuY , netBullet.getWidth() * ppuX, netBullet.getHeight() * ppuY);
		}
	}
	
	private void drawOscarArm(){
		if(oscar.isFacingLeft()){
			if(arm.getPreviousState().equals(OscarArm.States.SHOOTING)){
				oscarArmFrame = gunLeftTexture;
			}
			else{
				oscarArmFrame = oscarArmLeft;
			}
		}
		else{
			if(arm.getPreviousState().equals(OscarArm.States.SHOOTING)){
				oscarArmFrame = gunTexture;
			}
			else{
				oscarArmFrame = oscarArmTexture;
			}
				
		}
		
		//Dibujar Animacion de Oscar si esta Caminando
		if(arm.getState().equals(OscarArm.States.CATCHING)){
			oscarArmFrame = oscar.isFacingLeft() ? armLeftAnimation.getKeyFrame(arm.getStateTime(), true) : armRightAnimation.getKeyFrame(arm.getStateTime(),true);
		}
		//Dibujar si esta disparando
		if(arm.getState().equals(OscarArm.States.SHOOTING)){
			oscarArmFrame = oscar.isFacingLeft() ? gunLeftTexture : gunTexture;
		}
		batch.draw(oscarArmFrame, arm.getPosition().x * ppuX , arm.getPosition().y * ppuY , arm.getWidth() * ppuX, arm.getHeight() * ppuY);
	}
	
	private void drawButtons(){
		//Dibujar Left
		batch.draw(buttonLeft, stage.getButtonLeft().getPosition().x * ppuX , stage.getButtonLeft().getPosition().y * ppuY , stage.getButtonLeft().getWidth() * ppuX, stage.getButtonLeft().getHeight() * ppuY);
		//Dibujar Right
		batch.draw(buttonRight, stage.getButtonRight().getPosition().x * ppuX , stage.getButtonRight().getPosition().y * ppuY , stage.getButtonRight().getWidth() * ppuX, stage.getButtonRight().getHeight() * ppuY);
		//Dibujar Actions
		batch.draw(action, stage.getAction1().getPosition().x * ppuX , stage.getAction1().getPosition().y * ppuY , stage.getAction1().getWidth() * ppuX, stage.getAction1().getHeight() * ppuY);
		batch.draw(action2, stage.getAction2().getPosition().x * ppuX , stage.getAction2().getPosition().y * ppuY , stage.getAction2().getWidth() * ppuX, stage.getAction2().getHeight() * ppuY);
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
	
	private void drawStamina(){
		batch.draw(texStamina[oscar.getStamina()], stage.getStamina().getPosition().x * ppuX , stage.getStamina().getPosition().y * ppuY , stage.getStamina().getWidth() * ppuX, stage.getStamina().getHeight() * ppuY);
	}
	
	private void drawItems(){
		for(Item item : stage.getItems()){
			switch(item.getType()){
				case LIFEKIT:
					itemFrame = lifeKit;
					break;
				case AMMO:
					itemFrame = ammoKit;
					break;
				case SCOUT:
					itemFrame = scout;
					break;
				default:
					break;
			}
			batch.draw(itemFrame, item.getPosition().x * ppuX , item.getPosition().y * ppuY , item.getWidth() * ppuX, item.getHeight() * ppuY);
		}
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	
	protected void  drawNumber(long number, float posX, float posY, float w, float h) {
		String stringPuntuacion = String.valueOf(number);	//Convierto en String la puntuacion total
		TextureRegion texture;
		ArrayList<TextureRegion> textures = new ArrayList<TextureRegion>();
		
		for(int x = 0; x < stringPuntuacion.length(); x++){
			char numero = stringPuntuacion.charAt(x); //Paso por cada char del string de la puntacion
			String sNumero = Character.toString(numero);//Lo convierto a string cada char individual
			texture = actors.findRegion(sNumero);// Uso ese string para tomar su texture Region	
			textures.add(x, texture);	// Los agrego en un array de texturesRegion
		}		
		for(TextureRegion textureRegion : textures){
			batch.draw(textureRegion, posX * ppuX, posY * ppuY, w * ppuX, h * ppuY); //Imprimo cada Texture region del array
			posX = posX + w;
		}
	}
	
	protected void  drawNumber(int number, float posX, float posY, float w, float h) {
		String stringPuntuacion = String.valueOf(number);	//Convierto en String la puntuacion total
		TextureRegion texture;
		ArrayList<TextureRegion> textures = new ArrayList<TextureRegion>();
		
		for(int x = 0; x < stringPuntuacion.length(); x++){
			char numero = stringPuntuacion.charAt(x); //Paso por cada char del string de la puntacion
			String sNumero = Character.toString(numero);//Lo convierto a string cada char individual
			texture = actors.findRegion(sNumero);// Uso ese string para tomar su texture Region	
			textures.add(x, texture);	// Los agrego en un array de texturesRegion
		}		
		for(TextureRegion textureRegion : textures){
			batch.draw(textureRegion, posX * ppuX, posY * ppuY, w * ppuX, h * ppuY); //Imprimo cada Texture region del array
			posX = posX + w;
		}
	}
	
}
