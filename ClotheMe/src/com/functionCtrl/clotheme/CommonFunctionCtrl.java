package com.functionCtrl.clotheme;

import java.util.ArrayList;

public class CommonFunctionCtrl {
	// ArrayList<String> 转 String[]
	public static String[] trans(ArrayList<String> als){
	  String[] sa=new String[als.size()];
	  als.toArray(sa);
	  return sa;
	}
	// String[] 转 ArrayList<String>
	public static ArrayList<String> trans(String[] sa){
	  ArrayList<String> als=new ArrayList<String>(0);
	  for(int i=0;i<sa.length;i++){
	    als.add(sa[i]);
	  }
	  return als;
	}
}
