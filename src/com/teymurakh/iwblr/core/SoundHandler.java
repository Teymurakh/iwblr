package com.teymurakh.iwblr.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class SoundHandler {
	private boolean loadedProperly;

	/** Maximum data buffers we will need. */
	private int NUM_BUFFERS;

	/** Maximum emissions we will need. */
	@SuppressWarnings("unused")
	private int NUM_SOURCES;

	/** Buffers hold sound data. */
	private IntBuffer buffer;

	/** Sources are points emitting sound. */
	private IntBuffer source;

	/** Position of the source sound. */
	private FloatBuffer sourcePos;

	/** Velocity of the source sound. */
	private FloatBuffer sourceVel;

	/** Position of the listener. */
	private FloatBuffer listenerPos;

	/** Velocity of the listener. */
	private FloatBuffer listenerVel;

	/** Orientation of the listener. (first 3 elements are "at", second 3 are "up")
      Also note that these should be units of '1'. */
	private FloatBuffer listenerOri;


	private final HashMap<String, Integer> sounds;
	private final String path = "resources/audio"; 
	private int lastId;



	public SoundHandler() {
		// CRUCIAL!
		// any buffer that has data added, must be flipped to establish its position and limits
		sounds = new HashMap<String, Integer>();
	}

	public void initialize() {
		NUM_BUFFERS = 10;
		NUM_SOURCES = 10;
		buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);
		source = BufferUtils.createIntBuffer(NUM_BUFFERS);
		sourcePos = BufferUtils.createFloatBuffer(3*NUM_BUFFERS);
		sourceVel = BufferUtils.createFloatBuffer(3*NUM_BUFFERS);

		listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
		listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
		listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f });

		// Initialize OpenAL and clear the error bit.
		try {
			AL.create();
			AL10.alGetError();

			// Load the wav data.
			if(loadALData() == AL10.AL_FALSE) {
				System.out.println("Error loading data.");
				return;
			}

			listenerPos.flip();
			listenerVel.flip();
			listenerOri.flip();

			AL10.alListener(AL10.AL_POSITION,    listenerPos);
			AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
			AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
			loadedProperly = true;

		} catch (LWJGLException le) {
			le.printStackTrace();
			return;
		}
	}

	@SuppressWarnings("deprecation")
	private WaveData fileToWaveData(File file) throws MalformedURLException {
		// TODO improve file loading into sound
		URL url = file.toURL();
		WaveData sound = WaveData.create(url);
		return sound;
	}


	/**
	 * boolean LoadALData()
	 *
	 *  This function will load our sample data from the disk using the Alut
	 *  utility and send the data into OpenAL as a buffer. A source is then
	 *  also created to play that buffer.
	 */
	int loadALData() {
		// Load wav data into a buffers.
		AL10.alGenBuffers(buffer);

		if(AL10.alGetError() != AL10.AL_NO_ERROR)
			return AL10.AL_FALSE;

		/////////////////////////////////////////////////////
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 

		for (File file : listOfFiles) {
			if (file.isFile()) {
				try {
					int id = lastId++;
					WaveData sound = fileToWaveData(file);

					AL10.alBufferData(buffer.get(id), sound.format, sound.data, sound.samplerate);

					sound.dispose();

					String name = file.getName().replaceAll(".wav", "");				
					sounds.put(name, id);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Bind buffers into audio sources.
		AL10.alGenSources(source);

		if(AL10.alGetError() != AL10.AL_NO_ERROR)
			return AL10.AL_FALSE;

		for (Integer soundId : sounds.values()) {
			AL10.alSourcei(source.get(soundId), AL10.AL_BUFFER,   buffer.get(soundId) );
			AL10.alSourcef(source.get(soundId), AL10.AL_PITCH,    1.0f          );
			AL10.alSourcef(source.get(soundId), AL10.AL_GAIN,     1.0f          );
			AL10.alSource (source.get(soundId), AL10.AL_POSITION, (FloatBuffer) sourcePos.position(soundId*3));
			AL10.alSource (source.get(soundId), AL10.AL_VELOCITY, (FloatBuffer) sourceVel.position(soundId*3));
			AL10.alSourcei(source.get(soundId), AL10.AL_LOOPING,  AL10.AL_FALSE  );
		}  

		// Do another error check and return.
		if(AL10.alGetError() == AL10.AL_NO_ERROR)
			return AL10.AL_TRUE;

		return AL10.AL_FALSE;
	}

	public void play(String soundName) {

		if (loadedProperly) {
			if (sounds.containsKey(soundName)) {
				int soundId = sounds.get(soundName);
				sourcePos.put(soundId*3+0, 0f);
				sourcePos.put(soundId*3+1, 0f);
				sourcePos.put(soundId*3+2, 0f);

				AL10.alSource(source.get(soundId), AL10.AL_POSITION, (FloatBuffer) sourcePos.position(soundId*3));
				AL10.alSourcePlay(source.get(soundId));   
			} else {
				Game.console.error("There is no \"" + soundName + "\" sound");
			}
		}

	}

	protected void destroy() {
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
		AL.destroy();
	}
}