package com.teymurakh.iwblr.core;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.teymurakh.iwblr.core.graphics.Renderer;
import com.teymurakh.iwblr.entities.*;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.geom.Vec;
import com.teymurakh.iwblr.util.LuaVM;
import com.teymurakh.iwblr.util.MyColor;

public class WorldEditor {
	private ArrayList<String> avaliableEntities;
	private int currentObject = 0;
	private Entity cursorEntity;
	private int gridModifierX = 32;
	private int gridModifierY = 32;
	private int widthModifier = 1;
	private int heightModifier = 1;
	@SuppressWarnings("unused")
	private float widthMultiplier = 1f;
	@SuppressWarnings("unused")
	private float heightMultiplier = 1f;
	private float verticalGrid = 1f;
	private float horizontalGrid = 1f;
	private	float mouseDownX;
	private	float mouseDownY;
	private	float mouseUpX;
	private	float mouseUpY;
	
	
	@SuppressWarnings("unused")
	private Entity currentEntity;
	
	
	public WorldEditor() {
		
	}
	
	public void initialize() {
		avaliableEntities = new ArrayList<String>();
		for (String item : LuaVM.toLoad) {
			avaliableEntities.add(item);
		}

		this.cursorEntity = new Entity(avaliableEntities.get(0));	
	}

	
	public void place(float x, float y, float width, float height) {
		// Check for objects in clicked area, return if it does
		Rectangle cursorRect = new Rectangle(x, y, 0.01f, 0.01f);
		
		for (Entity item : Game.world.getGlobal()) {
			if (Collisions.twoRectangles(cursorRect, item.getRect())) {
				return;
			}
		}
		
		
		
		Entity ent = getCurrentObject();
		
		//Vec multiplier = new Vec(widthMultiplier, heightMultiplier);
		//ent.getD().set(multiplier);
		
		Game.world.place(ent, new Vec(x, y));
	}
	
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
	
	public void mouseLeftClick(float mouseX, float mouseY) {
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
	
	public void enable() {
		Game.config.setEditorEnabled(true);
	}
	
	public void disable() {
		Game.config.setEditorEnabled(false);
	}
	
	public Entity getCurrentObject() {
		return new Entity(avaliableEntities.get(currentObject));
	}
	
	public void delete(float x, float y) {
		
		Rectangle cursorRect = new Rectangle(x, y, 0.01f, 0.01f);
		
		for (Entity item : Game.world.getGlobal()) {
			if (Collisions.twoRectangles(cursorRect, item.getRect())) {
				item.destroy();
			}
		}
		
	}
	
	public void mouseMoved() {
	}
	
	public void update() {
		float worldMouseX = (Mouse.getX() - Game.config.getScreenWidth() / 2f) / Game.config.getScale() + Game.world.getCamera().getPosition().getX();
		float worldMouseY = (Mouse.getY() - Game.config.getScreenHeight() / 2f) / Game.config.getScale() + Game.world.getCamera().getPosition().getY();
		float griddedworldMouseX = (float) Math.floor(worldMouseX * (1f/horizontalGrid)) / (1f/horizontalGrid);
		float griddedworldMouseY = (float) Math.ceil(worldMouseY * (1f/verticalGrid)) / (1f/verticalGrid);
		
		cursorEntity = getCurrentObject();
		
		//Vec multiplier = new Vec(widthMultiplier, heightMultiplier);
		//cursorEntity.getD().multi(multiplier);
		cursorEntity.getPos().setX(griddedworldMouseX);
		cursorEntity.getPos().setY(griddedworldMouseY);
		cursorEntity.notifyPlaced(Game.world);
	}
	
	public void draw(Renderer worldRenderer) {
		cursorEntity.draw(worldRenderer);
		
		int screenWidth = Game.config.getScreenWidth();
		int screenHeight = Game.config.getScreenHeight();
		
		float cameraX = Game.world.getCamera().getPosition().getX() * Game.config.getScale();
		float cameraY = Game.world.getCamera().getPosition().getY() * Game.config.getScale();
		
		int verticalLines;
		if (getGridModifierX() > 2) {
		verticalLines = (int) Math.ceil(screenWidth / getGridModifierX());
		} else {
			verticalLines = 0;
		}
		
		for (int i = 0; i < verticalLines; i++) {
			float lineX = horizontalGrid * Game.config.getScale() * i + screenWidth/2 - cameraX;
			worldRenderer.drawLine(lineX, screenHeight, lineX, 0, 0, new MyColor(0.0f, 0.0f, 0.0f, 1f));
		}
		
		int horizontalLines;
		if (getGridModifierY() > 2) {
			horizontalLines = (int) Math.ceil(screenHeight / getGridModifierY());
		} else {
			horizontalLines = 0;
		}
		
		for (int i = 0; i < horizontalLines; i++) {
			float lineY = getGridModifierY() * i + screenHeight/2 - cameraY;
			worldRenderer.drawLine(0, lineY, screenWidth, lineY, 0, new MyColor(0.0f, 0.0f, 0.0f, 1f));
		}
		
		worldRenderer.drawString(10, 600, 20, "y-grid " + getGridModifierY());
		worldRenderer.drawString(10, 570, 20, "x-grid " + getGridModifierX());
		worldRenderer.drawString(10, 540, 20, "width:  " + widthModifier);
		worldRenderer.drawString(10, 510, 20, "height: " + heightModifier);
		worldRenderer.drawString(10, 480, 20, "x: " + cursorEntity.getX1());
		worldRenderer.drawString(10, 450, 20, "y: " + cursorEntity.getY1());
		worldRenderer.drawString(10, 420, 20, "Entity: " + avaliableEntities.get(currentObject));
		
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
	
	public void increaseWidthModifier() {
		this.widthModifier += 1;
		if (widthModifier == 0) 
			this.widthModifier = 1;
		
		if (widthModifier < 0) {
			this.widthMultiplier = (1f / 32f) * (32f/(float)Math.abs(widthModifier-1)); 
		}
		if (widthModifier > 0) {
			this.widthMultiplier = (1f / 32f) * ((widthModifier) * 32f);
		}
	}
	
	public void decreaseWidthModifier() {
		this.widthModifier -= 1;
		if (widthModifier == 0) 
			this.widthModifier = -1;
		
		if (widthModifier < 0) {
			this.widthMultiplier = (1f / 32f) * (32f/(float)Math.abs(widthModifier-1)); 
		}
		if (widthModifier > 0) {
			this.widthMultiplier = (1f / 32f) * ((widthModifier) * 32f);
		}
	}
	
	public void increaseHeightModifier() {
		this.heightModifier += 1;
		if (heightModifier == 0) 
			this.heightModifier = 1;
		
		if (heightModifier < 0) {
			this.heightMultiplier = (1f / 32f) * (32f/(float)Math.abs(heightModifier-1)); 
		}
		if (heightModifier > 0) {
			this.heightMultiplier = (1f / 32f) * ((heightModifier) * 32f);
		}
	}
	
	public void decreaseHeightModifier() {
		this.heightModifier -= 1;
		if (heightModifier == 0) 
			this.heightModifier = -1;
		
		if (heightModifier < 0) {
			this.heightMultiplier = (1f / 32f) * (32f/(float)Math.abs(heightModifier-1)); 
		}
		if (heightModifier > 0) {
			this.heightMultiplier = (1f / 32f) * ((heightModifier) * 32f);
		}
	}
	

	
	public void nextObject() {
		this.currentObject += 1;
		if (this.currentObject > avaliableEntities.size() - 1) {
			this.currentObject = 0;
		}
	}
	
	public void previousObject() {
		this.currentObject -= 1;
		if (this.currentObject < 0) {
			this.currentObject = avaliableEntities.size() - 1;
		}
	}

	public int getGridModifierX() {
		return gridModifierX;
	}

	public int getGridModifierY() {
		return gridModifierY;
	}

}
