package com.teymurakh.iwblr.geom;

public class Vec implements F2d {
	float x;
	float y;
	
	public Vec(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec(Vec toClone) {
		this.x = toClone.x;
		this.y = toClone.y;
	}
	
	public Vec(float speed, float angle, boolean angular) {
		if (angular) {
			Vec vectorSpeed = componentToVector(speed, angle);
			this.x = vectorSpeed.x;
			this.y = vectorSpeed.y;
		}
	}
	
	public void set(Vec vec) {
		this.x = vec.getX();
		this.y = vec.getY();
	}
	
	public void add(Vec toAdd) {
		this.x += toAdd.x;
		this.y += toAdd.y;
	}
	
	public void multi(Vec toMulti) {
		this.x *= toMulti.x;
		this.y *= toMulti.y;
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
	
	public void addX(float toAdd) {
		this.x = this.x + toAdd;
	}
	
	public void addY(float toAdd) {
		this.y = this.y + toAdd;
	}
	
	public static Vec addVectors(Vec vector1, Vec vector2) {
		Vec vector = new Vec(0, 0);
		vector.setX(vector1.getX() + vector2.getX());
		vector.setY(vector1.getY() + vector2.getY());
		return vector;
	}
	
	public static Vec componentToVector(float speed, float angle) {
		float speedX;
		float speedY;
		double formattedAngle;
		formattedAngle = angle;
	
		if (angle > 360) {
			formattedAngle = angle - Math.floor(angle / 360);
		}
		
		speedX = speed * (float)Math.cos(Math.toRadians(formattedAngle));
		speedY = speed * (float)Math.sin(Math.toRadians(formattedAngle));
		
		return new Vec(speedX, speedY);
	}
}
