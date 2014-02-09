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
	
	public static final String VERSION = "1.5";
	
	Timer4 titleUpdateTimer = new Timer4(0, 100);
	
	public static Config config								= new Config();
	public static Console console 							= new Console();
	public static InputHandler inputHandler 				= new InputHandler();
	public static Menu menu 								= new Menu();
	public static Renderer worldRenderer 					= new Renderer();
	public static TextureHandler textureHandler 			= new TextureHandler();
	public static FontHandler fontHandler 					= new FontHandler();
	public static AnimationFactory animationFactory			= new AnimationFactory();
	public static Collisions collisions 					= new Collisions();
	public static SoundHandler soundHandler 				= new SoundHandler();
	public static SaveHandler saveHandler 					= new SaveHandler();
	public static WorldEditor worldEditor 					= new WorldEditor();
	public static GameState gameState 						= new GameState();
	

	protected static World world;

	static WorldUpdateThread thread1;
	
	
	/** Creates a game and initializes it */
	public static void main(String[] argv) {
		Game game = new Game();
		game.initialize();
	}
	
	/** Creates a display and initializes all static fields of the game*/
	public void initialize() {
		LuaVM.loadVm();
		startTime = getTime();
		
		
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
			gameLoop();
		}
		end();
	}
	
	long startTime;
	long timeSinceStart;
	
	/** Main game loop. It is responsible for updating the game, updating the input and rendering the game */
	public void gameLoop() {
		inputHandler.update();
		int delta = getDelta();
		
		if (world != null) {
			if (!world.isPaused()) {
				if (config.isEditorEnabled()) {
					worldEditor.update();
				}
				worldRenderer.drawWorldLayer = true;
				worldRenderer.drawMenuLayer = false;
			}
			else {
				worldRenderer.drawMenuLayer = true;
				worldRenderer.drawGuyLayer = false;
				menu.update(delta, worldRenderer);
			}
		
			if (world.getGuy().isDead()) {
				worldRenderer.drawGameOverLayer = true;
				worldRenderer.drawGuyLayer = false;
			} else {
				worldRenderer.drawGameOverLayer = false;
				worldRenderer.drawGuyLayer = true;
			}
		}
		
		
		// Proper way
		if (world != null) {
				worldRenderer.worldLayerArray = world.getActive();
				worldRenderer.guyLayerArray = new Drawable[]{world.getGuy()};
		}
		worldRenderer.menuLayerArray = menu.getElementList().toArray(new Drawable[menu.getElementList().size()]);
		worldRenderer.gameOverLayerArray = menu.getGameOverScreen().toArray(new Drawable[menu.getGameOverScreen().size()]);
		// end
		
		
		
		worldRenderer.renderLayers();
		
		if (config.isEditorEnabled()) {
			worldEditor.draw(worldRenderer);
		}
		
		updateTitle(delta);
		Display.update();
		Display.sync(120);
		//Display.sync(config.getFpsLimit()); // cap fps to 60fps
	}
	
	/** Asks all entities to clean up, stops all running threads, and finally destroys the display*/
	public void end() {
		Game.console.printLine("Requesting thread1 " + thread1 + " to stop");
		if (thread1 != null) {
			thread1.requestStop();
		}
		soundHandler.destroy();
		Display.destroy();
	}
	
	public static void pause() {
		world.pause();
	}
	
	public static void unPause() {
		world.unPause();
	}
	
	public static boolean isPaused() {
		if (world != null) {
			return world.isPaused();
		}
		return true;
	}
	
	public static void newGame() {

		world.respawnGuy();
		saveHandler.load(world.getName());
		inputHandler.reset(Keyboard.KEY_R);
		
		
		/*
		String levelName = world.getName();
		
		loadLevel(levelName);
		
		*/
	}
	
	public static void loadLevel(String levelName) {
		if (thread1 != null) {
			thread1.requestStop();
		}
		
		world = new World();
		world.initialize();
		
		
		saveHandler.load(levelName);
		inputHandler.reset(Keyboard.KEY_R);
		
		thread1 = new WorldUpdateThread(world);
		thread1.setLevelToBeLoaded(levelName);
		thread1.start();
	}
	
	public static void saveLevel(String levelName) {
		world.saveLevel(levelName);
	}
	
	public static void save() {
		gameState.put("guy_x", world.getGuy().getPosX()+"");
		gameState.put("guy_y", world.getGuy().getPosY()+"");
		gameState.saveState(world.getName());
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