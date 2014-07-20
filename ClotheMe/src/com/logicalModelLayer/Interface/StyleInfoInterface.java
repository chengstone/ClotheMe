package com.logicalModelLayer.Interface;

import java.util.ArrayList;

import com.daogen.clotheme.Style;

public interface StyleInfoInterface {
	public int Load();
	public int getStyle(long in_StyleID,Style out_Style);
	public Style getStyle(String in_Style);//,Style out_Style);
	public int setStyle(long in_StyleID,Style in_Style);
	public int setStyle(Style in_Style);
	public int setStyle(String in_Style);
	public int removeStyle(long in_StyleID);
	public int removeStyle(String in_Style);
	public ArrayList<String> getAllStyle();
}
