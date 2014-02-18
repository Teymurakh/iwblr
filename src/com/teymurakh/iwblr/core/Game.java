package com.teymurakh.iwblr.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.teymurakh.iwblr.core.graphics.AnimationFactory;
import com.teymurakh.iwblr.core.graphics.Drawable;
import com.teymurakh.iwblr.core.graphics.FontHandler;
import com.teymurakh.iwblr.core.graphics.Renderer;
import com.teymurakh.iwblr.core.graphics.TextureHandler;
import com.teymurakh.iwblr.gameInterface.Menu;
import com.teymurakh.iwblr.util.LuaVM;
import com.teymurakh.iwblr.util.Timer4;

public class Game {
	
	public static boolean editingMode = false;
	
	public static final String VERSION = "1.5";
	
	private Timer4 titleUpdateTimer = new Timer4(0, 100);
	
	public static Config config;
	public static Console console;
	
	
	private InputHandler inputHandler;
	private Menu menu;
	private Renderer worldRenderer;
	private TextureHandler textureHandler;
	private FontHandler fontHandler;
	
	public static AnimationFactory animationFactory;
	public static Collisions collisions;
	public static SoundHandler soundHandler;
	public static WorldEditor worldEditor;
	

	protected static World world;

	private static WorldUpdateThread thread1;
	
	
	/** Creates a game and initializes it */
	public static void main(String[] argv) {
		Game game = new Game();
		game.initialize();
	}
	
	/** Creates a display and initializes all static fields of the game*/
	private void initialize() {
		
		config				= new Config();
		console 			= new Console();
		
		
		inputHandler 		= new InputHandler();
		
		
		textureHandler 		= new TextureHandler();
		fontHandler 		= new FontHandler();
		
		animationFactory	= new AnimationFactory(textureHandler);
		menu 				= new Menu(textureHandler);
		worldRenderer 		= new Renderer(fontHandler);
		collisions 			= new Collisions();
		soundHandler 		= new SoundHandler();
		worldEditor 		= new WorldEditor();
		
		
		
		
		
		LuaVM.loadVm();
		
		
		config.load(); // Has to be initialized before creating a display to get dimensions
		
		try {
			Display.setDisplayMode(new DisplayMode(config.getScreenWidth(), config.getScreenHeight()));
			//Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		textureHandler.load();
		fontHandler.load();
		animationFactory.initialize();
		
		inputHandler.initialize();
		worldRenderer.initialize();
		soundHandler.initialize();
		worldEditor.initialize();
		collisions.initialize();
		menu.initialize();
		
		
		getDelta(); 

		while (!Display.isCloseRequested()) {
			updateLoop();
		}
		end();
	}
	
	/** Main game loop. It is responsible for updating the game, updating the input and rendering the game */
	private void updateLoop() {
		inputHandler.update(this);
		int delta = getDelta();
		
		if (world != null) {

			if (editingMode) {
				worldEditor.update();
				world.nonEntityUpdate();
			} else if (world.isPaused()) {
				world.nonEntityUpdate();
			} else {
				world.update();
			}

			if (world.isPaused()) {
				menu.update(delta, worldRenderer);
			}

			worldRenderer.drawWorldLayer = true;
			worldRenderer.drawMenuLayer = world.isPaused();
			worldRenderer.drawGuyLayer = !world.getGuy().isDead();
			worldRenderer.drawGameOverLayer = world.getGuy().isDead();
			
			worldRenderer.worldLayerArray = world.getActive();
			worldRenderer.guyLayerArray = new Drawable[]{world.getGuy()};
		}
		
		worldRenderer.menuLayerArray = menu.getElementList().toArray(new Drawable[menu.getElementList().size()]);
		worldRenderer.gameOverLayerArray = menu.getGameOverScreen().toArray(new Drawable[menu.getGameOverScreen().size()]);
		// end
		
		worldRenderer.renderLayers();
		
		if (editingMode) {
			worldEditor.draw(worldRenderer);
		}
		
		updateTitle(delta);
		Display.update();
		Display.sync(144);
		//Display.sync(config.getFpsLimit()); // cap fps to 60fps
	}
	
	/** Asks all entities to clean up, stops all running threads, and finally destroys the display*/
	private void end() {
		Game.console.printLine("Requesting thread1 " + thread1 + " to stop");
		if (thread1 != null) {
			thread1.requestStop();
		}
		soundHandler.destroy();
		Display.destroy();
	}
	

	////////////////////////////////////////////////////////// GAME COMMANDS ///////////////////////////////////////////////////////////
	public void pause() {
		world.pause();
	}
	
	public void unPause() {
		world.unPause();
	}
	
	public boolean isPaused() {
		if (world != null) {
			return world.isPaused();
		}
		return true;
	}
	
	public void newGame() {
		world.respawnGuy();
		loadLevel(world.getName());
		
		inputHandler.reset(Keyboard.KEY_R);
	}
	
	public void loadLevel(String levelName) {
		if (thread1 != null) {
			thread1.requestStop();
		}
		
		world = new World();
		world.initialize();
		
		
		inputHandler.reset(Keyboard.KEY_R);
		
		thread1 = new WorldUpdateThread(world);
		thread1.setLevelToBeLoaded(levelName);
		world.loadLevel(levelName);
		//thread1.start();
	}
	
	public void saveLevel(String levelName) {
		world.saveLevel(levelName);
	}
	
	public static void save() {
		world.saveState();
	}
	
	
	
	////////////////////////////////////////////////////////// INPUT ///////////////////////////////////////////////////////////
	protected void keyDown(int key) {
		if (editingMode) {
			
			if (key == Keyboard.KEY_P) 
				editingMode = false;
			
			if (key == Keyboard.KEY_ESCAPE) {
				if (isPaused()) unPause();
				else pause();
			}

			if (key == Keyboard.KEY_F) {
				if (config.isFlyingEnabled()) config.setFlyingEnabled(false); 
				else config.setFlyingEnabled(true);
			}
			
			if (key == Keyboard.KEY_G) 
				//Game.worldEditor.decreaseWidthModifier();
			
			if (key == Keyboard.KEY_H) 
				//Game.worldEditor.increaseWidthModifier();
			
			if (key == Keyboard.KEY_B) 
				//Game.worldEditor.decreaseHeightModifier();
			
			if (key == Keyboard.KEY_N) 
				//Game.worldEditor.increaseHeightModifier();
			
			if (key == Keyboard.KEY_R) 
				newGame();
			
			if (key == Keyboard.KEY_Q) 
				worldEditor.decreaseXGrid();
			
			if (key == Keyboard.KEY_W) 
				worldEditor.increaseXGrid();
			
			if (key == Keyboard.KEY_E) 
				worldEditor.increaseYGrid();
			
			if (key == Keyboard.KEY_D) 
				worldEditor.decreaseYGrid();
			
			if (key == Keyboard.KEY_T) 
				worldEditor.previousObject();
			
			if (key == Keyboard.KEY_Y) 
				worldEditor.nextObject();
			
			if (key == Keyboard.KEY_UP) 
				world.getCamera().setMovingUp(true);
			
			if (key == Keyboard.KEY_DOWN) 
				world.getCamera().setMovingDown(true);
			
			if (key == Keyboard.KEY_LEFT) 
				world.getCamera().setMovingLeft(true);
			
			if (key == Keyboard.KEY_RIGHT) 
				world.getCamera().setMovingRight(true);
			
		} else {
			
			if (key == Keyboard.KEY_P) 
				editingMode = true;
			
			if (key == Keyboard.KEY_R) 
				newGame();
			
			if (key == Keyboard.KEY_ESCAPE) {
				if (isPaused()) unPause();
				else pause();
			}
			
			if (key == Keyboard.KEY_F) {
				if (config.isFlyingEnabled()) config.setFlyingEnabled(false);
				else config.setFlyingEnabled(true);
			}
			
			if (key == Keyboard.KEY_D) {
				if (config.isDrawDebug()) config.setDrawDebug(false);
				else config.setDrawDebug(true);
			}
			
			//////////////////////////////////// Guy Controls //////////////////////////////////////////////			
			if (key == Keyboard.KEY_UP) 
				world.getGuy().setMovingUp(true);
			
			if (key == Keyboard.KEY_DOWN) 
				world.getGuy().setMovingDown(true);
			
			if (key == Keyboard.KEY_LEFT) 
				world.getGuy().setMovingLeft(true);
			
			if (key == Keyboard.KEY_RIGHT) 
				world.getGuy().setMovingRight(true);
			
			if (key == Keyboard.KEY_W) 
				world.getCamera().setMovingUp(true);
			
			if (key == Keyboard.KEY_S) 
				world.getCamera().setMovingDown(true);
			
			if (key == Keyboard.KEY_A) 
				world.getCamera().setMovingLeft(true);
			
			if (key == Keyboard.KEY_D) 
				world.getCamera().setMovingRight(true);
			
			if (key == Keyboard.KEY_X) 
				world.getGuy().useWeapon();

			if (key == Keyboard.KEY_LSHIFT) 
				world.getGuy().jumpPressed();
			
			if (key == Keyboard.KEY_1) 
				world.getGuy().equipSlot(1);
			
			if (key == Keyboard.KEY_2) 
				world.getGuy().equipSlot(2);
			
			if (key == Keyboard.KEY_3) 
				world.getGuy().equipSlot(3);
			
			if (key == Keyboard.KEY_4) 
				world.getGuy().equipSlot(4);
			
			if (key == Keyboard.KEY_5) 
				world.getGuy().equipSlot(5);
				
			if (key == Keyboard.KEY_6) 
				world.getGuy().equipSlot(6);
			/////////////////////////////////////////////////////////////////////////////////////////
		}
	}
	
	protected void keyUp(int key) {
		
		if (editingMode) {
			if (key == Keyboard.KEY_UP) 
				world.getCamera().setMovingUp(false);
			
			if (key == Keyboard.KEY_DOWN) 
				world.getCamera().setMovingDown(false);
			
			if (key == Keyboard.KEY_LEFT) 
				world.getCamera().setMovingLeft(false);
			
			if (key == Keyboard.KEY_RIGHT) 
				world.getCamera().setMovingRight(false);
			
		} else {
			if (key == Keyboard.KEY_UP) 
				world.getGuy().setMovingUp(false);
		
			if (key == Keyboard.KEY_DOWN) 
				world.getGuy().setMovingDown(false);
		
			if (key == Keyboard.KEY_LEFT) 
				world.getGuy().setMovingLeft(false);
		
			if (key == Keyboard.KEY_RIGHT) 
				world.getGuy().setMovingRight(false);
		
			if (key == Keyboard.KEY_SPACE) 
				world.getGuy().jumpReleased();
		
			if (key == Keyboard.KEY_LSHIFT) 
				world.getGuy().jumpReleased();
		}
	}
	
	
	protected void mouseDown(int key, int mouseX, int mouseY) {
		if (editingMode) {
			if (key == 0) {
				if (!isPaused()) {
					worldEditor.mouseRightDown(mouseX, mouseY);
				}
				else {
					menu.mouseClick(key, this, mouseX, mouseY);
				}
			}
		
			if (key == 1) {
				if (!isPaused()) {
				worldEditor.mouseLeftDown(mouseX, mouseY);
				} else {
					menu.mouseClick(key, this, mouseX, mouseY);
				}
			}
		} else {
			if (!isPaused()) {
				float worldMouseX = ((float)mouseX - (float)config.getScreenWidth() / 2f) / (float)config.getScale() + world.getCamera().getPosition().getX();
				float worldMouseY = ((float)mouseY - (float)config.getScreenHeight() / 2f) / (float)config.getScale() + world.getCamera().getPosition().getY();
				world.getGuy().mouseClick(worldMouseX, worldMouseY);
			} else {
				menu.mouseClick(key, this, mouseX, mouseY);
			}
		}
	}

	protected void mouseUp(int key, int mouseX, int mouseY) {
		if (config.outputInput()) {
			console.printLine("mouse up at " + mouseX + " " + mouseY);
		}
		if (editingMode) {
			if (key == 0) {
				if (!isPaused()) {
					worldEditor.mouseRightUp(mouseX, mouseY);
				}
			}

			if (key == 1) {
				if (!isPaused()) {
					worldEditor.mouseLeftUp(mouseX, mouseY);
				}
			}
		}
	}
	
	
	/** time at last frame */
	private long lastFrameTime;
	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrameTime);
	    lastFrameTime = time;
	    return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	 public static long getTime() {
	    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	 }
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateTitle(int delta) {
		int fps = (int)(1000 / CalcAverageTick(delta));
		
		if (titleUpdateTimer.evaluate(delta)) {
			String activeEntities;
			String globalEntities;
			if (world != null) {
				activeEntities = world.getActiveSize() + "";
				globalEntities = world.getGlobalSize() + "";
			} else {
				activeEntities = "no world";
				globalEntities = "no world";
			}
			
			Display.setTitle("FPS: " + fps + " Active Entities: " + activeEntities + " Global Entities: " + globalEntities);	
		}
	}
	
	private final int MAXSAMPLES = 100;
	private int tickindex=0;
	private int ticksum=0;
	private int[] ticklist = new int[MAXSAMPLES];

	/* need to zero out the ticklist array before starting */
	/* average will ramp up until the buffer is full */
	/* returns average ticks per frame over the MAXSAMPPLES last frames */

	double CalcAverageTick(int newtick)
	{
	    ticksum-=ticklist[tickindex];  /* subtract value falling off */
	    ticksum+=newtick;              /* add new value */
	    ticklist[tickindex]=newtick;   /* save new value so it can be subtracted later */
	    if(++tickindex==MAXSAMPLES)    /* inc buffer index */
	        tickindex=0;

	    /* return average */
	    return((double)ticksum/MAXSAMPLES);
	}
	
	
}