package com.teymurakh.iwblr.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.compiler.DumpState;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaVM {

	private static String rawPath = "resources/scripts/raw/";
	private static String debugPath = "resources/scripts/debug/";
	private static String compiledPath = "resources/scripts/compiled/";
	
	public static final ArrayList<String> toLoad = new ArrayList<String>();
	private static LuaValue _G;
	
	public static void loadVm() {
		//////////////////////////////
		final File folder = new File(rawPath + "entities/");
		ArrayList<String> unparsed = listFilesForFolder(folder);
		for (String name : unparsed) {
			toLoad.add(name.replaceAll(".lua", ""));
		}
		///////////////////////////////
		
		for (String name : toLoad) {
			processLuaEntity(rawPath + "entities/" + name + ".lua", debugPath + "entities/" + name + ".lua");
		}
		
		for (String name : toLoad) {
			compile(debugPath + "entities/" + name + ".lua", compiledPath + "entities/" + name);
		}

		FileSystem.copyFile(rawPath + "core/main.lua", debugPath + "core/main.lua");
		compile(debugPath + "core/main.lua", compiledPath + "core/main");

		FileSystem.copyFile(rawPath + "/entities/entity.lua", debugPath + "/entities/entity.lua");
		compile(debugPath + "entities/entity.lua", compiledPath + "/entities/entity");
		
		String script = compiledPath + "core/main";
		_G = JsePlatform.standardGlobals();
		_G.get("dofile").call( LuaValue.valueOf(script) );
		
		call("initialize", toLoad);
	}
	
	
	private static void processLuaEntity(String inPath, String outPath) {
		String script = FileSystem.fileToString(inPath);
				
		String lookFor = "#extends ";
		
		int extendsIndex = script.indexOf(lookFor); // index of the "#extends" beginning
		int end = extendsIndex;                     //
		
		String parent;
		if (extendsIndex > -1) {
			end = script.indexOf("\n", extendsIndex);
			parent = script.substring(extendsIndex + lookFor.length(), end).trim();
			
			String includeStart = FileSystem.fileToString(rawPath + "core/include_start.lua");
			String includeEnd = FileSystem.fileToString(rawPath + "core/include_end.lua");
			String includeStartParsed = includeStart.replaceAll("%parent%", parent);
			
			String toReplace = script.substring(extendsIndex, end);
			script = script.replaceAll(toReplace, includeStartParsed);
			script = script + includeEnd;
		}
		
		FileSystem.stringToFile(script, outPath);
	}
	

	
	private static void compile(String inPath, String outPath) {
		try {
			String script = FileSystem.fileToString(inPath);
			
			InputStream is = new ByteArrayInputStream(script.getBytes());
			OutputStream os = new FileOutputStream(outPath);

			String chunkname = "";
			@SuppressWarnings("static-access")
			Prototype chunk = LuaC.instance.compile(is, chunkname);
	        DumpState.dump(chunk, os, false, 0, false);

			is.close(); 
			os.close();
			
			System.out.println(inPath + " compiled to " + outPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static ArrayList<String> listFilesForFolder(final File folder) {
		ArrayList<String> files = new ArrayList<String>();
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	files.add(fileEntry.getName());
	        }
	    }
	    return files;
	}
	
	
	
	
	public static void call(String fName, Object object1) {
		LuaValue luaObject1 = CoerceJavaToLua.coerce(object1);
		_G.get(fName).call(luaObject1);
	}
	
	public static void call(String fName, Object object1, Object object2) {
		LuaValue luaObject1 = CoerceJavaToLua.coerce(object1);
		LuaValue luaObject2 = CoerceJavaToLua.coerce(object2);

		_G.get(fName).call(luaObject1, luaObject2);
	}
	
	public static void call(String fName, Object object1, Object object2, Object object3) {
		LuaValue luaObject1 = CoerceJavaToLua.coerce(object1);
		LuaValue luaObject2 = CoerceJavaToLua.coerce(object2);
		LuaValue luaObject3 = CoerceJavaToLua.coerce(object3);
		
		_G.get(fName).call(luaObject1, luaObject2, luaObject3);
	}
	
	public static void invoke(String fName, Object[] objects) {
		LuaValue[] args = new LuaValue[objects.length];
		for (int i = 0; i < objects.length; i++) {
			args[i] = CoerceJavaToLua.coerce(objects[i]);
		}
		
		_G.get(fName).invoke(args);
	}
	
}