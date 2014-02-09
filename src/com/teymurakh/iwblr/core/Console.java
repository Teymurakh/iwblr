package com.teymurakh.iwblr.core;

import java.util.Calendar;

public class Console {
	
	public void printLine(String line) {
		Calendar calendar = Calendar.getInstance();
		String date = "[" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "] ";
		System.out.println(date + line);
	}
	
	public void error(String s) {
		Calendar calendar = Calendar.getInstance();
		String date = "[" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "] ";
		System.out.println(date + "[ERROR]" + " " + s);
	}
}
