package com.logicalModelLayer.Interface;

import java.util.ArrayList;

import com.daogen.clotheme.CategoryArchive;

public interface CategoryArchiveInfoInterface {
	public int Load();
	public int getCategoryArchive(int in_meterialID,CategoryArchive out_CategoryArchive);
	public int setCategoryArchive(int in_meterialID,CategoryArchive in_CategoryArchive);
	public int removeCategoryArchive(int in_meterialID);
	public ArrayList<String> getAllRemindFrequency();
}
