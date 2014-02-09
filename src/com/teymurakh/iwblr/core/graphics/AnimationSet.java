package com.teymurakh.iwblr.core.graphics;

import com.teymurakh.iwblr.core.Camera;
import com.teymurakh.iwblr.geom.Vec;

public class AnimationSet {
	private Animation[] animations;
	private float designedWidth;
	private float designedHeight;
	
	public AnimationSet(Animation[] animations, float designedWidth, float designedHeight) {
		this.animations = animations;
		this.designedWidth = designedWidth;
		this.designedHeight = designedHeight;
	}
	
	public AnimationSet clone() {
		Animation[] clonedArray = new Animation[animations.length];
		for (int i = 0; i < animations.length; i++) {
			clonedArray[i] = animations[i].clone();
		}
		
		AnimationSet cloned = new AnimationSet(clonedArray, designedWidth, designedHeight);
		return cloned;
	}
	
	public void update(int delta) {
		for(int i = 0; i < animations.length; i++) {
			animations[i].update(delta);
		}
	}
	
	public void draw(Renderer worldRenderer, Vec position, Vec d, Camera camera, float rotation, boolean flipHorizontal, boolean flipVertical) {
		Vec scale = new Vec(d.getX()/designedWidth, d.getY()/designedHeight);
		
		for(int i = 0; i < animations.length; i++) {
			animations[i].draw(worldRenderer, position, scale, camera, rotation, flipHorizontal, flipVertical);
		}
	}
	
	public void reset() {
		for(int i = 0; i < animations.length; i++) {
			animations[i].reset();;
		}
	}
	
}