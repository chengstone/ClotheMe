package com.logicalModelLayer.clotheme;

import java.util.HashMap;
import java.util.Map;

import android.util.SparseArray;

import com.commonStructs.*;

public class CategoryArchiveInfo {
	public class CategoryArchiveStruct {
//		int meterialID;
		boolean isWashRemind;
		String remindTime;
		String remindFrequency;
	}
	private static CategoryArchiveInfo m_instance = null;  
	private SparseArray<CategoryArchiveStruct> m_CategoryArchiveData = null;
    private CategoryArchiveInfo() {  	
    	m_CategoryArchiveData = new SparseArray<CategoryArchiveStruct>();
    	Load();
    }  
    public static CategoryArchiveInfo getInstance() {  
        if(m_instance == null){
        	m_instance = new CategoryArchiveInfo();
        }
        return m_instance;
    }  
    
    public boolean Load(){
    	boolean ret = true;
    	
    	return ret;
    }
}
