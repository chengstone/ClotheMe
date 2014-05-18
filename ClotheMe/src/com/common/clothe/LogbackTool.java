package com.common.clothe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTool {

	public static Logger logback = null;
	public LogbackTool(){
		
	}
	
	public static Logger getInstance(String in_className){
		if(logback == null){
			logback = LoggerFactory.getLogger(in_className);	
		}
		return logback;
	}
	
	public static void i(String msg){
		
	}
	
	public static void e(String msg){
		
	}

	public static void w(String msg){
	
	}
	
	public static void d(String msg){
		
	}
	
	public static void v(String msg){
		
	}
}
