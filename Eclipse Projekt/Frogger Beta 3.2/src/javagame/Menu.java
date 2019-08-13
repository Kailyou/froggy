package javagame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState {

	Image menubild;
	Image playNow;
	Image exitGame;

	Music introMusic;

	static boolean menuActive = true;

	public Menu(int state) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		menubild = new Image("data/Images/menu/menubild.png");
		playNow = new Image("data/Images/menu/playNow.png");
		exitGame = new Image("data/Images/menu/exitGame.png");
		introMusic = new Music("data/sounds/Frogger_Intro.wav");

		if (!introMusic.playing()) {
			introMusic.play();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		menubild.draw(0, 0);
		playNow.draw(290, 380);
		exitGame.draw(290, 480);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		MouseOverArea moaPlayGame = new MouseOverArea(gc, playNow, 290, 380,
				211, 51);
		if (moaPlayGame.isMouseOver() && Mouse.isButtonDown(0)) {
			if (menuActive)
				sbg.enterState(2);
			else {
				Game.resetGame(gc, sbg);
			}
		}

		MouseOverArea moaExitGame = new MouseOverArea(gc, exitGame, 290, 480,
				211, 52);
		if (moaExitGame.isMouseOver() && Mouse.isButtonDown(0)) {
			System.exit(0);
		}
	}

	public int getID() {
		return 1;
	}
}
