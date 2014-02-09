package com.teymurakh.iwblr.core;


public class WorldUpdateThread extends Thread {
	volatile boolean stopRequested;
	private World world;
	private String levelToBeLoaded;
	   
	   public WorldUpdateThread(World world) {
		   this.world = world;
		   this.stopRequested = false;
	   }

	   public void setLevelToBeLoaded(String name) {
		   this.levelToBeLoaded = name;
	   }
	   
	   @Override
	   public void run() {
		   world.loadLevel(levelToBeLoaded);
		   
		   while (!this.stopRequested) {
			   world.update();
		   }
		   
		   Game.console.printLine("WorldUpdateThread " + this + " has successfully stopped running");
	   }
	   
	   public void requestStop() {
		   this.stopRequested = true;
	   }
	}