package com.teymurakh.iwblr.geom;

public class BlockHitboxSet extends RegularHitbox implements Hitbox{
	
	public BlockHitboxSet() {
		super(4, 4);
		
		for (int i = 0; i < numOfPoints; i++) {
			expanded[i] = new Point(0, 0);
			rotated[i] = new Point(0, 0);
			finished[i] = new Point(0, 0);
		}

		points[0] = new Point(-1, 1);
		points[1] = new Point(1, 1);
		points[2] = new Point(1, -1);
		points[3] = new Point(-1, -1);

		lines[0] = new Line(finished[0], finished[1]);
		lines[1] = new Line(finished[1], finished[2]);
		lines[2] = new Line(finished[2], finished[3]);
		lines[3] = new Line(finished[3], finished[0]);
	}
}


