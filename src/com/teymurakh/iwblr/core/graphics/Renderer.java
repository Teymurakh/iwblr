package com.teymurakh.iwblr.core.graphics;

import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.teymurakh.iwblr.core.Game;
import com.teymurakh.iwblr.core.World;
import com.teymurakh.iwblr.entities.Entity;
import com.teymurakh.iwblr.util.MyColor;

public class Renderer {
	private final FontHandler fontHandler;
	
	public boolean drawWorldLayer = true;
	public boolean drawMenuLayer = true;
	public boolean drawGuyLayer = true;
	public boolean drawGameOverLayer = true;
	
	public Collection<Entity> worldLayerArray = new ArrayList<Entity>();
	public Drawable[] menuLayerArray = new Drawable[0];
	public Drawable[] guyLayerArray = new Drawable[0];
	public Drawable[] gameOverLayerArray = new Drawable[0];
	
	public Renderer(FontHandler fontHandler) {
		this.fontHandler = fontHandler;
	}
	
	public void initialize() {
		
		int width = Game.config.getScreenWidth();
		int height = Game.config.getScreenHeight();
        
		GL11.glClearColor(160f/255f, 162f/255f, 25f/255f, 0.0f);          
        
        	// enable alpha blending
        	GL11.glEnable(GL11.GL_BLEND);
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glViewport(0,0,width,height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void renderLayers() {
		
		if (true) {

			// Clear The Screen And The Depth Buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			// R,G,B,A Set The Color To Blue One Time Only
			GL11.glColor3f(60f/255f, 100f/255f, 54f/255f);

			// draw quad
			GL11.glPushMatrix();
			GL11.glTranslatef(0, 0, 0);
			GL11.glRotatef(0, 0, 0, 1 ); // now rotate

			if (drawWorldLayer) {
				for(Drawable item : worldLayerArray) {
					if (item != null)
						item.draw(this);
				}
			}

			if (drawGuyLayer) {
				for(int i = 0; i < guyLayerArray.length; i++) {
					guyLayerArray[i].draw(this);
				}
			}

			if (drawGameOverLayer) {
				for(int i = 0; i < gameOverLayerArray.length; i++) {
					gameOverLayerArray[i].draw(this);
				}
			}

			if (drawMenuLayer) {
				for(int i = 0; i < menuLayerArray.length; i++) {
					menuLayerArray[i].draw(this);
				}
			}

			GL11.glPopMatrix();
		}
	}
	
	public void renderWorld(World world) {
	}

	public void drawTexture(MappedTexture mappedTexture, float x, float y, float width, float height, float rotation, boolean flipHorizontal, boolean flipVertical) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Color.white.bind();
		mappedTexture.getTexture().bind();

		GL11.glTranslatef(x+width/2f, y-height/2f, 0);
		GL11.glRotatef(rotation, 0, 0, 1 ); // now rotate
		
		
     	float width1 = width;
     	float height1 = height;
     	
		float textureWidth = mappedTexture.getTexture().getTextureWidth();
		float textureHeight =  mappedTexture.getTexture().getTextureHeight();
		
		float textureX1 = mappedTexture.getX()/textureWidth;
		float textureY1 = mappedTexture.getY()/textureHeight;
		float textureX2 = (mappedTexture.getX() + mappedTexture.getWidth())/textureWidth;
		float textureY2 = (mappedTexture.getY() + mappedTexture.getHeight())/textureHeight;
     	float textureX3 = mappedTexture.getX()/textureWidth + mappedTexture.getWidth()/textureWidth/2f;
     	float textureY3 = mappedTexture.getY()/textureHeight + mappedTexture.getHeight()/textureHeight/2f;
		
		
     	float triangleWidth = width1/2f;
     	float triangleHeight = height1/2f;

	    GL11.glEnable(GL11.GL_TEXTURE_2D);
	    GL11.glBegin(GL11.GL_TRIANGLES); 

     	if (!flipHorizontal) {
	        GL11.glTexCoord2f(textureX1, textureY2); 
	        GL11.glVertex2f(-triangleWidth, -triangleHeight); 
	        GL11.glTexCoord2f(textureX1, textureY1); 
	        GL11.glVertex2f(-triangleWidth, triangleHeight); 
	        GL11.glTexCoord2f(textureX3, textureY3); 
	        GL11.glVertex2f(0, 0); 
	        
	        
	        GL11.glTexCoord2f(textureX2, textureY1); 
	        GL11.glVertex2f(triangleWidth, triangleHeight);
	        GL11.glTexCoord2f(textureX1, textureY1); 
	        GL11.glVertex2f(-triangleWidth, triangleHeight); 
	        GL11.glTexCoord2f(textureX3, textureY3); 
	        GL11.glVertex2f(0, 0); 
	        
	        
	        GL11.glTexCoord2f(textureX2, textureY2); 
	        GL11.glVertex2f(triangleWidth, -triangleHeight); 
	        GL11.glTexCoord2f(textureX2, textureY1); 
	        GL11.glVertex2f(triangleWidth, triangleHeight); 
	        GL11.glTexCoord2f(textureX3, textureY3); 
	        GL11.glVertex2f(0, 0); 
	        
	        GL11.glTexCoord2f(textureX1, textureY2); 
	        GL11.glVertex2f(-triangleWidth, -triangleHeight);
	        GL11.glTexCoord2f(textureX2, textureY2); 
	        GL11.glVertex2f(triangleWidth, -triangleHeight); 
	        GL11.glTexCoord2f(textureX3, textureY3); 
	        GL11.glVertex2f(0, 0); 
     	} else if(flipHorizontal) {
     		
	        GL11.glTexCoord2f(textureX2, textureY2); 
	        GL11.glVertex2f(-triangleWidth, -triangleHeight); 
	        GL11.glTexCoord2f(textureX2, textureY1); 
	        GL11.glVertex2f(-triangleWidth, triangleHeight); 
	        GL11.glTexCoord2f(textureX3, textureY3); 
	        GL11.glVertex2f(0, 0);
	        
	        
	        GL11.glTexCoord2f(textureX1, textureY1); 
	        GL11.glVertex2f(triangleWidth, triangleHeight); 
	        GL11.glTexCoord2f(textureX2, textureY1); 
	        GL11.glVertex2f(-triangleWidth, triangleHeight); 
	        GL11.glTexCoord2f(textureX3, textureY3); 
	        GL11.glVertex2f(0, 0); 
	        
	        
	        GL11.glTexCoord2f(textureX1, textureY2); 
	        GL11.glVertex2f(triangleWidth, -triangleHeight); 
	        GL11.glTexCoord2f(textureX1, textureY1); 
	        GL11.glVertex2f(triangleWidth, triangleHeight); 
	        GL11.glTexCoord2f(textureX3, textureY3); 
	        GL11.glVertex2f(0, 0);
	       
	        
	        GL11.glTexCoord2f(textureX2, textureY2); 
	        GL11.glVertex2f(-triangleWidth, -triangleHeight);
	        GL11.glTexCoord2f(textureX1, textureY2); 
	        GL11.glVertex2f(triangleWidth, -triangleHeight);
	        GL11.glTexCoord2f(textureX3, textureY3); 
	        GL11.glVertex2f(0, 0); 
     		
     	}
        
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	public void drawString(float x, float y, int size, String string) {
		String[] splitString = string.split("");
		for (int i = 0; i < string.length(); i++) {
			String letter = splitString[i+1];
			if (fontHandler.containsTexture("" + letter)) {
				drawLetter(x + size*i, y, size, letter);
			}
		}
	}
	
	private void drawLetter(float x, float y, int size, String letter) {
		String textureMapName = "" + letter;
		MappedTexture mappedTexture = fontHandler.getMappedTexture(textureMapName);
		float width = (float)(size);
		float height = (float)(size*2);
		drawTexture(mappedTexture, x, y, width, height, 0, false, false);
	}
	
	public void drawLine(float x1, float y1, float x2, float y2, float rotationAngle, MyColor color) {
		
		float width = x2 - x1;
		float height = y2 - y1;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D); 
	    GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		
		GL11.glTranslatef(x1, y1, 0f);
		GL11.glRotatef(rotationAngle, 0, 0, 1 ); // now rotate
	    
	    GL11.glBegin(GL11.GL_LINE_STRIP);
	    GL11.glVertex2f(0f, 0f);
	    GL11.glVertex2f(width, height);
	    GL11.glEnd();
	    GL11.glPopMatrix();
	}
	
	public void drawRect(float x1, float y1, float width, float height, MyColor color, float rotationAngle) {
		float rectX1 = -width/2f;
		float rectY1 = height/2f;
		float rectX2 = width/2f;
		float rectY2 = -height/2f;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D); 
	    GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		
		GL11.glTranslatef(x1 - rectX1, y1 - rectY1, 0f);
		GL11.glRotatef(rotationAngle, 0, 0, 1 ); // now rotate
		
	    GL11.glBegin(GL11.GL_LINE_STRIP);
	    GL11.glVertex2f(rectX1, rectY1);
	    GL11.glVertex2f(rectX2, rectY1);
	    GL11.glVertex2f(rectX2, rectY2);
	    GL11.glVertex2f(rectX1, rectY2);
	    GL11.glVertex2f(rectX1, rectY1);
	    GL11.glEnd();		
		
	    GL11.glPopMatrix();		
	}
	
	public void fillRect(float x, float y, float width, float height, MyColor color) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);  
		GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		GL11.glBegin(GL11.GL_QUADS);
		
		float x1 = x;
		float y1 = y;
		float x2 = x + width;
		float y2 = y;
		float x3 = x + width;
		float y3 = y - height;
		float x4 = x;
		float y4 = y - height;
		
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x2, y2);
		GL11.glVertex2f(x3, y3);
		GL11.glVertex2f(x4, y4);
		GL11.glEnd();
	}
}