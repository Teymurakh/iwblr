package com.teymurakh.iwblr.core.graphics;

import com.teymurakh.iwblr.core.Camera;
import com.teymurakh.iwblr.geom.Vec;
import com.teymurakh.iwblr.util.Timer4;


public class Animation {
	private boolean repeating;
	private int numberOfFrames;
	private int frameDuration;
	private Graphic[] graphicArray;
	private Graphic currentFrame;
	private int currentFrameNumber;
	private Timer4 changeFrameTimer;
	
	public Animation(Graphic[] graphicArray, int frameDuration, boolean repeating) {
		this.graphicArray = graphicArray;
		this.numberOfFrames = graphicArray.length;
		this.frameDuration = frameDuration;
		this.changeFrameTimer = new Timer4(0, frameDuration);
		this.currentFrame = graphicArray[0];
		this.repeating = repeating;
	}
	
	public Animation(Graphic[] graphicArray) {
		this.graphicArray = graphicArray;
		this.numberOfFrames = graphicArray.length;
		this.frameDuration = 0;
		this.changeFrameTimer = new Timer4(0, frameDuration);
		this.currentFrame = graphicArray[0];
		this.repeating = false;
	}
	
	public Animation clone() {
		Graphic[] clonedArray = new Graphic[graphicArray.length];
		for (int i = 0; i < graphicArray.length; i++) {
			clonedArray[i] = graphicArray[i].clone();
		}
		
		Animation clonned = new Animation(clonedArray ,this.frameDuration, this.repeating);
		return clonned;
	}
	
	public void reset() {
		currentFrame = graphicArray[0];
		currentFrameNumber = 0;
		this.changeFrameTimer = new Timer4(0, frameDuration);
	}

	public void update(int delta) {
		if (changeFrameTimer.evaluate(delta)) {
			currentFrameNumber++;
			if (repeating) {
				if (currentFrameNumber >= numberOfFrames) {
					currentFrameNumber = 0;
				}
			} else {
				if (currentFrameNumber >= numberOfFrames) {
					currentFrameNumber = numberOfFrames - 1;
				}
			}
		}
		currentFrame = graphicArray[currentFrameNumber];
	}
	
	public void draw(Renderer worldRenderer, Vec position, Vec scale, Camera camera, float rotation, boolean flipHorizontal, boolean flipVertical) {
		currentFrame.draw(worldRenderer, position, scale, camera, rotation, flipHorizontal, flipVertical);
	}
	
	
	public boolean isRepeating() {
		return repeating;
	}

	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}
	
}
