package com.teymurakh.iwblr.util;

import com.teymurakh.iwblr.geom.Vec;

public class Movement {
	Vec speed;
	Vec acceleration;
	
	public Movement(Vec speed, Vec acceleration) {
		this.speed = speed;
		this.acceleration = acceleration;
	}
	
	public Movement(Vec speed) {
		this.speed = speed;
		this.acceleration = new Vec(0, 0);
	}
	
	public Movement() {
		this.speed = new Vec(0, 0);
		this.acceleration = new Vec(0, 0);
	}
	
	public Movement(float speed, float angle, Vec acceleration) {

		
		Vec vectorSpeed;
		vectorSpeed = componentToVector(speed, angle);
		
		this.acceleration = acceleration;
		this.speed = vectorSpeed;
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

	
	public static float angleBetween(Vec vector1, Vec vector2) {
		float xToYRatio = Math.abs(vector1.getY()-vector2.getY()) / Math.abs(vector1.getX()-vector2.getX());
		float angle = (float) Math.toDegrees(Math.atan(xToYRatio)); //+ ( (-scatter) + 2*Math.random()*scatter);

		float directionX = vector2.getX() - vector1.getX();
		float directionY = vector2.getY() - vector1.getY();
		float geographicAngle = 0;
		
		if (directionX > 0 && directionY > 0) {      // Quadrant 1
			geographicAngle = angle;
		}
		else if (directionX < 0 && directionY > 0) { // Quadrant 2
			geographicAngle = 180 - angle;
		}
		else if (directionX < 0 && directionY < 0) { // Quadrant 3
			geographicAngle = angle + 180;
		}
		else if (directionX > 0 && directionY < 0) { // Quadrant 4
			geographicAngle = 360 - angle;
		}
		
		return geographicAngle;
	}
	
	public static float movementAngle(Vec vector1) {
		float xToYRatio = Math.abs(vector1.getX()) /Math.abs(vector1.getY());
		float angle = (float) Math.toDegrees(Math.atan(xToYRatio)); //+ ( (-scatter) + 2*Math.random()*scatter);

		
		float directionX = vector1.getX();
		float directionY = vector1.getY();
		float geographicAngle = 0;
		
		if (directionX > 0 && directionY > 0) {
			geographicAngle = angle;
		}
		else if (directionX > 0 && directionY < 0) {
			geographicAngle = 180 - angle;
		}
		else if (directionX < 0 && directionY < 0) {
			geographicAngle = angle + 180;
		}
		else if (directionX < 0 && directionY > 0) {
			geographicAngle = 360 - angle;
		}
		
		return geographicAngle;
	}
	
	public static double scalarFromVector(Vec vector1) {
		return Math.sqrt(Math.pow(vector1.getX(), 2) + Math.pow(vector1.getY(), 2));
	}

	public Vec getSpeed() {
		return speed;
	}

	public void setSpeed(Vec speed) {
		this.speed = speed;
	}

	public Vec getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vec acceleration) {
		this.acceleration = acceleration;
	}
	
}
