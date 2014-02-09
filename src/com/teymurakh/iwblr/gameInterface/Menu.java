package com.teymurakh.iwblr.gameInterface;

import java.util.ArrayList;

import com.teymurakh.iwblr.core.Game;
import com.teymurakh.iwblr.core.graphics.Drawable;
import com.teymurakh.iwblr.core.graphics.Renderer;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.util.MyColor;

public class Menu {
	
	private int levelToLoad;
	
	private ArrayList<Drawable> elementList;
	private RelativeBlock continueButton;
	private RelativeBlock newGameButton;
	
	private RelativeBlock previousLevelButton;
	private RelativeBlock nextLevelButton;
	private RelativeBlock loadLevelButton;
	
	private RelativeBlock saveLevelButton;
	
	private InterfaceBlock menuButtons;
	
	private ArrayList<Drawable> gameOverScreen;
	private InterfaceBlock gameOverConatiner;
	private RelativeBlock gameOverText;
	
	public void initialize() {
		this.levelToLoad = 1;
		elementList = new ArrayList<Drawable>();
		gameOverScreen = new ArrayList<Drawable>();
		
		this.menuButtons = new InterfaceBlock(new Rectangle(Game.config.getScreenWidth()/2f -600.0f/2f, Game.config.getScreenHeight()/2f +400.0f/2f, 600.0f, 400.0f), new MyColor(MyColor.RED));
		elementList.add(this.menuButtons);
		
		this.continueButton = new RelativeBlock(100.0f, -20.0f, 400.0f, 70.0f, new MyColor(MyColor.GREEN), "Continue");
		menuButtons.addElement(this.continueButton);
		
		this.newGameButton = new RelativeBlock(100.0f, -100.0f, 400.0f, 70.0f, new MyColor(MyColor.GREEN), "New Game");
		menuButtons.addElement(this.newGameButton);
		
		this.previousLevelButton = new RelativeBlock(100.0f, -200.0f, 32.0f, 64.0f, new MyColor(MyColor.GREEN), Game.textureHandler.getMappedTexture("left_arrow"));
		menuButtons.addElement(this.previousLevelButton);
		
		this.nextLevelButton = new RelativeBlock(468.0f, -200.0f, 32.0f, 64.0f, new MyColor(MyColor.GREEN), Game.textureHandler.getMappedTexture("right_arrow"));
		menuButtons.addElement(this.nextLevelButton);
		
		this.loadLevelButton = new RelativeBlock(150.0f, -200.0f, 300.0f, 64.0f, new MyColor(MyColor.GREEN), "Load Level " + levelToLoad);
		menuButtons.addElement(this.loadLevelButton);
		
		this.saveLevelButton = new RelativeBlock(150.0f, -300.0f, 300.0f, 64.0f, new MyColor(MyColor.GREEN), "Save Level " + levelToLoad);
		menuButtons.addElement(saveLevelButton);
	
		this.gameOverText = new RelativeBlock(0, 0, 766.0f, 160.0f, new MyColor(MyColor.GREEN), Game.textureHandler.getMappedTexture("gameover"));
		this.gameOverConatiner = new InterfaceBlock(new Rectangle(Game.config.getScreenWidth()/2f -766.0f/2f, Game.config.getScreenHeight()/2f +160.0f/2f, 766.0f, 160.0f), new MyColor(MyColor.RED));
		this.gameOverConatiner.addElement(gameOverText);
		gameOverScreen.add(gameOverConatiner);
	}
	
	public void update(long delta, Renderer worldRenderer) {
		
	}
	
	public String getCurrentlySelectedLevelName() {
		return "level" + levelToLoad;
	}
	
	public void mouseClick(int key, int x, int y) {
		if (key == 0) {
			if (continueButton.getRectangle().isInside(x, y)) {
				Game.unPause();
			}
			
			if (newGameButton.getRectangle().isInside(x, y)) {
				Game.newGame();
			}
			
			if (nextLevelButton.getRectangle().isInside(x, y)) {
				nextLevel();
			}
			
			if (previousLevelButton.getRectangle().isInside(x, y)) {
				previousLevel();
			}
			
			if (loadLevelButton.getRectangle().isInside(x, y)) {
				Game.loadLevel("level" + levelToLoad);
			}
			if (saveLevelButton.getRectangle().isInside(x, y)) {
				Game.saveLevel("level" + levelToLoad);
			}
		}
	}
	
	public void nextLevel(){
		levelToLoad += 1;
		if (levelToLoad > 9) {
			levelToLoad = 9;
		}
		this.loadLevelButton.setTextLine("Load Level " + levelToLoad);
		this.saveLevelButton.setTextLine("Save Level " + levelToLoad);
	}
	
	public void previousLevel(){
		levelToLoad -= 1;
		if (levelToLoad < 1) {
			levelToLoad = 1;
		}
		this.loadLevelButton.setTextLine("Load Level " + levelToLoad);
		this.saveLevelButton.setTextLine("Save Level " + levelToLoad);
	}

	public ArrayList<Drawable> getGameOverScreen() {
		return gameOverScreen;
	}

	public ArrayList<Drawable> getElementList() {
		return elementList;
	}
}
