package com.teymurakh.iwblr.gameInterface;

import java.util.ArrayList;

import com.teymurakh.iwblr.core.Game;
import com.teymurakh.iwblr.core.graphics.Drawable;
import com.teymurakh.iwblr.core.graphics.MappedTexture;
import com.teymurakh.iwblr.core.graphics.Renderer;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.util.MyColor;

public class InterfaceBlock implements Drawable {
	Rectangle rectangle;
	private MyColor color;
	private MappedTexture backgroundTexture;
	private ArrayList<Drawable> elementList;
	
	InterfaceBlock(Rectangle rectangle) {
		this.elementList = new ArrayList<Drawable>();
		this.rectangle = rectangle;
		this.color = new MyColor(MyColor.BLUE);
	}
	
	InterfaceBlock(Rectangle rectangle, MyColor color) {
		this.elementList = new ArrayList<Drawable>();
		this.rectangle = rectangle;
		this.color = color;
	}
	
	InterfaceBlock(Rectangle rectangle, MyColor color, MappedTexture mappedTexture) {
		this.elementList = new ArrayList<Drawable>();
		this.rectangle = rectangle;
		this.color = color;
		this.backgroundTexture = mappedTexture;
	}
	
	public void draw(Renderer worldRenderer) {
		if (Game.config.DrawMenuDebug()) {
			worldRenderer.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), color);
		}
		if (!(backgroundTexture == null)) {
			drawBackground(worldRenderer);
		}
		
		for (Drawable item: elementList) {
			item.draw(worldRenderer, rectangle.getX(), rectangle.getY());
		}
	}
	
	public void addElement(Drawable element) {
		elementList.add(element);
	}

	private void drawBackground(Renderer worldRenderer) {
		worldRenderer.drawTexture(backgroundTexture, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), 0, false, false);
	}
	
	public void draw(Renderer worldRenderer, float x, float y) {
		worldRenderer.fillRect(rectangle.getX() + x, rectangle.getY() + y, rectangle.getWidth(), rectangle.getHeight(), color);
		if (!(backgroundTexture == null)) {
			drawBackground(worldRenderer);
		}
	}
}
