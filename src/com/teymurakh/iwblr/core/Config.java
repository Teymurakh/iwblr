package com.teymurakh.iwblr.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
 
public class Config {
 
	private HashMap<String, String> configMap;
	
	private String configFilePath;
	
	private int screen_width;
	private int screen_height;
	private int fps_limit;
	private boolean write_load_log_to_console;
	private boolean write_input_to_console;
	private int scale;
	private boolean flying_enabled;
	private boolean camera_locked;
	private boolean draw_debug;
	private float gravity;
	private float first_jump_speed;
	private float second_jump_speed;
	private float maximum_falling_speed;
	private float maximum_flying_speed;
	private boolean draw_menu_debug;
	private boolean auto_restart;
	
	
	public void load() {
		
		//writeLoadLogToConsole = true;
		configFilePath = "settings.cfg";
		
		
		configMap = new HashMap<String, String>();
		
		configMap.put("screen_width", "800"); 
		configMap.put("screen_height", "600");
		configMap.put("fps_limit", "60");
		configMap.put("write_load_log_to_console", "true");
		configMap.put("write_input_to_console", "true");
		configMap.put("scale", "20");
		configMap.put("flying_enabled", "false");
		
		
		
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(configFilePath));
			while ((sCurrentLine = br.readLine()) != null) {
				
				String line = sCurrentLine.replaceAll("\\s","");
				if (!(line.startsWith("//") || line.equals(""))) {
					String[] splitLine = line.split("[=]");
					configMap.put(splitLine[0], splitLine[1]); 
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		screen_width = Integer.parseInt(configMap.get("screen_width"));
		screen_height = Integer.parseInt(configMap.get("screen_height"));
		fps_limit = Integer.parseInt(configMap.get("fps_limit"));
		write_load_log_to_console = configMap.get("write_load_log_to_console").equals("true");
		write_input_to_console = configMap.get("write_input_to_console").equals("true");
		scale = Integer.parseInt(configMap.get("scale"));
		flying_enabled = configMap.get("flying_enabled").equals("true");
		draw_debug = configMap.get("draw_debug").equals("true");
		draw_menu_debug = configMap.get("draw_menu_debug").equals("true");
		auto_restart = configMap.get("auto_restart").equals("true");
		gravity = Float.parseFloat(configMap.get("gravity"));
		first_jump_speed = Float.parseFloat(configMap.get("first_jump_speed"));
		second_jump_speed = Float.parseFloat(configMap.get("second_jump_speed"));
		maximum_falling_speed = Float.parseFloat(configMap.get("maximum_falling_speed"));
		maximum_flying_speed = Float.parseFloat(configMap.get("maximum_flying_speed"));
		
		configMap = null;
		if (outputLoadLog()) {
			Game.console.printLine("Config successfully loaded from: " + configFilePath);
		}
 
	}

	public int getScreenWidth() {
		return screen_width;
	}
	
	public int getScreenHeight() {
		return screen_height;
	}
	
	public int getFpsLimit() {
		return fps_limit;
	}
	
	public int getScale() {
		return scale;
	}
	
	public boolean outputLoadLog() {
		return write_load_log_to_console;
	}
	
	public boolean outputInput() {
		return write_input_to_console;
	}
	
	public boolean isFlyingEnabled() {
		return flying_enabled;
	}
	
	public void setFlyingEnabled(boolean flyingEnabled) {
		this.flying_enabled = flyingEnabled;
	}

	public boolean isCameraLocked() {
		return camera_locked;
	}

	public void setCameraLocked(boolean camera_locked) {
		this.camera_locked = camera_locked;
	}

	public boolean isDrawDebug() {
		return draw_debug;
	}
	
	public void setDrawDebug(boolean draw_debug) {
		this.draw_debug = draw_debug;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public float getFirstJumpSpeed() {
		return first_jump_speed;
	}

	public void setFirstJumpSpeed(float first_jump_speed) {
		this.first_jump_speed = first_jump_speed;
	}

	public float getSecondJumpSpeed() {
		return second_jump_speed;
	}

	public void setSecondJumpSpeed(float second_jump_speed) {
		this.second_jump_speed = second_jump_speed;
	}

	public float getMaximumFallingSpeed() {
		return maximum_falling_speed;
	}

	public void setMaximumFallingSpeed(float maximum_falling_speed) {
		this.maximum_falling_speed = maximum_falling_speed;
	}

	public float getMaximumFlyingSpeed() {
		return maximum_flying_speed;
	}

	public void setMaximumFlyingSpeed(float maximum_flying_speed) {
		this.maximum_flying_speed = maximum_flying_speed;
	}

	public boolean DrawMenuDebug() {
		return draw_menu_debug;
	}

	public void setDrawMenuDebug(boolean draw_menu_debug) {
		this.draw_menu_debug = draw_menu_debug;
	}

	public boolean autoRestart() {
		return auto_restart;
	}

	public void setAutoRestart(boolean auto_restart) {
		this.auto_restart = auto_restart;
	}
}