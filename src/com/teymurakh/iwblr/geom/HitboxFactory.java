package com.teymurakh.iwblr.geom;

public class HitboxFactory {

	public static Hitbox create(String name) {
		Hitbox set;
		if (name.equals("rectangle")) {
			set = new BlockHitboxSet();
		} else if (name.equals("spike")) {
			set = new SpikeHitboxSet();
		} else {
			set = new BlockHitboxSet();
		}
		return set;
	}
}
