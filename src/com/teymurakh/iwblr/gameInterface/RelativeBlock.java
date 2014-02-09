package com.teymurakh.iwblr.gameInterface;


import com.teymurakh.iwblr.core.Game;
import com.teymurakh.iwblr.core.graphics.Drawable;
import com.teymurakh.iwblr.core.graphics.MappedTexture;
import com.teymurakh.iwblr.core.graphics.Renderer;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.util.MyColor;

public class RelativeBlock implements Drawable {
	float x;
	float y;
	float relativeX;
	float relativeY;
	float width;
	float height;
	private MyColor color;
	private MappedTexture backgroundTexture;
	private String textLine;
	
	RelativeBlock(float relativeX, float relativeY, float width, float height) {
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.width = width;
		this.height = height;
		this.color = new MyColor(MyColor.BLUE);
		this.textLine = "";
	}
	
	RelativeBlock(float relativeX, float relativeY, float width, float height, MyColor color) {
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.width = width;
		this.height = height;
		this.color = color;
		this.textLine = "";
	}
	
	RelativeBlock(float relativeX, float relativeY, float width, float height, MyColor color, MappedTexture mappedTexture) {
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.width = width;
		this.height = height;
		this.color = color;
		this.backgroundTexture = mappedTexture;
		this.textLine = "";
	}
	
	RelativeBlock(float relativeX, float relativeY, float width, float height, MyColor color, String textLine) {
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.width = width;
		this.height = height;
		this.color = color;
		this.textLine = textLine;
	}
	
	public void draw(Renderer worldRenderer, float x, float y) {
		this.x = x + this.relativeX;
		this.y = y + this.relativeY;
		if (Game.config.DrawMenuDebug()) {
			worldRenderer.fillRect(this.x, this.y, width, height, color);
		}
		if (!(backgroundTexture == null)) {
			drawBackground(worldRenderer);
		}
		if (!(textLine == "")) {
			worldRenderer.drawString(this.x, this.y, 20, textLine);
		}
	}
	
	private void drawBackground(Renderer worldRenderer) {
		worldRenderer.drawTexture(backgroundTexture, x, y, width, height, 0, false, false);
	}

	@Override
	public void draw(Renderer renderer) {
		// TODO Auto-generated method stub
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

	public String getTextLine() {
		return textLine;
	}

	public void setTextLine(String textLine) {
		this.textLine = textLine;
	}
}
