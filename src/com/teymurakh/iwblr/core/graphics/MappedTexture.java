package com.teymurakh.iwblr.core.graphics;

import org.newdawn.slick.opengl.Texture;

public class MappedTexture {

	
	private Texture texture;
	private float x;
	private float y;
	private float width;
	private float height;
	private float rotationAngle;
	private boolean mirrorHorizantal;
	private boolean mirrorVertical;
	
	
	public MappedTexture(float x, float y, float width, float height, Texture texture) {
		this.mirrorHorizantal = false;
		this.mirrorVertical = false;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.rotationAngle = 0f;
	}
	
	public MappedTexture(float x, float y, float width, float height, Texture texture, boolean mirrorHorizontal) {
		this.mirrorHorizantal = mirrorHorizontal;
		this.mirrorVertical = false;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.rotationAngle = 0f;
	}
	
	public MappedTexture(float x, float y, float width, float height, Texture texture, boolean mirrorHorizontal, boolean mirrorVertical) {
		this.mirrorHorizantal = mirrorHorizontal;
		this.mirrorVertical = mirrorVertical;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.rotationAngle = 0f;
	}
	
	public MappedTexture(MappedTexture toClone, float rotationAngle) {
		this.mirrorHorizantal = false;
		this.mirrorVertical = false;
		this.texture = toClone.texture;
		this.x = toClone.x;
		this.y = toClone.y;
		this.height = toClone.height;
		this.width = toClone.width;
		this.rotationAngle = rotationAngle;
	}
	
	public MappedTexture(MappedTexture toClone, float rotationAngle, boolean mirrorHorizontal) {
		this.mirrorHorizantal = mirrorHorizontal;
		this.texture = toClone.texture;
		this.x = toClone.x;
		this.y = toClone.y;
		this.height = toClone.height;
		this.width = toClone.width;
		this.rotationAngle = rotationAngle;
	}

	public MappedTexture(MappedTexture toClone, float rotationAngle, boolean mirrorHorizontal, boolean mirrorVertical) {
		this.mirrorHorizantal = mirrorHorizontal;
		this.mirrorVertical = mirrorVertical;
		this.texture = toClone.texture;
		this.x = toClone.x;
		this.y = toClone.y;
		this.height = toClone.height;
		this.width = toClone.width;
		this.rotationAngle = rotationAngle;
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
		return mirrorHorizantal;
	}

	public void setMirrorHorizantal(boolean mirrorHorizantal) {
		this.mirrorHorizantal = mirrorHorizantal;
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
