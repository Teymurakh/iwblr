package com.teymurakh.iwblr.core.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.teymurakh.iwblr.core.Game;

public class TextureHandler {

	private final boolean debug = true;
	
	private final ArrayList<File> files;
	private final HashMap<String, MappedTexture> mappedTextures;
	private final String path = "resources/graphics"; 
	
	private final int width = 1024;
	private final int height = 1024;	
	
	private MappedTexture missingTexture;
	
	public TextureHandler() {
		mappedTextures = new HashMap<String, MappedTexture>();
		files = new ArrayList<File>();
	}
	
	public void load() {
		loadFiles();
		processFiles();
		createMissingTexture();
	}

	public MappedTexture getMappedTexture(String mappedTextureName) {
		MappedTexture mappedTexture;
		if (mappedTextures.containsKey(mappedTextureName)) {
			 mappedTexture = mappedTextures.get(mappedTextureName);
		}
		else {
			mappedTexture = missingTexture;
		}
		return mappedTexture;
	}
	
	public boolean containsTexture(String textureName) {
		return mappedTextures.containsKey(textureName);
	}
	
	
	protected HashMap<String, MappedTexture> getAllMapped() {
		return mappedTextures;
	}
	
	private void createMissingTexture() {
		try {
			BufferedImage missing = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
			Graphics missingG = missing.getGraphics();
			
			missingG.setColor(new Color(255, 0, 255));
			missingG.fillRect(0, 0, 32, 32);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(missing, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			Texture texture = TextureLoader.getTexture("PNG", is);
			
			missingTexture = new MappedTexture(0, 0, 32, 32, texture);
			
		} catch (IOException e) {
			Game.console.error("Failed to generate a missing texture placeholder: " + e.getMessage());
		}	
	}
	
	private void loadFiles() {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 

		for (File image : listOfFiles) {
			if (image.isFile()) {
				files.add(image);
			}
		}
	}

	
	private void processFiles() {
		Canvas canvas = new Canvas(width, height);
		
		for (File file : files) {
			try {
				BufferedImage image = ImageIO.read(file);
				String imageName = file.getName().replaceAll(".png", "");
				
				if (!canvas.hasRoom(image)) {
					System.out.println("Canvas ran out of room");
					canvas.process();
					canvas = new Canvas(width, height);
				}
				
				canvas.addImage(image, imageName);
				 
				
				
			} catch (IOException e) {
				Game.console.error("Failed to read file as an image: " + file.getName());
			}
		}

		canvas.process();
		
		
		
	}
	
	
	
	private static int lastId;
	
	
	private class Region {
		private final float x;
		private final float y;
		private final float width;
		private final float height;
		private final String name;
		
		public Region(float x, float y, float width, float height, String name) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.name = name;
		}
		
	}
	
	private class Canvas {
		private final int extraSpace = 2;
		private final int id;
		
		private int x;
		private int y;
		private int largestY;
		
		private final int width;
		private final int height;
		private final BufferedImage asImage;
		private final Graphics graphics;
		
		private final ArrayList<Region> regions;
		
		private Canvas(int width, int height) {
			this.width = width;
			this.height = height;
			this.id = lastId++;

			regions = new ArrayList<Region>();

			asImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			graphics = asImage.getGraphics();
		}
		
		public void addImage(BufferedImage image, String name) {
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();

			if(x + imageWidth > width){
				x = 0;
				y += largestY + extraSpace;
				largestY = 0;
			}
			
			graphics.drawImage(image, x, y, null);

			Region temp = new Region(x, y, imageWidth, imageHeight, name);
			regions.add(temp);

			x += imageWidth + extraSpace;

			if (imageHeight > largestY) {
				largestY = imageHeight;
			}

			/*
			if(x > width){
				x = 0;
				y += largestY;
			}
			*/
		}
		
		public boolean hasRoom(BufferedImage image) {
			int imageHeight = image.getHeight();
			
			
	        return !(this.y + imageHeight > this.height);
		}
		
		public void process() {
			String path = "debug/gfx/texture_" + id + ".png";
			if (debug) {
				try {
					ImageIO.write(asImage, "png", new File(path));
				} catch (IOException e) {
					Game.console.error("Failed to save debug texture image to: " + path);
				}
			}
			

			Texture texture = generateTexture();
			for (Region template : regions) {
				MappedTexture mapped = new MappedTexture(template.x, template.y, template.width, template.height, texture);
				mappedTextures.put(template.name, mapped);
			}
		}
		
		private Texture generateTexture() {
			Texture texture = null;
			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(asImage, "png", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				texture = TextureLoader.getTexture("PNG", is);
			} catch (IOException e) {
				Game.console.error("Failed to convert ImageBuffer to Texture");
			}
			
			return texture;
		}
		
	}
}
