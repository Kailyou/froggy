package javagame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Boss extends movingObject {
	Image info;
	Image bossNormal;
	
	Sound explosionSound, bossDefeatedSound, victoySound;

	Animation boss, bossMoving, bossDieing;

	Vector2f startPositionSave; // für's zurücksetzen
	Vector2f endPositionSave;	// für's zurücksetzen
	Vector2f startPosition;
	Vector2f endPosition;

	static int ragelevel		= 0;	// static, da resetmethode
	static int bossDeathTimer	= 6500; // static, da resetmethode
	static int musicTimer		= 8000; // static, da resetmethode
		   int[] durationMoving = { 150, 150, 150 }; // static, da resetmethode
		   int[] durationDieing = { 400, 400, 400, 400, 400, 400, 400, 400, 400, 400, 
				   					400, 400, 400, 400 }; // static, da resetmethode

	
	static boolean bossDefeated 			= false; // static, da resetmethode
	static boolean isMoving					= true;  // static, da resetmethode
	static boolean infoShowed				= false; // static, da resetmethode
	static boolean raging					= false; // static, da resetmethode
	static boolean explosionSoundPlayed 	= false; // static, da resetmethode
	static boolean bossDefeatedSoundPlayed	= false; // static, da resetmethode
	static boolean victorySoundPlayed		= false; // static, da resetmethode
	
	Shape bossRectangle2;
	
	
	

	public Boss(String direction, Vector2f startPosition, Vector2f endPosition,
			float currentWayPoint, float speed) {
		super(direction, startPosition, endPosition, currentWayPoint, speed);

		mO_direction = direction;
		mO_startingPosition = startPosition;
		this.startPositionSave = startPosition.copy();
		mO_currentWorldPosition = mO_startingPosition.copy();
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.endPositionSave = endPosition.copy();
		mO_headingVector = endPosition.copy();
		mO_headingVector.sub(startPosition);
		mO_totalDistance = startPosition.distance(endPosition);
		mO_headingVector.normalise();
		mO_currentWayPoint = currentWayPoint;
		mO_speed = speed;
	}

	// GETTER
	@Override
	public Shape getBoundingShape() {
		return mO_boundingRectangle;
	}

	public Shape getBoundingShape2() {
		return bossRectangle2;
	}

	public boolean isMoving() {
		return isMoving;
	}

	// SETTER
	public static Vector2f setRandomVector() {
		return new Vector2f(myRandom(65, 542), myRandom(33, 402));
	}

	// OTHERS
	public static void resetBoss() { //static da in Menu
		ragelevel = 0;
		bossDeathTimer = 5000;
		musicTimer = 8000;
		bossDefeated = false;
		infoShowed = false;
		isMoving = true;
		raging = false;
		explosionSoundPlayed = false;
		bossDefeatedSoundPlayed = false;
		victorySoundPlayed = false;
	}

	// Random float
	public static float myRandom(float low, float high) {
		return (float) (Math.random() * (high - low) + low);
	}

	// boss death
	public void die(int delta) {
		boss = bossDieing;
		bossDefeated = true;
		if(!explosionSoundPlayed)
			explosionSound.loop();
	}

	public void Initialize() throws SlickException {
		info = new Image("data/Images/info.png");
		bossNormal = new Image("data/boss/bossMoving1.png");
		
		Image[] moving = { new Image("data/boss/bossMoving1.png"),
						   new Image("data/boss/bossMoving2.png"),
						   new Image("data/boss/bossMoving3.png") };
		
		Image[] dieing = { new Image("data/boss/bossDie1.png"),
				   new Image("data/boss/bossDie2.png"),
		           new Image("data/boss/bossDie3.png"),
				   new Image("data/boss/bossDie4.png"),
				   new Image("data/boss/bossDie5.png"),
				   new Image("data/boss/bossDie6.png"),
				   new Image("data/boss/bossDie7.png"),
				   new Image("data/boss/bossDie8.png"),
				   new Image("data/boss/bossDie9.png"),
				   new Image("data/boss/bossDie10.png"),
				   new Image("data/boss/bossDie11.png"),
				   new Image("data/boss/bossDie12.png"),
				   new Image("data/boss/bossDie13.png"),
				   new Image("data/boss/bossDie14.png") };

bossMoving = new Animation(moving, durationMoving, false);
bossDieing = new Animation(dieing, durationDieing, false);

boss = bossMoving;		
		
		explosionSound = new Sound("data/Sounds/Explosion6.wav");
		bossDefeatedSound = new Sound("data/Sounds/Boss Defeated.wav");
		victoySound = new Sound("data/Sounds/Victory.wav");

		// Boss Rectangle 1 (Upper one)
		mO_boundingRectangle = new Rectangle(mO_startingPosition.getX() + 5,
				mO_startingPosition.getY() + 45, boss.getWidth() - 15,
				boss.getHeight() - 135);
		
		// Boss Rectangle 2 (Lower one)
		bossRectangle2 = new Rectangle(mO_startingPosition.getX() + 50,
				mO_startingPosition.getY() + 105, boss.getWidth() - 100,
				boss.getHeight() - 150);
	}

	public void Update(int delta) {
		mO_currentWayPoint += mO_speed * delta / 1000.0f;

		Vector2f localDelta = mO_headingVector.copy();
		localDelta.scale(mO_currentWayPoint);
		mO_currentWorldPosition = mO_startingPosition.copy();
		mO_currentWorldPosition.add(localDelta);

		if (mO_currentWayPoint > mO_totalDistance) {
			startPosition = endPosition.copy();
			endPosition = setRandomVector();
			mO_currentWayPoint = 0.0f;

			mO_startingPosition = startPosition;
			mO_currentWorldPosition = mO_startingPosition.copy();
			mO_headingVector = endPosition.copy();
			mO_headingVector.sub(startPosition);
			mO_totalDistance = startPosition.distance(endPosition);
			mO_headingVector.normalise();
		}

		mO_boundingRectangle.setLocation(mO_currentWorldPosition.getX() + 5,
				mO_currentWorldPosition.getY() + 45);
		bossRectangle2.setLocation(mO_currentWorldPosition.getX() + 50,
				mO_currentWorldPosition.getY() + 105);

		if (ragelevel == 1)
			mO_speed = 150;

		if (ragelevel == 2)
			mO_speed = 200;

		if (ragelevel == 3)
			mO_speed = 250;

		if (ragelevel == 4)
			mO_speed = 300;

		if (ragelevel == 5)
			mO_speed = 350;

		if (ragelevel == 6)
			mO_speed = 400;

		if (ragelevel == 7) {
			mO_speed = 450;
			raging = true;
			isMoving = false;
		}

		if (ragelevel == 8)
			mO_speed = 500;

		if (ragelevel == 9)
			mO_speed = 550;

		if (ragelevel == 10) {
			mO_speed = 0;
			bossNormal.setRotation(0);
			raging = false;
			isMoving = true;
			Player.froggyOver9000 = true;
		}

		if (raging)
			bossNormal.rotate(delta * 2);

		// animation if boss alive
		if (!bossDefeated) {
			boss.update(delta);
		} else {
			boss.update(delta);
			boss.stopAt(13);
			bossDeathTimer -= delta;
		}
		
		if(bossDeathTimer <= 0){
			Level_2.levelCompleted = true;
		}
		
		// sound when game won
		if(bossDeathTimer <= 0)
		{
			musicTimer -= delta;
			
			// boss defeated sound
			if(!bossDefeatedSoundPlayed)
			{
				explosionSound.stop();
				bossDefeatedSound.play();
				bossDefeatedSoundPlayed = true;
			}
			
			// victory sound
			if(musicTimer <= 0 && !victorySoundPlayed)
			{
				victoySound.play();
				victorySoundPlayed = true;
			}
		}
	}

	@Override
	public void Render(Graphics g) {
		if (!infoShowed) {
			info.draw(250, 160);
			
			g.drawString("Mantis Ant", 350, 200);
			g.drawString("It seems that this mon-", 290, 250);
			g.drawString("ster is way too strong", 290, 270);
			g.drawString("for our little frog.", 290, 290);

			g.drawString("But may we can defeat it", 290, 340);
			g.drawString("by collecting up the", 290, 360);
			g.drawString("dynamite?", 290, 380);

			g.drawString("Let us try it!", 290, 430);

			g.drawString("Press space to continue!", 292, 530);
		}
		if (isMoving && bossDeathTimer > 0)
			boss.draw(mO_currentWorldPosition.getX(),
					mO_currentWorldPosition.getY());
		else if (raging)
			bossNormal.draw(mO_currentWorldPosition.getX(),
					mO_currentWorldPosition.getY());
	}
}
