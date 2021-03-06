package com.logicalModelLayer.Interface;

import java.util.ArrayList;

import com.daogen.clotheme.Category;

public interface CategoryInfoInterface {
	public int Load();
	public int getCategory(long in_CategoryID,Category out_Category);
	public Category getCategory(String in_CategoryName);//,Category out_Category);
	public int setCategory(long in_CategoryID,Category in_Category);
	public int setCategory(Category in_Category);
	public int removeCategory(long in_CategoryID);
	public int removeCategory(String in_CategoryName);
	public ArrayList<String> getAllCategory();
}
