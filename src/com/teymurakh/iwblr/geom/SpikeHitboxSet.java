package com.teymurakh.iwblr.geom;

public class SpikeHitboxSet extends RegularHitbox implements Hitbox{
	
	public SpikeHitboxSet() {
		super(3, 3);
		
		for (int i = 0; i < numOfPoints; i++) {
			expanded[i] = new Point(0, 0);
			rotated[i] = new Point(0, 0);
			finished[i] = new Point(0, 0);
		}

		points[0] = new Point(-1, 0.99f);
		points[1] = new Point(1, 0);
		points[2] = new Point(-1, -0.99f);

		lines[0] = new Line(finished[0], finished[1]);
		lines[1] = new Line(finished[1], finished[2]);
		lines[2] = new Line(finished[2], finished[0]);
	}
}

