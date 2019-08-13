package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame {

	public final static String gamename = "Frogger!";
	public final int menu		= 1;
	public final int level_1	= 2;
	public final int level_2	= 3;
	
	public static void resetGame(GameContainer gc,StateBasedGame sbg) throws SlickException
	{
		Level_1.restartLevel1();
		Level_2.restartLevel2();
		Boss.resetBoss();
		Player.resetFroggyGamerover();
		sbg.init(gc);
		sbg.enterState(2);
	}

	public Game(String gamename) throws SlickException {
		super(gamename);
		this.addState(new Menu(menu));
		this.addState(new Level_1(level_1));
		this.addState(new Level_2(level_2));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(level_1).init(gc, this);
		this.getState(level_2).init(gc, this);
		// Womit soll Slick beginnen?
		this.enterState(menu);
	}

	public static void main(String[] args) {
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(800, 680, false);
			appgc.setShowFPS(false);
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
