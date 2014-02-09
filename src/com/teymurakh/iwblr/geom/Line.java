package com.teymurakh.iwblr.geom;

import com.teymurakh.iwblr.core.Collisions;

public class Line extends Shape {
	public final Point start;
	public final Point end;
	
	public Line(Point start, Point end) {
		this.start 	= start;
		this.end 	= end;
	}
	
	public boolean intersects(Line line) {
		return Collisions.linesIntersect(this, line);
	}
	
}
