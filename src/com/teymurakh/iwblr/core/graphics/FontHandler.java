package com.teymurakh.iwblr.core.graphics;

import java.awt.Color;
import java.awt.Font;
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

public class FontHandler {

	private final boolean debug = true;
	private final HashMap<String, MappedTexture> mappedTextures;
	
	private final int width = 1024;
	private final int height = 1024;	
	
	private MappedTexture missingTexture;
	
	public FontHandler() {
		mappedTextures = new HashMap<String, MappedTexture>();
	}
	
	public void load() {
		createFontTextures();
	}

	public MappedTexture getMappedTexture(String mappedTextureName) {
		MappedTexture mappedTexture;
		if (mappedTextures.containsKey(mappedTextureName)) {
			 mappedTexture = mappedTextures.get(mappedTextureName);
		}
		else {
			//System.out.println("SDSDS" + mappedTextureName);
			mappedTexture = missingTexture;
		}
		return mappedTexture;
	}
	
	public boolean containsTexture(String textureName) {
		return mappedTextures.containsKey(textureName);
	}
	
	private void createFontTextures() {
		
		ArrayList<String> characters = new ArrayList<String>();
		characters.add("a");
		characters.add("b");
		characters.add("c");
		characters.add("d");
		characters.add("e");
		characters.add("f");
		characters.add("g");
		characters.add("h");
		characters.add("i");
		characters.add("j");
		characters.add("k");
		characters.add("l");
		characters.add("m");
		characters.add("n");
		characters.add("o");
		characters.add("p");
		characters.add("q");
		characters.add("r");
		characters.add("s");
		characters.add("t");
		characters.add("u");
		characters.add("v");
		characters.add("w");
		characters.add("x");
		characters.add("y");
		characters.add("z");
		
		characters.add("A");
		characters.add("B");
		characters.add("C");
		characters.add("D");
		characters.add("E");
		characters.add("F");
		characters.add("G");
		characters.add("H");
		characters.add("I");
		characters.add("J");
		characters.add("K");
		characters.add("L");
		characters.add("M");
		characters.add("N");
		characters.add("O");
		characters.add("P");
		characters.add("Q");
		characters.add("R");
		characters.add("S");
		characters.add("T");
		characters.add("U");
		characters.add("V");
		characters.add("W");
		characters.add("X");
		characters.add("Y");
		characters.add("Z");
		
		characters.add("0");
		characters.add("1");
		characters.add("2");
		characters.add("3");
		characters.add("4");
		characters.add("5");
		characters.add("6");
		characters.add("7");
		characters.add("8");
		characters.add("9");
		

		characters.add("!");
		characters.add("\"");
		characters.add("#");
		characters.add("$");
		characters.add("%");
		characters.add("&");
		characters.add("'");
		characters.add("(");
		characters.add(")");
		characters.add("*");
		characters.add("+");
		characters.add(",");
		characters.add("-");
		characters.add(".");
		characters.add("/");
		characters.add(":");
		characters.add(";");
		characters.add("<");
		characters.add("=");
		characters.add(">");
		characters.add("?");
		characters.add("[");
		characters.add("\\");
		characters.add("]");
		characters.add("^");
		characters.add("_");
		characters.add("{");
		characters.add("|");
		characters.add("}");
		characters.add("~");
		
		
		Canvas canvas = new Canvas(width, height);
		
		int size = 8;
		
		for (String letter : characters) {
			BufferedImage characterImage = new BufferedImage(size*3, size*3*2, BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = characterImage.getGraphics();

			Font font = new Font(Font.MONOSPACED, Font.BOLD, size*4);

			graphics.setColor(new Color(255, 255, 255));
			graphics.setFont(font);
			graphics.drawString(letter, 0, size*4);


			if (!canvas.hasRoom(characterImage)) {
				System.out.println("Canvas ran out of room");
				canvas.process();
				canvas = new Canvas(width, height);
			}

			canvas.addImage(characterImage, letter);



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
				y += largestY + 1;
				largestY = 0;
			}
			
			graphics.drawImage(image, x, y, null);

			Region temp = new Region(x, y, imageWidth, imageHeight, name);
			regions.add(temp);

			x += imageWidth + 1;

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
			String path = "debug/gfx/letters_" + id + ".png";
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