package com.teymurakh.iwblr.geom;

public interface Hitbox {

	public Line[] getLines();
	public void updateAll(Point coords, float width, float height, float angle);
	public boolean collides(Hitbox compare);
	
	public void dimChanged(float width, float height);
	public void rotationChanged(float rotation);
	public void posChanged(float x, float y);
}
