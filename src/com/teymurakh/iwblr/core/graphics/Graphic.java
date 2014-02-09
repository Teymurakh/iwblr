package com.teymurakh.iwblr.core.graphics;

import com.teymurakh.iwblr.core.Camera;
import com.teymurakh.iwblr.core.Game;
import com.teymurakh.iwblr.geom.Vec;

public class Graphic {
	private String textureName;
	private MappedTexture mappedTexture;
	private float width;
	private float height;
	private float offsetX;
	private float offsetY;
	private float rotation;
	
	public Graphic(String textureName, float width, float height, float rotation, float offsetX, float offsetY) {
		setTextureName(textureName);
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.rotation = rotation;
	}
	
	public Graphic clone() {
		Graphic clonned = new Graphic(this.textureName, this.width, this.height, this.rotation, this.offsetX, this.offsetY);
		return clonned;
	}

	public void draw(Renderer worldRenderer, Vec position, Vec scale, Camera camera, float rotation, boolean flipHorizontal, boolean flipVertical) {
		float scaleCamera = Game.config.getScale();
		
		worldRenderer.drawTexture(
				mappedTexture,
				(float)(((position.getX() + offsetX) * scaleCamera) + Game.config.getScreenWidth() / 2 - camera.getPosition().getX() * scaleCamera),
				(float)(((position.getY() - offsetY) * scaleCamera) + Game.config.getScreenHeight() / 2 - camera.getPosition().getY() * scaleCamera),
				(float)(width * scale.getX() * scaleCamera),
				(float)(height * scale.getY() * scaleCamera),
				rotation,
				flipHorizontal,
				flipVertical
				);
	}
	
	public String getTextureName() {
		return textureName;
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
		this.mappedTexture = Game.textureHandler.getMappedTexture(textureName);
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public MappedTexture getMappedTexture() {
		return mappedTexture;
	}
	
}
