package com.teymurakh.iwblr.geom;

public class RegularHitbox {
	protected final Point[] points;
	protected final Point[] expanded;
	protected final Point[] rotated;
	protected final Point[] finished;
	protected final Line[] lines;
	protected int numOfPoints;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float rotation;
	
	public RegularHitbox(int numOfPoints, int numOfLines) {
		this.numOfPoints = numOfPoints;
		
		this.points = new Point[numOfPoints];
		this.expanded = new Point[numOfPoints];		
		this.rotated = new Point[numOfPoints];
		this.finished = new Point[numOfPoints];	
		
		this.lines = new Line[numOfLines];
	}
	

	public Line[] getLines() {
		return lines;
	}
	
	
	public boolean collides(Hitbox compare) {
		for (Line item1 : this.lines) {
			for (Line item2 : compare.getLines()) {
				if (item1.intersects(item2)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void updateAll(Point coords, float width, float height, float angle) {

		this.x = coords.x;
		this.y = coords.y;
		this.width = width;
		this.height = height;
		this.rotation = angle;
		
		dimChanged(width, height);
	}
	
	public void dimChanged(float width, float height) {
		this.width = width;
		this.height = height;

		for (int i = 0; i < numOfPoints; i++) {
			expanded[i].takeFrom(points[i]);
			expand(expanded[i], height, width);
		}
		
		rotationChanged(rotation);
	}

	public void rotationChanged(float rotation) {
		this.rotation = rotation;

		for (int i = 0; i < numOfPoints; i++) {
			rotated[i].takeFrom(expanded[i]);
			rotate(rotated[i], rotation);
		}
		
		posChanged(x, y);
	}

	public void posChanged(float x, float y) {
		this.x = x;
		this.y = y;

		for (int i = 0; i < numOfPoints; i++) {
			
			finished[i].takeFrom(rotated[i]);
			translate(finished[i], x, y, height, width);
		}
	}
	
	protected static void expand(Point point, float height, float width) {
		point.x = (point.x*width/2f);
		point.y = (point.y*height/2f);
	}
	
	protected static void rotate(Point point, float angle) {
		point.rotate(new Point(0, 0), angle);
	}
	
	protected static void translate(Point point, float x, float y, float height, float width) {
		point.x = (point.x + x + width/2f);
		point.y = (point.y + y - height/2f);
	}
}
