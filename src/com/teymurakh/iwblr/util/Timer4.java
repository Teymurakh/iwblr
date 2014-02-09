package com.teymurakh.iwblr.util;

public class Timer4 {
	private int interval;
	private int timeLeft;
		
	public Timer4(int startDelay, int interval) {
		this.interval = interval;
		this.timeLeft = startDelay;
	}
		
	public boolean evaluate(int delta) {
		this.timeLeft -= delta;
		if(timeLeft <= 0) {
			reset();
			return true;
		}
		return false;
	}
	
	public void reset() {
		this.timeLeft = interval;
	}
}
