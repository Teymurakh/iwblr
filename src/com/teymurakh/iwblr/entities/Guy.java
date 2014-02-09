package com.teymurakh.iwblr.entities;

import com.teymurakh.iwblr.util.LuaVM;

public class Guy extends Entity {
	
	
	
	public Guy() {
		super("guy");
	}
	
	// Controls 
	public void jumpPressed() {
		LuaVM.call("call", intId, "jumpPressed");
	}
	
	public void jumpReleased() {
		LuaVM.call("call", intId, "jumpReleased");
	}

	public void setMovingUp(boolean movingUp) {
		LuaVM.call("call", intId, "setMovingUp", movingUp);
	}

	public void setMovingDown(boolean movingDown) {
		LuaVM.call("call", intId, "setMovingDown", movingDown);
	}
	
	public void setMovingLeft(boolean movingLeft) {
		LuaVM.call("call", intId, "setMovingLeft", movingLeft);
	}

	public void setMovingRight(boolean movingRight) {
		LuaVM.call("call", intId, "setMovingRight", movingRight);
	}
	
	public void equipSlot(int weaponSlot) {
		LuaVM.invoke("invoke", new Object[]{intId, "equip", weaponSlot});
	}
	
	public void mouseClick(float targetX, float targetY) {
		LuaVM.invoke("invoke", new Object[]{intId, "useWeapon", targetX, targetY});
	}
	
	public void useWeapon() {
		LuaVM.invoke("invoke", new Object[]{intId, "useWeapon"});
	}
/////////////////////////////////////////////////
}