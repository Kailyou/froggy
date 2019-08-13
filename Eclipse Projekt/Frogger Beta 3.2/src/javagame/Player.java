package javagame;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

public class Player {
	Sound jumpSound, deathSound;

	final float froggyStartX;
	final float froggyStartY;
	      float froggyPositionX;
	      float froggyPositionY;
	      float speed;
	      
	Animation froggy, movingUp, movingDown, movingLeft, movingRight, froggyDeath;

	int[] duration = { 150, 150 }; // how long is animation, image to left

	static boolean froggyOver9000 = false; //static, da in methode in bossklasse verwendet
	static boolean gameOver = false; //static, da in einer methode zum resetten in menuklasse
	static boolean froggyAlive = true;
		   boolean froggyCollision = false;
		   boolean froggyOnTree = false;
    
	
	Rectangle froggyRectangle;

	public Player(float froggyStartX, float froggyStartY, float speed) {
		this.froggyStartX = froggyStartX;
		this.froggyStartY = froggyStartY;
		this.froggyPositionX = froggyStartX;
		this.froggyPositionY = froggyStartY;
		this.speed = speed;
	}

	// GETTER
//	public float getX() {
//		return froggyPositionX;
//	}
//
//	public float getY() {
//		return froggyPositionY;
//	}

	public Rectangle getBoundingRectangle() {
		return froggyRectangle;
	}

	public boolean isAlive() {
		return froggyAlive;
	}

	// SETTER
	public void setX(float froggyPositionX) {
		this.froggyPositionX = froggyPositionX;
	}

	public void setY(float froggyPositionY) {
		this.froggyPositionY = froggyPositionY;
	}

	public void setStartPosition() {
		froggyPositionX = froggyStartX;
		froggyPositionY = froggyStartY;
	}

	
	// OTHERS
	public void die() {
		froggyAlive = false;
		froggy = froggyDeath;
		deathSound.play();
	}

	// resets froggy (restart game)
	public void resetFroggyIngame() {
		froggyAlive = true;
		setStartPosition();
		froggy = movingUp;
	}
	
	public static void resetFroggyGamerover() {
		froggyOver9000 = false;
		gameOver = false;
		froggyAlive = true;
	}

	public void init() throws SlickException {
		Image[] walkUp = { new Image("data/animation/froggyUpNormal.png"),
				new Image("data/animation/froggyUpJump.png") };
		Image[] walkDown = { new Image("data/animation/froggyDownNormal.png"),
				new Image("data/animation/froggyDownJump.png") };
		Image[] walkLeft = { new Image("data/animation/froggyLeftNormal.png"),
				new Image("data/animation/froggyLeftJump.png") };
		Image[] walkRight = {
				new Image("data/animation/froggyRightNormal.png"),
				new Image("data/animation/froggyRightJump.png") };
		Image[] froggyDown = { new Image("data/animation/froggyDeath.png"),
				new Image("data/animation/froggyDeath.png") };

		movingUp = new Animation(walkUp, duration, false);
		movingDown = new Animation(walkDown, duration, false);
		movingLeft = new Animation(walkLeft, duration, false);
		movingRight = new Animation(walkRight, duration, false);
		froggyDeath = new Animation(froggyDown, duration, false);

		froggy = movingUp;

		jumpSound = new Sound("data/sounds/Frogger_Jump.wav");
		deathSound = new Sound("data/sounds/Froggy Death.wav");

		froggyRectangle = new Rectangle(froggyPositionX + 5,
										froggyPositionY + 5, 
										froggy.getWidth() - 10,
										froggy.getHeight() - 10);
	}		

	public void update(int delta, Input input) {
		// PLAYER ALIVE
		if (froggyAlive) {
			
			// INPUT KEYBOARD
			// up
			if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
				froggy = movingUp;
				froggy.update(delta);
				froggyPositionY -= speed * delta / 1000.0f;

				if (jumpSound.playing() == false) {
					jumpSound.play(1, 0.2f);
					
				}
			}
			
			// down
			else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
				froggy = movingDown;
				froggy.update(delta);
				froggyPositionY += speed * delta / 1000.0f;

				if (jumpSound.playing() == false) {
					jumpSound.play(1, 0.2f);
				}
			}
						
			// left
			else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
				froggy = movingLeft;
				froggy.update(delta);
				froggyPositionX -= speed * delta / 1000.0f;

				if (jumpSound.playing() == false) {
					jumpSound.play(1, 0.2f);
				}
			}
			
			// right
			else if (input.isKeyDown(Input.KEY_RIGHT)
					|| input.isKeyDown(Input.KEY_D)) {
				froggy = movingRight;
				froggy.update(delta);
				froggyPositionX += speed * delta / 1000.0f;

				if (!jumpSound.playing()) {
					jumpSound.play(1, 0.2f);
				}
			}
			else
				froggy.setCurrentFrame(0);
			
			froggyRectangle.setLocation(froggyPositionX + 5, froggyPositionY + 5);
		} // PLAYER ALIVE END

		// PLAYER DEATH
		else {
				froggyAlive = true;
			 } // PLAYER DEATH END
	}

	public void render() {
		froggy.draw(froggyPositionX, froggyPositionY);
	}
}