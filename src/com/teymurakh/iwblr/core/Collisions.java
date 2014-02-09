package com.teymurakh.iwblr.core;

import java.util.ArrayList;
import java.util.Collection;

import com.teymurakh.iwblr.entities.Entity;
import com.teymurakh.iwblr.entities.Guy;
import com.teymurakh.iwblr.geom.Hitbox;
import com.teymurakh.iwblr.geom.Line;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.geom.Vec;


public class Collisions {
	
	public Collisions() {
	}
	
	public void initialize() {
	}
	
	public void doCollisions(World world) {
		collisions20(world);
	}
	
	
	private void collisions20(World world) {
		Collection<Entity> allEntities = world.getActive();		
		for (Entity item1 : allEntities) {
			for (String entityTypeItem : item1.getCollidesWith()) {
				Collection<Entity> collisionEntities = world.getActiveTag(entityTypeItem);
				entityVsList(item1, collisionEntities);
			}
		}

		Guy guy = Game.world.getGuy();
		if (!guy.isDead()) { // Only do collisions with guy if he's not dead
			for (String item : guy.getCollidesWith()) {
				Collection<Entity> collisionEntities = world.getActiveTag(item);
				entityVsList(guy, collisionEntities);
			}
			
			for (Entity item1 : allEntities) {
				if (item1.hasCollides("guy")) {
					entityVsEntity(item1, guy);
				}
			}
		}
	}
	
	private void entityVsList(Entity entity, Collection<Entity> entityList) {
		
		
		if (entity.hasTag("depth_collision")) {
			Entity entity2 = getLeastDeep(entity, entityList);
			
			if (entity2 != null) {
				entityVsEntity(entity, entity2);
			}
			
		} else  {
			for (Entity item : entityList) {
				entityVsEntity(entity, item);
			}
		}
	}
	
	private void entityVsEntity(Entity entity1, Entity entity2) {
		if (entity1.isRectangular() && entity2.isRectangular()) { // Check if both entities use rectangular collision
			if (rectHitbox(entity1, entity2)) { // Check if two entities collide
				float direction = determineDirection(entity1, entity2); // Get the direction of the collision
				//System.out.println("checking vs guy: " + entity1.getScriptName());
				entity1.collidedWithDirection(entity2, direction); // Tell the entity that it has collided
			}
		} else {  // If one of the entities don't use rectangular collision
			if(lineHitbox(entity1, entity2)) { // Check if a collision of lines has happened 
				entity1.collidedWithDirection(entity2, -1); // Tell the entity that it has collided, giving -1 as unknown direction
			}
		}
	}
	
	private boolean rectHitbox(Entity entity1, Entity entity2) {
		return twoRectangles(entity1.getRect(), entity2.getRect());
	}
	
	private boolean lineHitbox(Entity entity1, Entity entity2) {
		Hitbox hitbox1 = entity1.getJavaHitbox();
		Hitbox hitbox2 = entity2.getJavaHitbox();
		
		boolean collided = hitbox1.collides(hitbox2); 
		return collided;
	}
	
	private float determineDirection(Entity entity1, Entity entity2) {
		float direction = -1;

		boolean top = entity1.getCenterY() - entity2.getCenterY() > 0;
		boolean right = entity1.getCenterX() - entity2.getCenterX() > 0;

		double depthY;
		if (top) depthY = entity2.getY1() - entity1.getY2();
		else     depthY = entity1.getY1() - entity2.getY2();

		double depthX;
		if (right) depthX = entity2.getX2() - entity1.getX1();
		else	   depthX =  entity1.getX2() - entity2.getX1();
		
		boolean horizontal = depthY > depthX;
		
		if (horizontal) {
			if (right) {
				direction = 180;
			} else {
				direction = 0;
			}
		} else {
			if (top) {
				direction = 270;
			} else {
				direction = 90;
			}
		}
		return direction;
	}
	
	
	/////////////////////////////////////////////////////////////////////
	//  Depth Collisions Begin Here
	/////////////////////////////////////////////////////////////////////

	private Entity getLeastDeep(Entity entity, Collection<Entity> entitiesArray) {

		Entity leastDepthEntity = null;
		for (int i = 0; i < 2; i++) {
			ArrayList<Entity> depthCollisionList = new ArrayList<Entity>();

			for (Entity item : entitiesArray) {
				if (entity.isRectangular() && item.isRectangular()) { // || twoLineArraysIntersect(entity.getJavaHitbox(), item.getJavaHitbox())) {
					if (twoRectangles(entity.getRect(), item.getRect())) {
						depthCollisionList.add(item);
					}
				} 
			}

			float lastDepth = Float.MAX_VALUE;

			for (Entity item : depthCollisionList) {
				float depthY = entity.getCenterY() - item.getCenterY();
				float depthX = entity.getCenterX() - item.getCenterX();
				float depth = Math.abs(depthX) + Math.abs(depthY);
				if (depth < lastDepth) {
					leastDepthEntity = item;
					lastDepth = depth;
				}
			}
		}

		return leastDepthEntity;
	}
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////
	//  STATIC METHODS
	/////////////////////////////////////////////////////////////////////
	

	public static boolean twoRectangles(Rectangle rect1, Rectangle rect2) {
		if ((rect2.getX1() < rect1.getX2() && rect2.getX2() > rect1.getX1() && rect2.getY1() > rect1.getY2() && rect2.getY2() < rect1.getY1()) || 
			(rect1.getX1() < rect2.getX2() && rect1.getX2() > rect2.getX1() && rect1.getY1() > rect2.getY2() && rect1.getY2() < rect2.getY1())) {
			return true;
		}
		
		return false;
	}
	
	
	
	
	// Credit : http://www.java-gaming.org/index.php?topic=22590.0
	public static boolean linesIntersect(Line line1, Line line2){

		double x1 = line1.start.x;
		double y1 = line1.start.y;
		double x2 = line1.end.x;
		double y2 = line1.end.y;

		double x3 = line2.start.x;
		double y3 = line2.start.y;
		double x4 = line2.end.x;
		double y4 = line2.end.y;

		// Return false if either of the lines have zero length
		if (x1 == x2 && y1 == y2 ||
				x3 == x4 && y3 == y4){
			return false;
		}
		// Fastest method, based on Franklin Antonio's "Faster Line Segment Intersection" topic "in Graphics Gems III" book (http://www.graphicsgems.org/)
		double ax = x2-x1;
		double ay = y2-y1;
		double bx = x3-x4;
		double by = y3-y4;
		double cx = x1-x3;
		double cy = y1-y3;

		double alphaNumerator = by*cx - bx*cy;
		double commonDenominator = ay*bx - ax*by;
		if (commonDenominator > 0){
			if (alphaNumerator < 0 || alphaNumerator > commonDenominator){
				return false;
			}
		}else if (commonDenominator < 0){
			if (alphaNumerator > 0 || alphaNumerator < commonDenominator){
				return false;
			}
		}
		double betaNumerator = ax*cy - ay*cx;
		if (commonDenominator > 0){
			if (betaNumerator < 0 || betaNumerator > commonDenominator){
				return false;
			}
		}else if (commonDenominator < 0){
			if (betaNumerator > 0 || betaNumerator < commonDenominator){
				return false;
			}
		}
		if (commonDenominator == 0){
			// This code wasn't in Franklin Antonio's method. It was added by Keith Woodward.
			// The lines are parallel.
			// Check if they're collinear.
			double y3LessY1 = y3-y1;
			double collinearityTestForP3 = x1*(y2-y3) + x2*(y3LessY1) + x3*(y1-y2);   // see http://mathworld.wolfram.com/Collinear.html
			// If p3 is collinear with p1 and p2 then p4 will also be collinear, since p1-p2 is parallel with p3-p4
			if (collinearityTestForP3 == 0){
				// The lines are collinear. Now check if they overlap.
				if (x1 >= x3 && x1 <= x4 || x1 <= x3 && x1 >= x4 ||
						x2 >= x3 && x2 <= x4 || x2 <= x3 && x2 >= x4 ||
						x3 >= x1 && x3 <= x2 || x3 <= x1 && x3 >= x2){
					if (y1 >= y3 && y1 <= y4 || y1 <= y3 && y1 >= y4 ||
							y2 >= y3 && y2 <= y4 || y2 <= y3 && y2 >= y4 ||
							y3 >= y1 && y3 <= y2 || y3 <= y1 && y3 >= y2){
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	
	
	public static boolean twoLineArraysIntersect(Line[] lines1, Line[] lines2) {
		for (int i = 0; i < lines1.length; i++) {
			for (int i2 = 0; i2 < lines2.length; i2++) {
				if (linesIntersect(lines2[i2], lines1[i])) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	
	
    public static boolean getCircleLineIntersectionPoint2(Vec pointA, Vec pointB, Vec center, float radius) {
        double baX = pointB.getX() - pointA.getX();
        double baY = pointB.getY() - pointA.getY();
        double caX = center.getX() - pointA.getX();
        double caY = center.getY() - pointA.getY();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return false;
        }
        return true;
    }
	
	
	
	
	
	
	
}
