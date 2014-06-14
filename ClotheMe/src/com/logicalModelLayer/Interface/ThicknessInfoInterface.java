package com.logicalModelLayer.Interface;

import com.daogen.clotheme.Thickness;

public interface ThicknessInfoInterface {
	public int Load();
	public int getThickness(long in_ThicknessID,Thickness out_Thickness);
	public int getThickness(String in_Thickness,Thickness out_Thickness);
	public int setThickness(long in_ThicknessID,Thickness in_Thickness);
	public int setThickness(Thickness in_Thickness);
	public int removeThickness(long in_ThicknessID);
}
