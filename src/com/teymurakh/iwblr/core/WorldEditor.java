package com.teymurakh.iwblr.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Mouse;

import com.teymurakh.iwblr.core.graphics.Renderer;
import com.teymurakh.iwblr.entities.*;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.geom.Vec;
import com.teymurakh.iwblr.util.LuaVM;
import com.teymurakh.iwblr.util.MyColor;

public class WorldEditor {
	private ArrayList<String> avaliableEntities;
	private HashMap<String, Entity> avaliableEntitiesCreated;
	private int currentObject = 0;
	private Entity cursorEntity;
	private int gridModifierX = 32;
	private int gridModifierY = 32;
	private int widthModifier = 1;
	private int heightModifier = 1;
	private float verticalGrid = 1f;
	private float horizontalGrid = 1f;
	private	float mouseDownX;
	private	float mouseDownY;
	private	float mouseUpX;
	private	float mouseUpY;
	
	public void initialize() {
		avaliableEntities = new ArrayList<String>();
		avaliableEntitiesCreated = new HashMap<String, Entity>();
		for (String item : LuaVM.getAllAvaliable()) {
			avaliableEntities.add(item);
			avaliableEntitiesCreated.put(item, new Entity(item));
		}

		this.cursorEntity = getExisting(avaliableEntities.get(0));	
	}

	
	private Entity getExisting(String key) {
		return avaliableEntitiesCreated.get(key);
	}
	
	
	private void place(float x, float y, float width, float height) {
		// Check for objects in clicked area, return if it does
		Rectangle cursorRect = new Rectangle(x, y, 0.01f, 0.01f);
		
		for (Entity item : Game.world.getGlobal()) {
			if (Collisions.twoRectangles(cursorRect, item.getRect())) {
				return;
			}
		}
		
		Entity newEntity = new Entity(avaliableEntities.get(currentObject));
		//Game.world.place(newEntity, new Vec(x, y));
		Game.world.placeGlobal(newEntity, new Vec(x, y));
	}
	

	
	private void delete(float x, float y) {
		Rectangle cursorRect = new Rectangle(x, y, 0.01f, 0.01f);
		for (Entity item : Game.world.getGlobal()) {
			if (Collisions.twoRectangles(cursorRect, item.getRect())) {
				
				
				Game.world.removeEntity(item);
				Game.world.removeGlobal(item);
				
				
			}
		}
	}
	
	public void update() {
		float worldMouseX = (Mouse.getX() - Game.config.getScreenWidth() / 2f) / Game.config.getScale() + Game.world.getCamera().getPosition().getX();
		float worldMouseY = (Mouse.getY() - Game.config.getScreenHeight() / 2f) / Game.config.getScale() + Game.world.getCamera().getPosition().getY();
		float griddedworldMouseX = (float) Math.floor(worldMouseX * (1f/horizontalGrid)) / (1f/horizontalGrid);
		float griddedworldMouseY = (float) Math.ceil(worldMouseY * (1f/verticalGrid)) / (1f/verticalGrid);
		
		cursorEntity.getPos().setX(griddedworldMouseX);
		cursorEntity.getPos().setY(griddedworldMouseY);
	}
	
	public void draw(Renderer worldRenderer) {
		cursorEntity.draw(worldRenderer, Game.world.getCamera());
		
		int screenWidth = Game.config.getScreenWidth();
		int screenHeight = Game.config.getScreenHeight();
		
		float cameraX = Game.world.getCamera().getPosition().getX() * Game.config.getScale();
		float cameraY = Game.world.getCamera().getPosition().getY() * Game.config.getScale();
		
		int verticalLines;
		if (gridModifierX > 2) {
		verticalLines = (int) Math.ceil(screenWidth / gridModifierX);
		} else {
			verticalLines = 0;
		}
		
		for (int i = 0; i < verticalLines; i++) {
			//float lineX = gridModifierX * i + screenWidth/2 - cameraX;
			float lineX = gridModifierX * i + (float)(Math.ceil(cameraX/gridModifierX) * gridModifierX) - cameraX + 16f;
			worldRenderer.drawLine(lineX, screenHeight, lineX, 0, 0, new MyColor(0.0f, 0.0f, 0.0f, 0.4f));
		}
		
		int horizontalLines;
		if (gridModifierY > 2) {
			horizontalLines = (int) Math.ceil(screenHeight / gridModifierY);
		} else {
			horizontalLines = 0;
		}
		
		for (int i = 0; i < horizontalLines; i++) {
			//float lineY = (gridModifierY * i) + screenHeight/2f - cameraY + (float)(Math.ceil(cameraY/gridModifierY) * gridModifierY);
			  float lineY = (gridModifierY * i) + (float)(Math.ceil(cameraY/gridModifierY) * gridModifierY) - cameraY + 12f; // TODO fix the mysterious 12f number. No idea why it works
			
			worldRenderer.drawLine(0, lineY, screenWidth, lineY, 0, new MyColor(0.0f, 0.0f, 0.0f, 1f));
		}
		int distanceY = 20;
		worldRenderer.drawString(10, 600+(-distanceY*0), 14, "x: " + cursorEntity.getX1());
		worldRenderer.drawString(10, 600+(-distanceY*1), 14, "y: " + cursorEntity.getY1()); 
		worldRenderer.drawString(10, 600+(-distanceY*2), 14, "width:  " + widthModifier);
		worldRenderer.drawString(10, 600+(-distanceY*3), 14, "height: " + heightModifier);
		worldRenderer.drawString(10, 600+(-distanceY*4), 14, "y-grid " + gridModifierY);
		worldRenderer.drawString(10, 600+(-distanceY*5), 14, "x-grid " + gridModifierX);
		worldRenderer.drawString(10, 600+(-distanceY*6), 14, "Entity: " + avaliableEntities.get(currentObject));
		
	}

	private void changeCursorEntity() {
		cursorEntity = getExisting(avaliableEntities.get(currentObject));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////// INPUT /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void mouseRightDown(float mouseX, float mouseY) {
		mouseDownX = mouseX;
		mouseDownY = mouseY;
	}
	
	public void mouseRightUp(float mouseX, float mouseY) {
		mouseUpX = mouseX;
		mouseUpY = mouseY;
		float mouseWidth = mouseUpX - mouseDownX;
		float mouseHeight = mouseUpY - mouseDownY;
		
		float worldMouseX = (mouseDownX - Game.config.getScreenWidth() / 2f) / Game.config.getScale() + Game.world.getCamera().getPosition().getX();
		float worldMouseY = (mouseDownY - Game.config.getScreenHeight() / 2f) / Game.config.getScale() + Game.world.getCamera().getPosition().getY();
		
		float worldMouseWidth = mouseWidth / Game.config.getScale();
		float worldMouseHeight = mouseHeight / Game.config.getScale();
		
		float griddedWorldMouseWidth = (float) Math.floor(worldMouseWidth * (1f/horizontalGrid)) / (1f/horizontalGrid);
		float griddedWorldMouseHeight = (float) Math.floor(worldMouseHeight * (1f/horizontalGrid)) / (1f/horizontalGrid);
		
		float griddedworldMouseX = (float) Math.floor(worldMouseX * (1f/horizontalGrid)) / (1f/horizontalGrid);
		float griddedworldMouseY = (float) Math.ceil(worldMouseY * (1f/verticalGrid)) / (1f/verticalGrid);
		
		place(griddedworldMouseX, griddedworldMouseY, griddedWorldMouseWidth, griddedWorldMouseHeight);
	}
	
	public void mouseLeftDown(float mouseX, float mouseY) {
		mouseDownX = mouseX;
		mouseDownY = mouseY;
	}
	
	public void mouseLeftUp(float mouseX, float mouseY) {
		mouseUpX = mouseX;
		mouseUpY = mouseY;
		
		float worldMouseX = (mouseDownX - Game.config.getScreenWidth() / 2f) / Game.config.getScale() + Game.world.getCamera().getPosition().getX();
		float worldMouseY = (mouseDownY - Game.config.getScreenHeight() / 2f) / Game.config.getScale() + Game.world.getCamera().getPosition().getY();
		
		delete(worldMouseX, worldMouseY);
	}
	
	public void mouseMoved() {
	}
	
	public void increaseYGrid() {
		this.gridModifierY += 1;
		this.verticalGrid = (1f / 32f) * gridModifierY;
	}
	
	public void decreaseYGrid() {
		this.gridModifierY -= 1;
		this.verticalGrid = (1f / 32f) * gridModifierY;
	}
	
	public void increaseXGrid() {
		this.gridModifierX += 1;
		this.horizontalGrid = (1f / 32f) * gridModifierX;
	}
	
	public void decreaseXGrid() {
		this.gridModifierX -= 1;
		this.horizontalGrid = (1f / 32f) * gridModifierX;
	}
	
	public void nextObject() {
		this.currentObject += 1;
		if (this.currentObject > avaliableEntities.size() - 1) {
			this.currentObject = 0;
		}
		
		changeCursorEntity();
	}
	
	public void previousObject() {
		this.currentObject -= 1;
		if (this.currentObject < 0) {
			this.currentObject = avaliableEntities.size() - 1;
		}

		changeCursorEntity();
	}
}
