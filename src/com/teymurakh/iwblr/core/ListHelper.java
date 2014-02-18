package com.teymurakh.iwblr.core;

import java.util.HashMap;
import java.util.List;

import com.teymurakh.iwblr.entities.Entity;
import com.teymurakh.iwblr.geom.Rectangle;

public class ListHelper {
	private RealList<Entity> global;
	private RealList<Entity> active;
	
	private final HashMap<String, RealList<Entity>> tagLists = new HashMap<String, RealList<Entity>>();
	
	private final RealList<Entity> emptyList = new RealList<Entity>(0);
	
	public ListHelper() {
		this.global = new RealList<Entity>(100000);
		this.active = new RealList<Entity>(100000);
	}
	
	public void addEntity(Entity entity) {
		active.addEntity(entity);
		sortIntoLists(entity);
	}
	
	public void addEntityGlobal(Entity entity) {
		global.addEntity(entity);
	}
	
	public void removeEntity(Entity entity) {
		active.removeEntity(entity);
		for (String key : tagLists.keySet()) {
			tagLists.get(key).removeEntity(entity);
		}
	}
	
	public void removeGlobal(Entity entity) {
		global.removeEntity(entity);
	}
	
	
	public void removeEntityGlobal(Entity entity) {
		global.removeEntity(entity);
	}
	
	public RealList<Entity> getGlobal() {
		return global;
	}
	
	public RealList<Entity> getActive() {
		return active;
	}
	
	public RealList<Entity> getByTag(String tag) {
		if (tagLists.containsKey(tag)) {
			return tagLists.get(tag);
		} else {
			return emptyList;
		}
		
	}
	
	////////////////////////////////////////////////////////////////////
	
	public void sortIntoLists(Entity entity) {
		List<String> itemTags = entity.getTypeTags();
		
		for (String tag : itemTags) {
			addToSortedLists(tag, entity);
		}
	}
	
	private void addToSortedLists(String tag, Entity entity) {
		if (!tagLists.containsKey(tag)) {
			tagLists.put(tag, new RealList<Entity>(10000));
		}
		
		tagLists.get(tag).addEntity(entity);
	}
	
	
	////////////////////////////////////
	public void refreshActive(Rectangle rect) {
		active.clear();
		for (String key : tagLists.keySet()) {
			tagLists.get(key).clear();
		}
	}
	
	/////////////////
	
	
	public void updateLists(int delta) {
		//active.updateTypeLists(delta);
	}
	
	public void updateListsEntity(Entity entity) {
		//active.updateListsForEntity(entity);
	}
	
	
	
	////////////////////////////
	
	
	public void tagAdded(String tag, Entity entity) {
		
	}
	
	public void tagRemoved(String tag, Entity entity) {
		
	}
	
	
	
	
	/////
	public int getGlobalSize() {
		return global.size();
	}
	
	public int getActiveSize() {
		return active.size();
	}
	
}
