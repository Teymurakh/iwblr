package com.teymurakh.iwblr.geom;

import com.teymurakh.iwblr.util.Logic;

public class Rectangle extends Shape {
	private float x;
	private float y;
	private float width;
	private float height;
	
	public Rectangle(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getCenterX() {
		return this.x + this.width / 2;
	}
	
	public float getCenterY() {
		return this.y + this.height / 2;
	}
	
	public float getX1() {
		return x;
	}
	
	public float getY1() {
		return y;
	}

	public float getX2() {
		return x + width;
	}
	
	public float getY2() {
		return y - height;
	}
	
	public boolean equals(Rectangle compare) {
		boolean roomChanged = (!Logic.equal(this.getX1(), compare.getX1()) ||
				!Logic.equal(this.getY1(), compare.getY1()) ||
				!Logic.equal(this.getWidth(), compare.getWidth()) ||
				!Logic.equal(this.getHeight(), compare.getHeight()));
		
		return roomChanged;
	}
	
	public boolean isInside(float x, float y) {
		boolean xInside = x >= this.x && x <= (this.x + this.width);
		boolean yInside = y >= (this.y - this.height) && y <= this.y;
		
		if (xInside && yInside) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
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

	public boolean collides(Shape shape) {
		// TODO Auto-generated method stub
		return false;
	}
	

	
}
