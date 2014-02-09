package com.teymurakh.iwblr.geom;

import com.teymurakh.iwblr.util.Logic;

public class Point implements F2d{
	public float x;
	public float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point clone() {
		return new Point(x, y);
	}
	
	public void takeFrom(Point point) {
		this.x = point.x;
		this.y = point.y;
	}
	
	public void rotate(Point axisPoint, float extraAngle) {
		
		float angle = Logic.angleBetween(axisPoint, this);
		float distance = Logic.distanceBetween(axisPoint, this);
		
		float newAngle = angle + extraAngle;
		
		Point newPoint = Logic.componentToPoint(distance, newAngle);
		this.x = newPoint.x;
		this.y = newPoint.y;
	}

	@Override
	public float getX() {
		return this.x;
		
	}

	@Override
	public float getY() {
		return this.y;
		
	}

	@Override
	public void setX(float x) {
		this.x = x;
		
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}
}
