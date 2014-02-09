package com.teymurakh.iwblr.util;

import com.teymurakh.iwblr.geom.Point;
import com.teymurakh.iwblr.geom.Rectangle;

public class Logic {
	
	public static float getFloat(float f1, float f2) {
		float generated = f1 + (float)Math.random() * (f2- f1);
		return generated;
	}
	
	
	/**
	 * Checks if the string can be parsed as float
	 * @param any string
	 * @return true if the string contains a float number
	 */
	public static boolean containsNumber(String str) {
	    boolean result = false;
	    try {
	        Float.parseFloat(str);
	        result = true;
	    } catch (NullPointerException npe) {
	    	result = false;
	    } catch (NumberFormatException nfe) {
	    	result = false;
	    }
	    return result;
	}
	
	public static boolean equalsZeroFloat(float float1) {
		return Math.abs(float1) < 0.01f;
	}
	
	public static boolean twoRectangles(Rectangle rect1, Rectangle rect2) {
		boolean	condition1 = rect2.getX1() < rect1.getX2();
		boolean	condition2 = rect2.getX2() > rect1.getX1();
		boolean	condition3 = rect2.getY1() > rect1.getY2();
		boolean	condition4 = rect2.getY2() < rect1.getY1();
		
		boolean	condition5 = rect1.getX1() < rect2.getX2();
		boolean	condition6 = rect1.getX2() > rect2.getX1();
		boolean	condition7 = rect1.getY1() > rect2.getY2();
		boolean	condition8 = rect1.getY2() < rect2.getY1();
	
		if (condition1 && condition2 && condition3 && condition4) {
			return true;
		}
		
		if (condition5 && condition6 && condition7 && condition8) {
			return true;
		}
		
		return false;
	}
	
	// TODO FIX FLOAT COMPARING
	public static boolean equal(float float1, float float2) {
		if (Math.abs(float1 - float2) < 0.001f) {
			return true;
		}
		return false;
	}
	
	
	public static boolean circlesCollide(float x1, float y1, float r1, float x2, float y2, float r2) {
		float rSum = r1 + r2;
		float xDiff = x1 - x2;
		float yDiff = y1 - y2;
		return rSum * rSum > (xDiff * xDiff + yDiff * yDiff);
	}
	
	/*
	// Credit : http://www.java-gaming.org/index.php?topic=22590.0
	public static boolean linesIntersect(Line line1, Line line2){

		double x1 = line1.getX1();
		double y1 = line1.getY1();
		double x2 = line1.getX2();
		double y2 = line1.getY2();

		double x3 = line2.getX1();
		double y3 = line2.getY1();
		double x4 = line2.getX2();
		double y4 = line2.getY2();

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
				if (Logic.linesIntersect(lines2[i2], lines1[i])) {
					return true;
				}
			}
		}
		return false;
	}
*/
	
	
	
	
	
	
	
	public static float angleBetween(Point point1, Point point2) {
		float xToYRatio = Math.abs(point1.y-point2.y) / Math.abs(point1.x-point2.x);
		float angle = (float) Math.toDegrees(Math.atan(xToYRatio));

		float directionX = point2.x - point1.x;
		float directionY = point2.y - point1.y;
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
	
	
	public static float distanceBetween(Point point1, Point point2) {
		
		float x = Math.abs(point1.x - point2.x);
		float y = Math.abs(point1.y - point2.y);
		
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	} 
	
	public static Point componentToPoint(float speed, float angle) {
		float speedX;
		float speedY;
		double formattedAngle;
		formattedAngle = angle;
	
		//if (angle > 360) {
		//	formattedAngle = angle - Math.floor(angle / 360);
		//}
		
		speedX = speed * (float)Math.cos(Math.toRadians(formattedAngle));
		speedY = speed * (float)Math.sin(Math.toRadians(formattedAngle));
		
		return new Point(speedX, speedY);
	}
	
	
	
	
	
	/*
	public static boolean lolwhat() {
		float a = d.Dot( d ) ;
		float b = 2*f.Dot( d ) ;
		float c = f.Dot( f ) - r*r ;

		float discriminant = b*b-4*a*c;
		if( discriminant < 0 )
		{
			// no intersection
		}
		else
		{
			// ray didn't totally miss sphere,
			// so there is a solution to
			// the equation.

			discriminant = sqrt( discriminant );

			// either solution may be on or off the ray so need to test both
			// t1 is always the smaller value, because BOTH discriminant and
			// a are nonnegative.
			float t1 = (-b - discriminant)/(2*a);
			float t2 = (-b + discriminant)/(2*a);

			// 3x HIT cases:
			//          -o->             --|-->  |            |  --|->
			// Impale(t1 hit,t2 hit), Poke(t1 hit,t2>1), ExitWound(t1<0, t2 hit), 

			// 3x MISS cases:
			//       ->  o                     o ->              | -> |
			// FallShort (t1>1,t2>1), Past (t1<0,t2<0), CompletelyInside(t1<0, t2>1)

			if( t1 >= 0 && t1 <= 1 )
			{
				// t1 is the intersection, and it's closer than t2
				// (since t1 uses -b - discriminant)
				// Impale, Poke
				return true ;
			}

			// here t1 didn't intersect so we are either started
			// inside the sphere or completely past it
			if( t2 >= 0 && t2 <= 1 )
			{
				// ExitWound
				return true ;
			}

			// no intn: FallShort, Past, CompletelyInside
			return false ;
		}
	}
	*/
	
	
	
	
	

}
