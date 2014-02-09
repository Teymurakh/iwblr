package com.teymurakh.iwblr.util;

public class Timer5 {
	private int interval;
	private int timeLeft;
	private boolean active;
		
	public Timer5(int startDelay, int interval) {
		active = true;
		this.interval = interval;
		this.timeLeft = startDelay;
	}

	public boolean evaluate(int delta) {
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

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
