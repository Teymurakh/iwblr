package com.teymurakh.iwblr.entities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.teymurakh.iwblr.core.Game;
import com.teymurakh.iwblr.core.World;
import com.teymurakh.iwblr.core.graphics.AnimationSet;
import com.teymurakh.iwblr.core.graphics.Drawable;
import com.teymurakh.iwblr.core.graphics.Renderer;
import com.teymurakh.iwblr.geom.Hitbox;
import com.teymurakh.iwblr.geom.HitboxFactory;
import com.teymurakh.iwblr.geom.Line;
import com.teymurakh.iwblr.geom.Point;
import com.teymurakh.iwblr.geom.Rectangle;
import com.teymurakh.iwblr.geom.Vec;
import com.teymurakh.iwblr.util.LuaVM;
import com.teymurakh.iwblr.util.MyColor;


public class Entity implements Drawable {
	
	public static String[] types = new String[]{	
	"ai",
	"block",
	"particle",
	"creature",
	"entity",
	"fake_block",
	"grenade",
	"guy",
	"healthpack",
	"homing_projectile",
	"jump_reset",
	"laser_beam",
	"moving_spike",
	"physical_spike",
	"pick_up",
	"projectile",
	"save_point",
	"spawner",
	"speed_boost",
	"spike",
	"spring",
	"static_spike",
	"terrain",
	"physical",
	"depth_collision",
	"movable",
	"platform",
	"platform_bouncy",
	"save"
	};
	
	
	public static long globalId = 0;
	
	// TAGS
	protected final List<String> typeTags;
	protected final List<String> collidesWith;
	
	// META
	protected long creationTime;
	protected String scriptName;
	protected long intId;

	// PHYSICAL
	protected final Vec dim;
	protected final Vec pos;
	protected final Vec vel;
	protected final Vec acc;
	protected float rotation;
	protected float rotationVel;
	protected float rotationAcc;
	
	
	// GRAPHICS
	protected String animationName;
	protected AnimationSet animation;
	protected final HashMap<String, AnimationSet> animations;
	
	protected MyColor debugColor;
	protected boolean flipHorizontal;
	protected boolean flipVertical;
	
	// GAME LOGIC
	protected World world;
	protected Entity parent;

	protected boolean affectedByGravity;
	protected Vec gravityAcceleration;
	

	protected boolean dead;
	
	// COLLISIONS
	protected String hitboxName;
	protected Hitbox javaHitbox;
	
	protected boolean rectangular;
	protected boolean preciseCollision;
	protected boolean platform;
	protected boolean grounded;
	protected Vec groundedVel;
	
	public Entity(String scriptName) {
		
		// TAGS
		typeTags = new ArrayList<String>();
		collidesWith = new ArrayList<String>();
		
		// META
		creationTime = 0;
		this.scriptName = scriptName;
		intId = globalId++;
		
		// PHYSICAL
		dim = new Vec(1f, 1f);
		pos = new Vec(0f, 0f);
		vel = new Vec(0f, 0f);
		acc = new Vec(0f, 0f);
		rotation = 0;
		rotationVel = 0;
		rotationAcc = 0;
		
		
		// GRAPHICS
		animations = new HashMap<String, AnimationSet>();
		animationName = "no_animation";
		animation = Game.animationFactory.getAnimation("no_animation");
		
		debugColor = new MyColor(MyColor.GREEN);
		flipHorizontal = false;
		flipVertical = false;
		
		// GAME LOGIC
		world = null;
		parent = null;
		
		affectedByGravity = false;
		gravityAcceleration = new Vec(0, 0);
		
		// COLLISIONS
		rectangular = true;
		groundedVel = new Vec(0, 0);
		setHitbox("rectangle");
		
		dim.set(new Vec(1f, 1f));
		
		createLua();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Lua Interface
	
	

	public boolean isFlying() {
		return Game.config.isFlyingEnabled();
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public void switchAnimation(String nextAnimation) {
		this.animation = animations.get(nextAnimation);
	}
	
	public void addAnimation(String key, String animationName) {
		animations.put(key, Game.animationFactory.getAnimation(animationName).clone());
	}
	
	public void addTag(String tag) {
		if (!typeTags.contains(tag)) typeTags.add(tag);
		//this.world.notifyTagAdded(tag, this);
	}
	
	public void removeTag(String tag) {
		typeTags.remove(tag);
		//this.world.notifyTagRemoved(tag, this);
	}
	
	public boolean hasTag(String type) {
		return typeTags.contains(type);
	}
	
	public void addCollision(String tag) {
		if (!collidesWith.contains(tag)) collidesWith.add(tag);
	}
	
	public void removeCollision(String tag) {
		collidesWith.remove(tag);
	}
	
	
	public float getDimX() {return dim.getX();}
	public void  setDimX(float x) {dim.setX(x);}
	
	public float getDimY() {return dim.getY();}
	public void  setDimY(float y) {dim.setY(y);}
	
	public float getPosX() {return pos.getX();}
	public void  setPosX(float x) {pos.setX(x);}
	
	public float getPosY() {return pos.getY();}
	public void  setPosY(float y) {pos.setY(y);}
	
	public float getPosX2() {return pos.getX() + dim.getX();}
	public float getPosY2() {return pos.getY() - dim.getY();}
	
	public float getVelX() {return vel.getX();}
	public void  setVelX(float x) {vel.setX(x);}
	
	public float getVelY() {return vel.getY();}
	public void  setVelY(float y) {vel.setY(y);}
	
	public float getAccX() {return acc.getX();}
	public void  setAccX(float x) {acc.setX(x);}
	
	public float getAccY() {return acc.getY();}
	public void  setAccY(float y) {acc.setY(y);}
	
	public float getRotation() {return rotation;}
	public void  setRotation(float rotation) {this.rotation = rotation;}
	
	public float getRotationVel() {return rotationVel;}
	public void  setRotationVel(float rotationVel) {this.rotationVel = rotationVel;}

	public float getRotationAcc() {return rotationAcc;}
	public void  setRotationAcc(float rotationAcc) {this.rotationAcc = rotationAcc;}
	
	public float getCenterX() {return pos.getX() + dim.getX()/2f;}
	public float getCenterY() {return pos.getY() - dim.getY()/2f;}
	
	public float getGravityAccX() {return gravityAcceleration.getX();}
	public float getGravityAccY() {return gravityAcceleration.getY();}
	
	public boolean isFlipHorizontal() {
		return flipHorizontal;
	}

	public void setFlipHorizontal(boolean flipHorizontal) {
		this.flipHorizontal = flipHorizontal;
	}

	public boolean isFlipVertical() {
		return flipVertical;
	}

	public void setFlipVertical(boolean flipVertical) {
		this.flipVertical = flipVertical;
	}
	
	
	public void setAnimation(String animationName) {
		this.animationName = animationName;
		this.animation = Game.animationFactory.getAnimation(animationName).clone();
	}
	
	public String getAnimation() {
		return animationName;
	}
	
	public long getIntId() { return intId; }
	
	public String getScriptName() {
		return scriptName;
	}
	
	public boolean isAffectedByGravity() {
		return affectedByGravity;
	}
	
	public void setAffectedByGravity(boolean bool) {
		this.affectedByGravity = bool;
	}

	public void save(float x, float y) {
		Game.save();
	}
	
	public void save() {
		Game.save();
	}
	
	public void destroy() {
		world.removeEntity(this);
	}
	
	public boolean isRectangular() {
		return rectangular;
	}
	
	public void setRectangular(boolean rectangular) {
		this.rectangular = rectangular;
	}
	
	public String getHitbox() {
		return hitboxName;
	}

	public void setHitbox(String hitboxName) {
		this.hitboxName = hitboxName;
		this.javaHitbox = HitboxFactory.create(hitboxName);
	}
	
	public boolean isPlatform() {
		return platform;
	}

	public void setPlatform(boolean platform) {
		this.platform = platform;
	}
	
	public void die() {
		setDead(true);
		destroy();
	}
	


	public void createEntity(Entity entity, float x, float y) {
		world.place(entity, new Vec(x, y));
	}	
	

	public void playSound(String name) {
		Game.soundHandler.play(name);;
	}	
	
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
	
	protected void createLua() {
		LuaEntityCapsule toPass = new LuaEntityCapsule(this);
		LuaVM.call("create", toPass, intId);
	}

	protected void updateLua(float delta) {
		LuaVM.call("update", intId, delta);
		animation.update((int)Math.round(delta*1000));
	}
	
	protected void deathLua() {
		LuaVM.call("death", intId);
	}
	
	protected void collidedLua(Entity entity, float direction) {
		LuaVM.call("collided", intId, entity.getIntId(), direction);
	}
	
	final public void update(float delta) {
		updateLua(delta);
		javaHitbox.updateAll(new Point(pos.getX(), pos.getY()), dim.getX(), dim.getY(), rotation);
	}
	
	public void cleanUp(){
		setDead(true);
		deathLua();
	}
	
	public void collidedWithDirection(Entity entity, float direction) {
		collidedLua(entity, direction);
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void notifyPlaced(World world) {
		this.world = world;
		this.gravityAcceleration = world.getGravityAcceleration();
		this.creationTime = world.getWorldTime();
	}
	
	public Rectangle getRect() {
		return new Rectangle(pos.getX(), pos.getY(), dim.getX(), dim.getY());
	}
	
	public boolean hasCollides(String type) {
		return collidesWith.contains(type);
	}

	/*
	public Line[] getJavaHitbox() {
	Line[] newHitbox;
		if (hitboxName.equals("rectangle")) {
			newHitbox = getRectBox();
		} else if (hitboxName.equals("spike")) {
			newHitbox = getSpikeBox();
		} else {
			newHitbox = getRectBox();
		}
		
		
	return newHitbox;
	}
	*/
	
	
	public float getX1() {
		return pos.getX();
	}
	
	public float getY1() {
		return pos.getY();
	}

	public float getX2() {
		return pos.getX() + dim.getX();
	}
	
	public float getY2() {
		return pos.getY() - dim.getY();
	}
	
	public void draw(Renderer worldRenderer) {
		drawDebug(worldRenderer);
		drawTexture(worldRenderer);
	}
	
	public void draw(Renderer worldRenderer, float x, float y) {
	}
	
	public void drawDebug(Renderer worldRenderer) {
		if (Game.config.isDrawDebug()) {
			float scale = Game.config.getScale();
			worldRenderer.fillRect(
				(float)((pos.getX() * scale) + Game.config.getScreenWidth() / 2 - world.getCamera().getPosition().getX() * scale),
				(float)((pos.getY() * scale) + Game.config.getScreenHeight() / 2 - world.getCamera().getPosition().getY() * scale),
				(float)(dim.getX() * scale),
				(float)(dim.getY() * scale),
				debugColor
			);
			
			Line[] lines = javaHitbox.getLines();
			
			for (int i = 0; i < lines.length; i++) {
			Line line = lines[i];
			
			worldRenderer.drawLine(
					(float)((line.start.x * scale) + Game.config.getScreenWidth() / 2 - world.getCamera().getPosition().getX() * scale),
					(float)((line.start.y * scale) + Game.config.getScreenHeight() / 2 - world.getCamera().getPosition().getY() * scale),
					(float)((line.end.x * scale) + Game.config.getScreenWidth() / 2 - world.getCamera().getPosition().getX() * scale),
					(float)((line.end.y * scale) + Game.config.getScreenHeight() / 2 - world.getCamera().getPosition().getY() * scale),
					0,
					new MyColor(1f, 0f, 0f, 1f)
			);
			}
			
			
		}
	}
	
	public void drawTexture(Renderer worldRenderer) {
		// TODO fix trying to draw null animations
		if (animation != null) {
			animation.draw(worldRenderer, pos, dim, world.getCamera(), rotation, flipHorizontal, flipVertical);
		}
	}
	
	public List<String> getCollidesWith() {
		return collidesWith;
	}
	
	public List<String> getTypeTags() {
		return typeTags;
	}
	
	public Vec getPos() {
		return pos;
	}
	
	public Vec getGravityAcceleration() {
		return gravityAcceleration;
	}

	public void setGravityAcceleration(Vec gravityAcceleration) {
		this.gravityAcceleration = gravityAcceleration;
	}
//////////////////////////////////////////////////////////////////////////////


	public Hitbox getJavaHitbox() {
		return javaHitbox;
	}

	public void setJavaHitbox(Hitbox hitbox2) {
		this.javaHitbox = hitbox2;
	}
	
//////////////////////////////////////////////////////////////////////////////
}
