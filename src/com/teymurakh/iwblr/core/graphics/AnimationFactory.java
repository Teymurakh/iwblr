package com.teymurakh.iwblr.core.graphics;

import java.util.HashMap;
import java.util.Set;

import com.teymurakh.iwblr.core.Game;

public class AnimationFactory {
	private final HashMap<String, AnimationSet> animationMap;
	
	public AnimationFactory() {
		animationMap = new HashMap<String, AnimationSet>();
	}
	
	public void initialize() {
		generateGeneric();
		generateFromPresets();
	}
	
	private void generateGeneric() {
		final HashMap<String, MappedTexture> allMapped = Game.textureHandler.getAllMapped();
		final Set<String> keys = allMapped.keySet();
		
		for (final String key : keys) {
			animationMap.put(key, new AnimationSet(new Animation[]{new Animation(new Graphic[]{
					new Graphic(key, 1f, 1f, 0, 0, 0)
					})
			}, 1f, 1f));
		}
	}
	
	private void generateFromPresets() {
		animationMap.put("guy_idle", new AnimationSet(new Animation[]{new Animation(new Graphic[]{
				new Graphic("guy_idle_1", 1f, 1f, 0f, -10f/32f, -12f/32f), 
				new Graphic("guy_idle_2", 1f, 1f, 0f, -10f/32f, -12f/32f),
				new Graphic("guy_idle_3", 1f, 1f, 0f, -10f/32f, -12f/32f),
				new Graphic("guy_idle_4", 1f, 1f, 0f, -10f/32f, -12f/32f)}, 90, true)
		}, 0.3125f, 0.625f));
		
		animationMap.put("guy_walking", new AnimationSet(new Animation[]{new Animation(new Graphic[]{
				new Graphic("guy_walking_1", 1f, 1f, 0f, -10f/32f, -12f/32f), 
				new Graphic("guy_walking_2", 1f, 1f, 0f, -10f/32f, -12f/32f),
				new Graphic("guy_walking_3", 1f, 1f, 0f, -10f/32f, -12f/32f),
				new Graphic("guy_walking_4", 1f, 1f, 0f, -10f/32f, -12f/32f)}, 16, true)
		}, 0.3125f, 0.625f));
		
		animationMap.put("guy_jumping", new AnimationSet(new Animation[]{new Animation(new Graphic[]{
				new Graphic("guy_jumping_1", 1f, 1f, 0f, -10f/32f, -12f/32f), 
				new Graphic("guy_jumping_2", 1f, 1f, 0f, -10f/32f, -12f/32f)
				}, 80, true)
		}, 0.3125f, 0.625f));
		
		animationMap.put("guy_falling", new AnimationSet(new Animation[]{new Animation(new Graphic[]{
				new Graphic("guy_falling_1", 1f, 1f, 0f, -10f/32f, -12f/32f), 
				new Graphic("guy_falling_2", 1f, 1f, 0f, -10f/32f, -12f/32f)
				}, 80, true)
		}, 0.3125f, 0.625f));
		
		animationMap.put("no_animation", new AnimationSet(new Animation[]{new Animation(new Graphic[]{
				new Graphic("no_texture", 1f, 1f, 0, 0, 0)
				})
		}, 1f, 1f));
	}
	
	public AnimationSet getAnimation(String animationName) {
		AnimationSet animation;
		
		if (animationMap.containsKey(animationName)) {
			animation = animationMap.get(animationName);
		}
		else {
			animation = animationMap.get("no_animation");
		}
		return animation;
	}
}
