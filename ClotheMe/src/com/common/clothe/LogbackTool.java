package com.common.clothe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTool {

	protected static final String TAG = "ClotheMe";
	public static Logger logback = null;
	public LogbackTool(){
		
	}
	
	public static Logger getInstance(String in_className){
		if(logback == null){
			logback = LoggerFactory.getLogger(in_className);
		}
		return logback;
	}
	
	public static Logger getInstance(){
		if(logback == null){
			logback = LoggerFactory.getLogger(TAG);
		}
		return logback;
	}
	
	public static void i(String msg){
		if(logback != null){
			logback.info(msg);
		}
	}
	
	public static void e(String msg){
		if(logback != null){
			logback.error(msg);
		}
	}

	public static void w(String msg){
		if(logback != null){
			logback.warn(msg);
		}
	}
	
	public static void d(String msg){
		if(logback != null){
			logback.debug(msg);
		}
	}
	
	public static void t(String msg){
		if(logback != null){
			logback.trace(msg);
		}
	}
}
