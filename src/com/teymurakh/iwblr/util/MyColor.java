package com.teymurakh.iwblr.util;

public class MyColor {
	static public int RED = 0;
	static public int GREEN = 1;
	static public int BLUE = 2;
	
	
	float red;
	float green;
	float blue;
	float alpha;
	
	public MyColor(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = 1.0f;
	}
	
	public MyColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public MyColor(int color) {
		if (color == MyColor.RED) {
			this.red = 1.0f;
			this.green = 0.1f;
			this.blue = 0.1f;
			this.alpha = 1.0f;
		}
		else if (color == MyColor.GREEN) {
			this.red = 0.1f;
			this.green = 1.0f;
			this.blue = 0.1f;
			this.alpha = 1.0f;
		}
		else if (color == MyColor.BLUE) {
			this.red = 0.1f;
			this.green = 0.1f;
			this.blue = 1.0f;
			this.alpha = 1.0f;
		}
	}

	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
}
