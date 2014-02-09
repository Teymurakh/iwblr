package com.teymurakh.iwblr.entities;

public class LuaEntityCapsule {
	private Entity e;

	public LuaEntityCapsule(Entity entity) {
		this.e = entity;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Add the specified tag to the entity. If the specified tag already exists, no changes will be made.
	 * @param tag
	 */
	public void addTag(String tag) {
		e.addTag(tag);
	}

	/** 
	 * Remove the specified tag from entity. If the specified tag does not exists, no changes will be made.
	 * @param tag
	 */
	public void removeTag(String tag) {
		e.removeTag(tag);
	}

	/**
	 * Returns true if the entity contains the specified tag.  
	 * @param 
	 * @return
	 */
	public boolean hasTag(String tag) {
		return e.hasTag(tag);
	}

	public void addCollision(String tag) {
		e.addCollision(tag);
	}

	public void removeCollision(String tag) {
		e.removeCollision(tag);
	}

	public float getDimX() {
		return e.getDimX();
	}

	public void setDimX(float x) {
		e.setDimX(x);
	}

	public float getDimY() {
		return e.getDimY();
	}

	public void setDimY(float y) {
		e.setDimY(y);
	}

	public float getPosX() {
		return e.getPosX();
	}

	public void setPosX(float x) {
		e.setPosX(x);
	}

	public float getPosY() {
		return e.getPosY();
	}
	

	public float getPosX2() {
		return e.getPosX2();
	}
	public float getPosY2() {
		return e.getPosY2();
	}

	public void setPosY(float y) {
		e.setPosY(y);
	}

	public float getVelX() {
		return e.getVelX();
	}

	public void setVelX(float x) {
		e.setVelX(x);
	}

	public float getVelY() {
		return e.getVelY();
	}

	public void setVelY(float y) {
		e.setVelY(y);
	}

	public float getAccX() {
		return e.getAccX();
	}

	public void setAccX(float x) {
		e.setAccX(x);
	}

	public float getAccY() {
		return e.getAccY();
	}

	public void setAccY(float y) {
		e.setAccY(y);
	}

	public float getRotation() {
		return e.getRotation();
	}

	public void setRotation(float rotation) {
		e.setRotation(rotation);
	}

	public float getRotationVel() {
		return e.getRotationVel();
	}

	public void setRotationVel(float rotationVel) {
		e.setRotationVel(rotationVel);
	}

	public float getRotationAcc() {
		return e.getRotationAcc();
	}

	public void setRotationAcc(float rotationAcc) {
		e.setRotationAcc(rotationAcc);
	}

	public float getCenterX() {
		return e.getCenterX();
	}

	public float getCenterY() {
		return e.getCenterY();
	}

	public float getGravityAccX() {
		return e.getGravityAccX();
	}

	public float getGravityAccY() {
		return e.getGravityAccY();
	}

	public void setAnimation(String animationName) {
		e.setAnimation(animationName);
	}

	public String getAnimation() {
		return e.getAnimation();
	}
			
	public long getIntId() {
		return e.getIntId();
	}

	public String getScriptName() {
		return e.getScriptName();
	}

	public boolean isAffectedByGravity() {
		return e.isAffectedByGravity();
	}

	public void setAffectedByGravity(boolean bool) {
		e.setAffectedByGravity(bool);
	}
	
	public void createEntity(LuaEntityCapsule entity, float x, float y) {
		e.createEntity(entity.e, x, y);
	}

	public void save(float x, float y) {
		e.save(x, y);
	}

	public void destroy() {
		e.destroy();
	}
	
	public boolean isRectangular() {
		return e.isRectangular();
	}
	
	public void setRectangular(boolean rectangular) {
		e.setRectangular(rectangular);
	}

	public String getHitbox() {
		return e.getHitbox();
	}

	public void setHitbox(String hitboxName) {
		e.setHitbox(hitboxName);
	}
	
	public boolean isPlatform() {
		return e.isPlatform();
	}

	public void setPlatform(boolean platform) {
		e.setPlatform(platform);
	}
	
	public void die() {
		e.die();
	}
	
	public boolean isFlipHorizontal() {
		return e.isFlipHorizontal();
	}

	public void setFlipHorizontal(boolean flipHorizontal) {
		e.setFlipHorizontal(flipHorizontal);
	}

	public boolean isFlipVertical() {
		return e.isFlipVertical();
	}

	public void setFlipVertical(boolean flipVertical) {
		e.setFlipVertical(flipVertical);
	}
	

	public boolean isFlying() {
		return e.isFlying();
	}
	
	public void switchAnimation(String nextAnimation) {
		e.switchAnimation(nextAnimation);;
	}
	
	public void addAnimation(String key, String animationName) {
		e.addAnimation(key, animationName);
	}
	
	public void save() {
		e.save();
	}
	
	public void playSound(String name) {
		e.playSound(name);;
	}	
	
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
