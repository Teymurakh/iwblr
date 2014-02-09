package com.teymurakh.iwblr.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SaveHandler {

	private HashMap<String, String> saveMap;
	private String saveFilePath;
	
	
	public void load(String level) {
		saveFilePath = "saves/";
		saveMap = new HashMap<String, String>();
		
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(saveFilePath + level + ".sav"));
			while ((sCurrentLine = br.readLine()) != null) {
				
				String line = sCurrentLine.replaceAll("\\s","");
				if (!(line.startsWith("//") || line.equals(""))) {
					String[] splitLine = line.split("[=]");
					saveMap.put(splitLine[0], splitLine[1]); 
				}
			}
		}  catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		Game.world.getGuy().getPos().setX(Float.parseFloat(saveMap.get("guy_x")));
		Game.world.getGuy().getPos().setY(Float.parseFloat(saveMap.get("guy_y")));
	}
	/*
	public void save(float x, float y) {
		try
		{
		    FileWriter fstream = new FileWriter(saveFilePath + Game.world.getName() + ".sav", false); //true tells to append data.
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write("guy_x = " + x);
		    out.newLine();
		    out.write("guy_y = " + y);
		    out.close();
		}
		catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
	}
	
	public void save(float x, float y, String level) {
		try
		{
		    FileWriter fstream = new FileWriter(saveFilePath + level + ".sav", false); //true tells to append data.
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write("guy_x = " + x);
		    out.newLine();
		    out.write("guy_y = " + y);
		    out.close();
		}
		catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
	}
	*/
}
