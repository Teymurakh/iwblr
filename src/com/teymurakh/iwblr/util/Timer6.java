package com.teymurakh.iwblr.util;

public class Timer6 {
	private float interval;
	private float timeLeft;
	private boolean active;
		
	public Timer6(float startDelay, float interval) {
		active = true;
		this.interval = interval;
		this.timeLeft = startDelay;
	}

	public boolean evaluate(float delta) {
		if (active) {
			this.timeLeft -= delta;
			if(timeLeft <= 0) {
				reset();
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		this.timeLeft = interval;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
