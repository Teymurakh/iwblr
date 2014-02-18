package com.teymurakh.iwblr.core.graphics;

import org.newdawn.slick.opengl.Texture;

public class MappedTexture {

	
	private Texture texture;
	private float x;
	private float y;
	private float width;
	private float height;
	private float rotationAngle;
	private boolean mirrorHorizontal;
	private boolean mirrorVertical;
	
	
	public MappedTexture(float x, float y, float width, float height, Texture texture) {
		this.mirrorHorizontal = false;
		this.mirrorVertical = false;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.rotationAngle = 0f;
	}
	
	public MappedTexture(float x, float y, float width, float height, Texture texture, boolean mirrorHorizontal) {
		this.mirrorHorizontal = mirrorHorizontal;
		this.mirrorVertical = false;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.rotationAngle = 0f;
	}
	
	public MappedTexture(float x, float y, float width, float height, Texture texture, boolean mirrorHorizontal, boolean mirrorVertical) {
		this.mirrorHorizontal = mirrorHorizontal;
		this.mirrorVertical = mirrorVertical;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.rotationAngle = 0f;
	}
	
	public MappedTexture clone() {
		return new MappedTexture(x, y, width, height, texture, mirrorHorizontal, mirrorVertical);
	}
	
	
	public Texture getTexture() {
		return texture;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean isMirrorHorizantal() {
		return mirrorHorizontal;
	}

	public void setMirrorHorizantal(boolean mirrorHorizantal) {
		this.mirrorHorizontal = mirrorHorizantal;
	}

	public boolean isMirrorVertical() {
		return mirrorVertical;
	}

	public float getRotationAngle() {
		return rotationAngle;
	}

	public void setRotationAngle(float rotationAngle) {
		this.rotationAngle = rotationAngle;
	}
	
	
}
