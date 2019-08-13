package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.*;
import org.newdawn.slick.Input;

public class Level_1 extends BasicGameState {

	Player froggy;

	Image map;
	Image option;
	Image finishLine;

	Music gamesound;
	
	Shape finishLineRectangle;

	static int timer 			= 121000;   // static, da resetmethode
	static int lifeCounter 		= 3; 		// static, da resetmethode
	static int completeTimer	= 1500;     // static, da resetmethode
	static int timerDeath 		= 1500;     // static, da resetmethode

	static Car[] cars = new Car[16]; 		// static, da resetmethode
	static Tree[] trees = new Tree[6]; 		// static, da resetmethode

	static boolean levelCompleted	= false; // static, da resetmethode
	       boolean quit				= false;

	public Level_1(int state) {
	}

	
	// OTHERS
	public static void restartLevel1() {
		timer = 121000;
		lifeCounter = 3;
		completeTimer = 1500;
		timerDeath = 1500;
		levelCompleted = false;
		
		for (int i = 0; i < cars.length; i++) {
			cars[i].resetCarGameover();
		}

		for (int i = 0; i < trees.length; i++) {
			trees[i].resetTreeGameover();
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		map = new Image("data/Maps/StreetMap.png");
		option = new Image("data/Images/options/Frogger_Options.png");

		gamesound = new Music("data/sounds/Frogger_Gamesound.wav");

		froggy = new Player(384, 608, 115);
		froggy.init();

		finishLine = new Image("data/Images/collision objects/finishLine.png");

		// MOVING OBJECTS
		// CARS
		// cars on the lower street, from left to right
		cars[0] = new Car("Left_1", new Vector2f(-80, 516),
								    new Vector2f(880,516), 
								    			0.0f, 110);

		cars[1] = new Car("Left_1", new Vector2f(-80, 516), 
									new Vector2f(880, 516), 
											     240.0f, 110);

		cars[2] = new Car("Left_1", new Vector2f(-80, 516),
									new Vector2f(880, 516),
												 480.0f, 110);

		cars[3] = new Car("Left_1", new Vector2f(-80, 516),
									new Vector2f(880, 516),
												 720.0f, 110);

		// cars on the lower street, from right to left
		cars[4] = new Car("Right_1", new Vector2f(880, 466),
									 new Vector2f(-80, 466), 
									 			   0.0f, 110);

		cars[5] = new Car("Right_1", new Vector2f(880, 466),
									 new Vector2f(-80, 466),
									 			  240.0f, 110);

		cars[6] = new Car("Right_1", new Vector2f(880, 466),
									 new Vector2f(-80, 466),
									 			  480.0f, 110);

		cars[7] = new Car("Right_1", new Vector2f(880, 466),
									 new Vector2f(-80, 466), 
									 			  720.0f, 110);

		// cars on the upper street, from left to right
		cars[8] = new Car("Left_2", new Vector2f(-120, 325),
									new Vector2f(920, 325),
												 0.0f, 110);

		cars[9] = new Car("Left_2", new Vector2f(-120, 325),
									new Vector2f(920, 325),
												260.0f, 110);

		cars[10] = new Car("Left_2", new Vector2f(-120, 325),
									 new Vector2f(920, 325), 
									 			  520.0f, 110);

		cars[11] = new Car("Left_2", new Vector2f(-120, 325),
									 new Vector2f(920, 325),
									 			  780.0f, 110);

		// cars on the upper street, from right to left
		cars[12] = new Car("Right_2", new Vector2f(920, 275),
									  new Vector2f(-120, 275),
									  			   0.0f, 130);

		cars[13] = new Car("Right_2", new Vector2f(920, 275), 
									  new Vector2f(-120, 275), 
									  			   260.0f, 130);

		cars[14] = new Car("Right_2", new Vector2f(920, 275),
									  new Vector2f(-120, 275),
									  			   520.0f, 130);

		cars[15] = new Car("Right_2", new Vector2f(920, 275),
									  new Vector2f(-120, 275), 
									  			   780.0f, 130);

		for (int i = 0; i < cars.length; i++) {
			cars[i].Initialize();
		}

		// tree's where froggy is able to jump on
		trees[0] = new Tree("LINE I - tree 1", new Vector2f(-137, 150), 
											   new Vector2f(937, 150),
											    			0.0f, 130.0f);

		trees[1] = new Tree("LINE I - tree 2", new Vector2f(-137, 150), 
											   new Vector2f(937, 150), 
															537.0f, 130.0f);

		trees[2] = new Tree("LINE II - tree 1", new Vector2f(937, 113),
												new Vector2f(-137, 113),
										                 	 0.0f, 130.0f);

		trees[3] = new Tree("LINE II - tree 2", new Vector2f(937, 113), 
												new Vector2f(-137, 113),
														     537.0f, 130.0f);

		trees[4] = new Tree("LINE III - tree 1", new Vector2f(-137, 76),
												 new Vector2f(937, 76),
												 		      0.0f, 130.0f);

		trees[5] = new Tree("LINE III - tree 1", new Vector2f(-137, 76),
												 new Vector2f(937, 76),
												 	          537.0f, 130.0f);

		trees[0].Initialize1();
		trees[1].Initialize1();
		trees[2].Initialize2();
		trees[3].Initialize2();
		trees[4].Initialize1();
		trees[5].Initialize1();
		
		trees[2].mO_boundingRectangle.setY(100);
		
		// Rectangles for the collision
		finishLineRectangle = new Rectangle(360, 0, 80, 10); // Finish line level 1
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Menu.menuActive = false;
				
		Input input = gc.getInput();

		// gameOver check
		if (lifeCounter != 0 && timer > 0) {
			Player.gameOver = false;
		} else {
			lifeCounter = 0;
			Player.gameOver = true;
		}

		// gamesound playing (endless)
		if (!gamesound.playing()) {
			gamesound.play();
		}

		// NOT MENU
		if (!quit) {
			// timer
			if (timer >= 0) {
				timer -= delta;
			}

			// car objects update
			for (int i = 0; i < cars.length; i++) {
				cars[i].Update(delta);
			}

			// tree object update
			trees[0].Update1(delta);
			trees[1].Update1(delta);
			trees[2].Update2(delta);
			trees[3].Update2(delta);
			trees[4].Update1(delta);
			trees[5].Update1(delta);
			

			// ALLIVE, !GAMEOVER, !LEVELCOMPLETED
			if (froggy.isAlive() && !Player.gameOver && !levelCompleted) {
				// Animation
				froggy.update(delta, input);

				// INPUT
				// open menu
				if (input.isKeyDown(Input.KEY_ESCAPE) && !Player.gameOver) {
					quit = true;
				}

				// COLLISIONS
				// froggy with wall
				// screen up
				if (froggy.froggyPositionY < 35 &&
					froggy.froggyPositionX <= 352 ||
					froggy.froggyPositionY < 35 &&
					froggy.froggyPositionX >= 427)
					froggy.froggyPositionY = 35;
				
				// screen down
				if (froggy.froggyPositionY > 613)
					froggy.froggyPositionY = 613;
				

				//  screen left
				if (froggy.froggyPositionX < -3.4)
					froggy.froggyPositionX = (float) -3.4;
				
				// screen right
				if (froggy.froggyPositionX > 770.19)
					froggy.froggyPositionX = (float) 770.19;
				
				
				// froggy with car array
				for (Car carN : cars) {
					if (carN != null
							&& froggy.getBoundingRectangle().intersects(
									carN.getBoundingShape())) {
						froggy.die();
						lifeCounter--;
					}
				}
				
				// first and third line of trees
				if (trees[0].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
					|| trees[1].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
					|| trees[4].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
					|| trees[5].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY()))
				{
					froggy.froggyOnTree = true;
					froggy.froggyPositionX += 130.0f * delta / 1000.0f;	
				}
					
				// second line of trees
				if 
					(trees[2].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
					|| trees[3].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY()))
				{
					froggy.froggyOnTree = true;
					froggy.froggyPositionX -= 130.0f * delta / 1000.0f;
				}
					
				// if no collision with tree				
				if(!trees[0].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
				   && !trees[1].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
				   && !trees[2].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
				   && !trees[3].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
				   && !trees[4].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY())
				   && !trees[5].getBoundingShape().contains(froggy.getBoundingRectangle().getCenterX(), froggy.getBoundingRectangle().getCenterY()))
				   		froggy.froggyOnTree = false;
							
								
				// collision detection: froggy with water
				if (!froggy.froggyOnTree && froggy.froggyPositionY >65
						&& froggy.froggyPositionY < 155) {
					froggy.die();
					lifeCounter--;
				}
				
				// collision detection: froggy with finish line
				if (froggy.getBoundingRectangle().intersects(finishLineRectangle)) {
					levelCompleted = true;
					froggy.froggy.setCurrentFrame(0);
				}

			} // ALLIVE, !GAMEOVER, !LEVELCOMPLETED END

			// PLAYER DEATH
			else if (!froggy.isAlive()) {
				timerDeath -= delta;

				if (timerDeath <= 0 && !Player.gameOver) {
					froggy.resetFroggyIngame();
					timerDeath = 1500;
				}
			} // PLAYER DEATH END

			// LEVEL COMPLETED
			if (levelCompleted) {
				completeTimer -= delta;

				if (completeTimer <= 0) {
					gamesound.stop();
					sbg.enterState(3);
				}
			} // LEVEL COMPLETED END
		} // NOT MENU END

		// MENU OPENED
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
				Game.resetGame(gc, sbg);
			}
		} // GAMEOVER END
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		map.draw(0, 0);
		
		for (int i = 0; i < trees.length; i++) {
			trees[i].Render(g);
		}

		if (!Player.gameOver || timerDeath > 0) {
			froggy.render();
		}

		for (int i = 0; i < cars.length; i++) {
			cars[i].Render(g);
		}

		// re-rendering if on a tree
		if (froggy.froggyOnTree) {
			froggy.render();
		}

		finishLine.draw(361, 0);

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

		// OTHERS
		// timer drawing
		if (!Player.gameOver) {
			g.drawString("Time left : " + timer / 1000, 660, 660);
		}

		// level drawing
		g.drawString("Level 1 - Street", 5, 660);

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
		if (levelCompleted) {
			g.drawString("Level 1 completed!", 350, 310);
		}
	}

	public int getID() {
		return 2;
	}
}
