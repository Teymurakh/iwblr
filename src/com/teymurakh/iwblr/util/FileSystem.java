package com.teymurakh.iwblr.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class FileSystem {
	
	public static String fileToString(String path) {
		String str = "";
		try {
			Scanner scanner = new Scanner(new File(path));
			str = scanner.useDelimiter("\\Z").next();
			scanner.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	public static void stringToFile(String text, String path) {
		PrintStream out = null;
		try {
		    try {
				out = new PrintStream(new FileOutputStream(path));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		    out.print(text);
		}
		finally {
		    if (out != null) out.close();
		}
	}
	
	public static void copyFile(String pathFrom, String pathTo) {
		stringToFile(fileToString(pathFrom), pathTo);
	}
	
}
