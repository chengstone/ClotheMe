package com.logicalModelLayer.Interface;

import java.util.ArrayList;

import com.daogen.clotheme.StorageLocation;

public interface StorageLocationInfoInterface {
	public int Load();
	public int getStorageLocation(long in_StorageLocationID,
								  StorageLocation out_StorageLocation);
	public int getStorageLocation(String in_StorageLocation,
			  StorageLocation out_StorageLocation);
	public int setStorageLocation(long in_StorageLocationID,
								  StorageLocation in_StorageLocation);
	public int setStorageLocation(StorageLocation in_StorageLocation);
	public int removeStorageLocation(long in_StorageLocationID);
	public int removeStorageLocation(String in_StorageLocation);
	public ArrayList<String> getAllLocation();
}
