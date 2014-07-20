package com.logicalModelLayer.Interface;

import java.util.ArrayList;

import com.daogen.clotheme.Season;

public interface SeasonInfoInterface {
	public int Load();
	public int getSeason(long in_SeasonID,Season out_Season);
	public Season getSeason(String in_SeasonName);//,Season out_Season);
	public int setSeason(long in_SeasonID,Season in_Season);
	public int setSeason(Season in_Season);
	public int removeSeason(long in_SeasonID);
	public int removeSeason(String in_SeasonName);
	public ArrayList<String> getAllSeason();
}
