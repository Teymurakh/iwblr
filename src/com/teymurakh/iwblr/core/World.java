package com.teymurakh.iwblr.core;



import com.teymurakh.iwblr.entities.*;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.geom.Vec;
import com.teymurakh.iwblr.util.Timer6;

public class World {
	
	private Guy guy;
	
	private String name;
	private Camera camera;
	
	private ListHelper listHelper;
	
	private Vec gravityAcceleration;
	private boolean paused;
	
	private Timer6 tickCountTimer = new Timer6(1f, 1f);
	private int ticks = 0;
	
	private final WorldIO worldIO;
	
	public World() {
		worldIO = new WorldIO("resources/levels/");
	}
	
	public void initialize() {
		
		listHelper = new ListHelper();
		
		gravityAcceleration = new Vec(0f, Game.config.getGravity());//-(0.4f * 50f / 32f * 50f));
		guy = new Guy();
		guy.notifyPlaced(this);
		
		camera = new Camera(Game.config.isCameraLocked(), new Vec (12.5f, 8.5f));
		
		nanoLastTime = getNanoTime();
	}

	/////////////////////////////////////// TIME /////////////////////////////
	private long nanoLastTime;
	
	private long getNanoTime() {
		return System.nanoTime();
	}	

	private long getNanoDelta() {
		long currentTime = getNanoTime();
		long delta = currentTime - nanoLastTime;
		nanoLastTime = currentTime;
		return delta;
	}

	public long getWorldTime() {
		return getNanoTime()/1000000;
	}


	//////////////////////////////////////////////////////////////////////////	
	public void nonEntityUpdate() {
			float deltaNano = getNanoDelta();
			float delta = deltaNano/1000000000f;

			camera.update(delta);
			ticks++;
			if (tickCountTimer.evaluate(delta)) {
				System.out.println("Ticks last second: " + ticks);
				ticks = 0;
			}
	}
	
	public void update() {
		if (!paused) {
			float deltaNano = getNanoDelta();
			float delta = deltaNano/1000000000f;

			updateLoop(delta);
			ticks++;
			if (tickCountTimer.evaluate(delta)) {
				System.out.println("Ticks last second: " + ticks);
				ticks = 0;
			}
		}
	}
	
	
	
	private void updateLoop(float delta) {
		checkScreenChange();
		camera.update(delta);

		if (!guy.isDead()) {
			guy.update(delta);
		}

		// Update all entities
		for(Entity item : listHelper.getActive()) {
			item.update(delta);
		}

		// Call collision handler to doCollisions
		Game.collisions.doCollisions(this);
	}
	
	
	
	
	
	private Rectangle lastRoomRect = new Rectangle(0f, 0f, 0f, 0f);
	
	private void checkScreenChange() {
		Rectangle roomRect = getScreenForPos(guy.getCenterX(), guy.getCenterY());
		if (!roomRect.equals(lastRoomRect)) {
			camera.setPosition(new Vec(roomRect.getX() + roomRect.getWidth()/2f, roomRect.getY() - roomRect.getHeight()/2f));
			refreshActiveList(roomRect);
			lastRoomRect = roomRect;
		}
	}
	
	private Rectangle getScreenForPos(float x, float y) {
		float rectWidth = 25f;
		float rectHeight = 19f;
		float rectX = rectWidth * (float)Math.floor(x/rectWidth);
		float rectY = rectHeight * (float)Math.floor(y/rectHeight);
		
		float extraWidth = 0f;
		float extraHeight = 0f;
		
		float usedRectX = rectX - extraWidth/2f;
		float usedRectY = rectY + extraHeight/2f + 19f - 1f;
		float usedWidth = rectWidth + extraWidth;
		float usedHeight = rectHeight + extraHeight;
		
		Rectangle roomRect = new Rectangle(usedRectX, usedRectY, usedWidth, usedHeight);
		return roomRect;
	}
	

	protected void refreshActiveList(Rectangle roomRect) {
		System.out.println("Refreshing active lists");
		listHelper.refreshActive(roomRect);	
		
		for(Entity item : getGlobal()) {
			if (Collisions.twoRectangles(roomRect, item.getRect())) {
				Entity copiedEntity = new Entity(item.getScriptName());
				Vec copiedPos = new Vec(item.getPos());
				place(copiedEntity, copiedPos);
			}
		}
		
	}
	
	public void pause() {
		paused = true;
	}
	
	public void unPause() {
		paused = false;
	}
	

	//Checks if the entity is contained in the list of entities and removes it
	public void notifyTagAdded(String tag, Entity entity) {
		listHelper.tagAdded(tag, entity);
	}
	
	public void notifyTagRemoved(String tag, Entity entity) {
		listHelper.tagRemoved(tag, entity);
	}
	
	
	public void removeEntity(Entity entity) {
		listHelper.removeEntity(entity);
	}

	public void removeGlobal(Entity entity) {
		listHelper.removeGlobal(entity);
	}
	
	public void place(Entity entity, Vec position) {
		listHelper.addEntity(entity);
		entity.getPos().set(position);
		entity.notifyPlaced(this);
	}
	
	
	public void placeGlobal(Entity entity, Vec position) {
		listHelper.addEntityGlobal(entity);
		listHelper.addEntity(entity);
		entity.getPos().set(position);
		entity.notifyPlaced(this);
	}
	
	
	public void respawnGuy() {
		guy.setDead(false);
	}
	
	// GETTERS AND SETTERS
	

	public Camera getCamera() {
		return camera;
	}

	public Guy getGuy() {
		return guy;
	}

	public Vec getGravityAcceleration() {
		return gravityAcceleration;
	}

	public boolean isPaused() {
		return paused;
	}

	public String getName() {
		return name;
	}

	public int getGlobalSize() {
		return listHelper.getGlobalSize();
	}
	
	public int getActiveSize() {
		return listHelper.getActiveSize();
	}	
	
	public RealList<Entity> getGlobal() {
		return listHelper.getGlobal();
	}
	
	public RealList<Entity> getActive() {
		return listHelper.getActive();
	}
	
	public RealList<Entity> getActiveTag(String tag) {
		return listHelper.getByTag(tag);
	}
	
	/////////////////////////////////////////////////////
	/////////////////// LOADING AND SAVING HERE /////////
	/////////////////////////////////////////////////////

	public void loadLevel(String levelName) {
		worldIO.loadLevel(levelName, this);
		this.name = levelName;
		loadState();
	}
	
	/// SAVING
	public void saveLevel(String levelName) {
		worldIO.saveLevel(levelName, this);
	}
	
	public void loadState() {
		StringMap worldState = new StringMap();
		worldState.loadState("saves/" + name + ".sav");
		
		float guyX = Float.parseFloat(worldState.get("guy_x"));
		float guyY = Float.parseFloat(worldState.get("guy_y"));

		guy.setPosX(guyX);
		guy.setPosY(guyY);
		
		nanoLastTime = getNanoTime();
	}
	
	public void saveState() {
		StringMap worldState = new StringMap();
		
		worldState.put("guy_x", guy.getPosX()+"");
		worldState.put("guy_y", guy.getPosY()+"");
		
		worldState.saveState("saves/" + name + ".sav");
	}
	
	
	
	
	
	
}