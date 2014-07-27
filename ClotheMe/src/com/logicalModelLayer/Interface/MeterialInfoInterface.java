package com.logicalModelLayer.Interface;

import java.util.List;

import com.daogen.clotheme.Meterial;
import com.daogen.clotheme.MeterialDao;

public interface MeterialInfoInterface {
	public int Load();
	public int getMeterial(long in_MeterialID,Meterial out_Meterial);
	public int getMeterial(String in_Description,Meterial out_Meterial);
	public int setMeterial(long in_MeterialID,Meterial in_Meterial);
	public int setMeterial(Meterial in_Meterial);
	public int removeMeterial(long in_MeterialID);
	public int removeMeterial(String in_Description);
//	public MeterialDao getMeterialDao();
	public List<Meterial> getMeterialFromCategoryID(Long in_CategoryID);
}
