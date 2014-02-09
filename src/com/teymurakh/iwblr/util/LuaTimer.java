package com.teymurakh.iwblr.util;

public class LuaTimer {
	private String action;
	private int interval;
	private int timeLeft;
	private boolean active;
		
	public LuaTimer(String action, int startDelay, int interval) {
		this.action = action;
		this.active = true;
		this.interval = interval;
		this.timeLeft = startDelay;
	}

	public boolean update(int delta) {
		if (active) {
			this.timeLeft -= delta;
			if(timeLeft <= 0) {
				this.active = false;
				if (interval >= 0) {
					reset();
				}
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		this.timeLeft = interval;
		this.active = true;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
