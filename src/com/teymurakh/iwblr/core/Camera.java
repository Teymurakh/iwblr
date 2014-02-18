package com.teymurakh.iwblr.core;

import com.teymurakh.iwblr.geom.Vec;

public class Camera {
	private Vec position;
	private boolean locked;
	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;
	private Vec velocity;
	
	public Camera(boolean locked, Vec position) {
		this.locked = locked;
		this.position = position;
		this.velocity = new Vec(0, 0);
	}
	
	public void update(float delta) {
		if (Game.editingMode) {
			velocity.setY(0);
			if (movingUp) {
				velocity.setY(10f);
			}
			if (movingDown) {
				velocity.setY(-10f);
			}

			velocity.setX(0);
			if (movingRight) {
				velocity.setX(velocity.getX() + 10f);
			}
			if (movingLeft) {
				velocity.setX(velocity.getX() - 10f);
			}
			updatePosition(delta);
		}
	}
	
	private void updatePosition(float delta) {
		position.setX(position.getX() + (velocity.getX() * delta));
		position.setY(position.getY() + (velocity.getY() * delta));
	}

	public Vec getPosition() {
		return position;
	}

	public void setPosition(Vec position) {
		this.position = position;
	}
	
	public boolean isLocked() {
		return this.locked;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}
	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}
}
