package javagame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level_2 extends BasicGameState {

	Player froggy;

	Boss boss;

	Image map;
	Image dynamit;
	Image info;

	Music gameSound;

	Image option;

	Shape[] dynamitShape = new Rectangle[dynamitCount];

	final static int dynamitCount 	    = 10; 		// static, da resetmethode
		  static int dynamiteCollected	= 0; 		// static, da resetmethode
		  static int timer 				= 350000;	// static, da resetmethode
		  static int timerDeath 		= 1500; 	// static, da resetmethode
		  static int lifeCounter		= 3; 		// static, da resetmethode
		  
	float[] randomX = new float[dynamitCount];
	float[] randomY = new float[dynamitCount];

	static boolean[] dynamitTaken = new boolean[dynamitCount]; // static, da resetmethode
	static boolean quit			  = false; 					   // static, da resetmethode
	static boolean levelCompleted = false; 					   // static, da resetmethode
	static boolean infoShowed 	  = false;					   // static, da resetmethode

	

	public Level_2(int level2) throws SlickException {
	}

	// OTHERS
	public static void restartLevel2() {
		dynamiteCollected = 0;
		timer = 350000;
		timerDeath = 1500;
		lifeCounter = 3;

		quit = false;
		levelCompleted = false;
		infoShowed 	  = false;

		for (int i = 0; i < dynamitCount; i++) {
			dynamitTaken[i] = false;
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		map = new Image("data/Maps/ForestMap.png");
		option = new Image("data/Images/options/Frogger_Options.png");
		dynamit = new Image("data/Images/collision objects/dynamit.png");
		info = new Image("data/Images/info.png");
		
		gameSound = new Music("data/sounds/bossTheme.wav");

		froggy = new Player(384, 608, 100);
		froggy.init();

		boss = new Boss("boss op", new Vector2f(303, 10),
								   new Vector2f(Boss.setRandomVector()),
								   			    0f, 100f);
		boss.Initialize();

		// initializing dynamitbooleans
		for (int i = 0; i < dynamitCount; i++) {
			dynamitTaken[i] = false;
		}

		// initializing random x's
		for (int i = 0; i < dynamitCount; i++) {
			randomX[i] = Boss.myRandom(65, 542);
		}

		// initializing random y's
		for (int i = 0; i < dynamitCount; i++) {
			randomY[i] = Boss.myRandom(33, 402);
		}

		// initializing random shapes
		for (int i = 0; i < dynamitCount; i++) {
			dynamitShape[i] = new Rectangle(randomX[i], randomY[i], 31f, 24f);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		Input input = gc.getInput();

		// infoMessage
		if (!Boss.infoShowed) {
			if (input.isKeyDown(Input.KEY_SPACE)) {
				Boss.infoShowed = true;
			}
		}

		// gameOver check
		if (lifeCounter != 0 && timer > 0) {
			Player.gameOver = false;
		} else {
			lifeCounter = 0;
			Player.gameOver = true;
		}

		// NOT MENU
		if (!quit) {
			// timer
			if (timer >= 0 && Boss.infoShowed) {
				timer -= delta;

				// gamesound playing (endless)
				if(!Boss.bossDefeated)
				if(!gameSound.playing()) {
					gameSound.play();
				}
			}

			// BOSS UPDATE
			if (Boss.infoShowed) {
				boss.Update(delta);
			}

			// ALLIVE, !GAMEOVER, !LEVELCOMPLETED
			if (froggy.isAlive() && !Player.gameOver && !levelCompleted
					&& Boss.infoShowed) {
				// froggy animation
				froggy.update(delta, input);

				// INPUT
				// open menu
				if (input.isKeyDown(Input.KEY_ESCAPE) && !Player.gameOver) {
					quit = true;
				}

				// COLLISION
				// froggy with wall
				// screen up
				if (froggy.froggyPositionY < 35)
					froggy.froggyPositionY = 35;
				
				// screen down
				if (froggy.froggyPositionY > 613)
					froggy.froggyPositionY = 613;
				
				if ((froggy.froggyPositionX > 0) && (froggy.froggyPositionX < 345))
					if (froggy.froggyPositionY > 583) 
						froggy.froggyPositionY = 583;
					

				if ((froggy.froggyPositionX > 423) && (froggy.froggyPositionX < 800)) 
					if (froggy.froggyPositionY > 583) 
						froggy.froggyPositionY = 583;
									
				// screen left
				if (froggy.froggyPositionX < 60) 
					froggy.froggyPositionX = 60.0f;
				
				if (froggy.froggyPositionY > 590)
					if (froggy.froggyPositionX < 348) 
						froggy.froggyPositionX = 348;
								
				// screen right
				if (froggy.froggyPositionX > 710) {
					froggy.froggyPositionX = 710;
				}

				if (froggy.froggyPositionY > 590)
					if (froggy.froggyPositionX > 420)
						froggy.froggyPositionX = 420;
							
				
				// collission detection: froggy with boss
				if ((froggy.getBoundingRectangle().intersects(
						boss.getBoundingShape()) || (froggy
						.getBoundingRectangle().intersects(boss
						.getBoundingShape2())))
						&& !Boss.bossDefeated) {
					if (Player.froggyOver9000) {
						boss.die(delta);
						gameSound.stop();
					} else {
						froggy.die();
						lifeCounter--;
					}
				}

				// wenn frosch auf dynamittrifft, und dieses noch nicht
				// gesammelt wurde, ragecounter +1, da counter sonst permanent
				// wächst wegen insert!
				for (int i = 0; i < dynamitCount; i++) {
					if ((froggy.getBoundingRectangle().intersects(
							dynamitShape[i]) && (!dynamitTaken[i]))) {
						froggy.speed += 15;
						Boss.ragelevel++;
						dynamiteCollected++;
						dynamitTaken[i] = true;
					}
				}

			}

			// PLAYER DEATH
			else if (!froggy.isAlive()) {
				timerDeath -= delta;

				if (timerDeath <= 0 && !Player.gameOver) {
					froggy.resetFroggyIngame();
					timerDeath = 1500;
				}

			} // PLAYER DEATH END
		} // NOT MENU END

		// MENU UP
		if (quit) {
			if (input.isKeyDown(Input.KEY_R)) {
				quit = false;
			}

			if (input.isKeyDown(Input.KEY_M)) {
				sbg.enterState(1);
				quit = false;
			}

			if (input.isKeyDown(Input.KEY_Q)) {
				System.exit(0);
			}
		} // MENU UP END

		// GAMEOVER
		if (Player.gameOver) {
			if (input.isKeyDown(Input.KEY_SPACE)) {
				// restarts the game
				Game.resetGame(gc, sbg);
			}
		} // GAMEOVER END
		
		// LEVEL COMPLETED
		if (!infoShowed && Boss.bossDeathTimer <= 0) {
			if (input.isKeyDown(Input.KEY_SPACE)) {
				// restarts the game
				Game.resetGame(gc, sbg);
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		map.draw(0, 0);

		for (int i = 0; i < dynamitCount; i++) {
			if (!dynamitTaken[i]) {
				dynamit.draw(randomX[i], randomY[i]);
			}
		}

		if (!Player.gameOver || timerDeath > 0) {
			froggy.render();
		}

		boss.Render(g);

		// LIFE IMAGE DRAW
		switch (lifeCounter) {
		case 3:
			g.drawImage(new Image("data/animation/froggyUpNormal.png"), 1, 635);
			g.drawImage(new Image("data/animation/froggyUpNormal.png"), 33, 635);
			g.drawImage(new Image("data/animation/froggyUpNormal.png"), 65, 635);
			break;
		case 2:
			g.drawImage(new Image("data/animation/froggyUpNormal.png"), 1, 635);
			g.drawImage(new Image("data/animation/froggyUpNormal.png"), 33, 635);
			break;

		case 1:
			g.drawImage(new Image("data/animation/froggyUpNormal.png"), 1, 635);
			break;
		default:
			g.drawString(" ", 5, 640);
		}

		// DYNAMITE POWER DRAW
		if (!Player.gameOver && timerDeath != 0) {
			switch (dynamiteCollected) {
			case 1:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x1", 235, 650);
				break;
			case 2:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x2", 235, 650);
				break;
			case 3:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x3", 235, 650);
				break;
			case 4:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x4", 235, 650);
				break;
			case 5:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x5", 235, 650);
				break;
			case 6:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x6", 235, 650);
				break;
			case 7:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x7", 235, 650);
				break;
			case 8:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x8", 235, 650);
				break;
			case 9:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x9", 235, 650);
				break;
			case 10:
				g.drawImage(dynamit, 200, 650);
				g.drawString("x10", 250, 650);
				g.drawString("maximum power! - Finish him!", 310, 650);
				break;
			default:
				g.drawString("collect the dynamite for maximum power!", 245, 650);
			}
		}

		// OTHERS
		// timer drawing
		if (!Player.gameOver) {
			g.drawString("Time left : " + timer / 1000, 660, 660);
		}

		// level drawing
		g.drawString("Level 2 - Forest", 5, 660);

		// game over drawing
		if (Player.gameOver && timerDeath <= 0) {
			g.drawString("GAME OVER", 350, 310);
			g.drawString("press space for restarting!", 280, 660);
		}

		// option drawing
		if (quit && !Player.gameOver) {
			option.draw(290, 250);
		}
		
		// level completed drawing
		if (!infoShowed && Boss.bossDeathTimer <= 0) {
			info.draw(250, 160);
			
			g.drawString("YOU WON", 360, 200);
			g.drawString("You made it through the", 290, 250);
			g.drawString("the street and even", 290, 270);
			g.drawString("survived the mantis ant.", 290, 290);

			g.drawString("You, my friend,", 290, 340);
			g.drawString("are ready to call", 290, 360);
			g.drawString("yourself a frogmaster.", 290, 380);

			g.drawString("From now on you will", 290, 430);
			g.drawString("be known as froggy god.", 290, 450);

			g.drawString("Press space to play again!", 285, 530);
		}
	}

	@Override
	public int getID() {
		return 3;
	}
}
