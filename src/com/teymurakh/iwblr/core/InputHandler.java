package com.teymurakh.iwblr.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class InputHandler {
	private ArrayList<Integer> keyList;
	private HashMap<Integer, Boolean> currentlyDown;
	private HashMap<Integer, Boolean> mouseCurrentlyDown;
	private int mouseX;
	private int mouseY;
	
	public void initialize() {
		keyList = new ArrayList<Integer>();
		keyList.add(Keyboard.KEY_LEFT);
		keyList.add(Keyboard.KEY_RIGHT);
		keyList.add(Keyboard.KEY_UP);
		keyList.add(Keyboard.KEY_DOWN);
		keyList.add(Keyboard.KEY_ESCAPE);
		keyList.add(Keyboard.KEY_X);
		keyList.add(Keyboard.KEY_D);
		keyList.add(Keyboard.KEY_Q);
		keyList.add(Keyboard.KEY_G);
		keyList.add(Keyboard.KEY_H);
		keyList.add(Keyboard.KEY_B);
		keyList.add(Keyboard.KEY_N);
		keyList.add(Keyboard.KEY_P);
		keyList.add(Keyboard.KEY_W);
		keyList.add(Keyboard.KEY_E);
		keyList.add(Keyboard.KEY_A);
		keyList.add(Keyboard.KEY_S);
		keyList.add(Keyboard.KEY_U);
		keyList.add(Keyboard.KEY_Y);
		keyList.add(Keyboard.KEY_T);
		keyList.add(Keyboard.KEY_F);
		keyList.add(Keyboard.KEY_R);
		keyList.add(Keyboard.KEY_C);
		keyList.add(Keyboard.KEY_1);
		keyList.add(Keyboard.KEY_2);
		keyList.add(Keyboard.KEY_3);
		keyList.add(Keyboard.KEY_4);
		keyList.add(Keyboard.KEY_5);
		keyList.add(Keyboard.KEY_6);
		keyList.add(Keyboard.KEY_7);
		keyList.add(Keyboard.KEY_8);
		keyList.add(Keyboard.KEY_9);
		keyList.add(Keyboard.KEY_O);
		keyList.add(Keyboard.KEY_SPACE);
		keyList.add(Keyboard.KEY_LSHIFT);
		
		currentlyDown = new HashMap<Integer, Boolean>();
		for (int key : keyList) {
			currentlyDown.put(key, false);
		}
		
		mouseCurrentlyDown = new HashMap<Integer, Boolean>();
		mouseCurrentlyDown.put(0, false);
		mouseCurrentlyDown.put(1, false);
	}
	
	public boolean isCurrentlyDown(int key) {
		return currentlyDown.get(key);
	}
	
	public void reset() {
		for (int key : keyList) {
			currentlyDown.put(key, false);
		}
	}
	
	public void reset(int exceptionKey) {
		for (int key : keyList) {
			if (!(key == exceptionKey)) {
				currentlyDown.put(key, false);
			}
		}
	}
	
	public void update() {
		// Keyboard
		for (int key : keyList) {
			if (Keyboard.isKeyDown(key) && !isCurrentlyDown(key)) {		
				currentlyDown.put(key, true);
				keyDown(key);
			}
			if (!Keyboard.isKeyDown(key) && isCurrentlyDown(key)) {
				currentlyDown.put(key, false);
				keyUp(key);
			}
		}
		// Mouse Coordinates
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		
		if (mouseX != Mouse.getX() || mouseY != Mouse.getY()) {
			mouseMoved();
		}
		
		// Mouse Left
		if (Mouse.isButtonDown(0) && !mouseCurrentlyDown.get(0)) {		
			mouseCurrentlyDown.put(0, true);
			mouseDown(0);
		}
		if (!Mouse.isButtonDown(0) && mouseCurrentlyDown.get(0)) {
			mouseCurrentlyDown.put(0, false);
			mouseUp(0);
		}
		// Mouse Right
		if (Mouse.isButtonDown(1) && !mouseCurrentlyDown.get(1)) {		
			mouseCurrentlyDown.put(1, true);
			mouseDown(1);
		}
		if (!Mouse.isButtonDown(0) && mouseCurrentlyDown.get(1)) {
			mouseCurrentlyDown.put(1, false);
			mouseUp(1);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	public void keyDown(int key) {
		if (Game.config.outputInput()) {
			Game.console.printLine(key + " down");
		}
		if (Game.config.isEditorEnabled()) {
			////////////////////////////////////////////////////////////////////////
			if (key == Keyboard.KEY_P) 
				Game.worldEditor.disable();
			
			if (key == Keyboard.KEY_ESCAPE) {
				if (Game.isPaused()) {
					Game.unPause();
				}
				else {
					Game.pause();
				}
			}
			if (key == Keyboard.KEY_G) 
				Game.worldEditor.decreaseWidthModifier();
			
			if (key == Keyboard.KEY_H) 
				Game.worldEditor.increaseWidthModifier();
			
			if (key == Keyboard.KEY_B) 
				Game.worldEditor.decreaseHeightModifier();
			
			if (key == Keyboard.KEY_N) 
				Game.worldEditor.increaseHeightModifier();
			
			if (key == Keyboard.KEY_R) 
				Game.newGame();
			
			if (key == Keyboard.KEY_Q) 
				Game.worldEditor.decreaseXGrid();
			
			if (key == Keyboard.KEY_W) 
				Game.worldEditor.increaseXGrid();
			
			if (key == Keyboard.KEY_E) 
				Game.worldEditor.increaseYGrid();
			
			if (key == Keyboard.KEY_D) 
				Game.worldEditor.decreaseYGrid();
			
			if (key == Keyboard.KEY_T) 
				Game.worldEditor.previousObject();
			
			if (key == Keyboard.KEY_Y) 
				Game.worldEditor.nextObject();
			
			if (key == Keyboard.KEY_UP) 
				Game.world.getCamera().setMovingUp(true);
			
			if (key == Keyboard.KEY_DOWN) 
				Game.world.getCamera().setMovingDown(true);
			
			if (key == Keyboard.KEY_LEFT) 
				Game.world.getCamera().setMovingLeft(true);
			
			if (key == Keyboard.KEY_RIGHT) 
				Game.world.getCamera().setMovingRight(true);
			
			if (key == Keyboard.KEY_F) {
				if (Game.config.isFlyingEnabled()) {
					Game.config.setFlyingEnabled(false);
				} else {
					Game.config.setFlyingEnabled(true);
				}
			}
			/////////////////////////////////////////////////////////////////////////////
		} else {
			
			if (key == Keyboard.KEY_P) {
				Game.worldEditor.enable();
			}
			
			if (key == Keyboard.KEY_ESCAPE) {
				if (Game.isPaused()) {
					Game.unPause();
				}
				else {
					Game.pause();
				}
			}
			
			if (key == Keyboard.KEY_R) {
				Game.newGame();
			}
			
			if (key == Keyboard.KEY_1) {
				Game.world.getGuy().equipSlot(1);
			}
			if (key == Keyboard.KEY_2) {
				Game.world.getGuy().equipSlot(2);
			}
			if (key == Keyboard.KEY_3) {
				Game.world.getGuy().equipSlot(3);
			}
			if (key == Keyboard.KEY_4) {
				Game.world.getGuy().equipSlot(4);
			}
			if (key == Keyboard.KEY_5) {
				Game.world.getGuy().equipSlot(5);
			}
			if (key == Keyboard.KEY_6) {
				Game.world.getGuy().equipSlot(6);
			}
			
			if (key == Keyboard.KEY_UP) {
				Game.world.getGuy().setMovingUp(true);
			}
			
			if (key == Keyboard.KEY_DOWN) {
				Game.world.getGuy().setMovingDown(true);
			}
			
			if (key == Keyboard.KEY_LEFT) {
				Game.world.getGuy().setMovingLeft(true);
			}
			
			if (key == Keyboard.KEY_RIGHT) {
				Game.world.getGuy().setMovingRight(true);
			}
			
			if (key == Keyboard.KEY_W) 
				Game.world.getCamera().setMovingUp(true);
			
			if (key == Keyboard.KEY_S) 
				Game.world.getCamera().setMovingDown(true);
			
			if (key == Keyboard.KEY_A) 
				Game.world.getCamera().setMovingLeft(true);
			
			if (key == Keyboard.KEY_D) 
				Game.world.getCamera().setMovingRight(true);
			
			if (key == Keyboard.KEY_X) {
				Game.world.getGuy().useWeapon();;
			}
			
			if (key == Keyboard.KEY_F) {
				if (Game.config.isFlyingEnabled()) {
					Game.config.setFlyingEnabled(false);
				} else {
					Game.config.setFlyingEnabled(true);
				}
			}
			
			if (key == Keyboard.KEY_D) {
				System.out.println("debug pressed");
				if (Game.config.isDrawDebug()) {
					Game.config.setDrawDebug(false);
				} else {
					Game.config.setDrawDebug(true);
				}
			}
			
			if (key == Keyboard.KEY_LSHIFT) {
				Game.world.getGuy().jumpPressed();
			}
		}
	}
	
	public void keyUp(int key) {
		if (Game.config.outputInput()) {
			Game.console.printLine(key + " up");
		}
		
		if (Game.config.isEditorEnabled()) {
			///////////////
			if (key == Keyboard.KEY_UP) {
				Game.world.getCamera().setMovingUp(false);
			}
			
			if (key == Keyboard.KEY_DOWN) {
				Game.world.getCamera().setMovingDown(false);
			}
			
			if (key == Keyboard.KEY_LEFT) {
				Game.world.getCamera().setMovingLeft(false);
			}
			
			if (key == Keyboard.KEY_RIGHT) {
				Game.world.getCamera().setMovingRight(false);
			}
			////////////////
		} else {
			if (key == Keyboard.KEY_W) 
				Game.world.getCamera().setMovingUp(false);
			
			if (key == Keyboard.KEY_S) 
				Game.world.getCamera().setMovingDown(false);
			
			if (key == Keyboard.KEY_A) 
				Game.world.getCamera().setMovingLeft(false);
			
			if (key == Keyboard.KEY_D) 
				Game.world.getCamera().setMovingRight(false);
			
		
			if (key == Keyboard.KEY_UP) {
				Game.world.getGuy().setMovingUp(false);
			}
		
			if (key == Keyboard.KEY_DOWN) {
				Game.world.getGuy().setMovingDown(false);
			}
		
			if (key == Keyboard.KEY_LEFT) {
				Game.world.getGuy().setMovingLeft(false);
			}
		
			if (key == Keyboard.KEY_RIGHT) {
				Game.world.getGuy().setMovingRight(false);
			}
		
		
			if (key == Keyboard.KEY_SPACE) {
				Game.world.getGuy().jumpReleased();
			}
		
			if (key == Keyboard.KEY_LSHIFT) {
				Game.world.getGuy().jumpReleased();
			}
		}
	}
	
	public void mouseDown(int key) {
		if (Game.config.outputInput()) {
			Game.console.printLine("mouse down at " + mouseX + " " + mouseY);
		}

		
		if (Game.config.isEditorEnabled()) {
			if (key == 0) {
				if (!Game.isPaused()) {
					Game.worldEditor.mouseRightDown(mouseX, mouseY);
				}
				else {
					Game.menu.mouseClick(key, mouseX, mouseY);
				}
			}
		
			if (key == 1) {
				if (!Game.isPaused()) {
				Game.worldEditor.mouseLeftClick(mouseX, mouseY);
				} else {
					Game.menu.mouseClick(key, mouseX, mouseY);
				}
			}
		} else {
			if (!Game.isPaused()) {
				float worldMouseX = ((float)mouseX - (float)Game.config.getScreenWidth() / 2f) / (float)Game.config.getScale() + Game.world.getCamera().getPosition().getX();
				float worldMouseY = ((float)mouseY - (float)Game.config.getScreenHeight() / 2f) / (float)Game.config.getScale() + Game.world.getCamera().getPosition().getY();
				Game.world.getGuy().mouseClick(worldMouseX, worldMouseY);
			} else {
				Game.menu.mouseClick(key, mouseX, mouseY);
			}
		}
	}
	
	public void mouseUp(int key) {
		if (Game.config.outputInput()) {
			Game.console.printLine("mouse up at " + mouseX + " " + mouseY);
		}
		if (Game.config.isEditorEnabled()) {
			if (key == 0) {
				if (!Game.isPaused()) {
					Game.worldEditor.mouseRightUp(mouseX, mouseY);
				}
			}
		
			if (key == 1) {
				if (!Game.isPaused()) {
					Game.worldEditor.mouseLeftUp(mouseX, mouseY);
				}
			}
		}
	}
	
	public void mouseMoved() {
		if (!Game.isPaused()) {
			Game.worldEditor.mouseMoved();
		}
	}
}
