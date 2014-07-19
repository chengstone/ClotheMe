package com.logicalModelLayer.Interface;

import java.util.ArrayList;

import com.daogen.clotheme.WearPlace;

public interface WearPlaceInfoInterface {
	public int Load();
	public int getWearPlace(long in_WearPlaceID,WearPlace out_WearPlace);
	public int getWearPlace(String in_WearPlace,WearPlace out_WearPlace);
	public int setWearPlace(long in_WearPlaceID,WearPlace in_WearPlace);
	public int setWearPlace(WearPlace in_WearPlace);
	public int setWearPlace(String in_WearPlace);
	public int removeWearPlace(long in_WearPlaceID);
	public int removeWearPlace(String in_WearPlace);
	public ArrayList<String> getAllWearPlace();
}
