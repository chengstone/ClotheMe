package com.logicalModelLayer.Interface;

import com.daogen.clotheme.Meterial;

public interface MeterialInfoInterface {
	public int Load();
	public int getMeterial(long in_MeterialID,Meterial out_Meterial);
	public int getMeterial(String in_Description,Meterial out_Meterial);
	public int setMeterial(long in_MeterialID,Meterial in_Meterial);
	public int setMeterial(Meterial in_Meterial);
	public int removeMeterial(long in_MeterialID);
	public int removeMeterial(String in_Description);
}
