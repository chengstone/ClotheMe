package com.logicalModelLayer.clotheme;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.common.clothe.*;

public class CategoryArchiveInfo {
	public class CategoryArchiveStruct {
//		int meterialID;
		boolean isWashRemind;
		String remindTime;
		String remindFrequency;
	}
	private final String TABLENAME = "categoryarchive";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db;
	private static CategoryArchiveInfo m_instance = null;  
	private SparseArray<CategoryArchiveStruct> m_CategoryArchiveData = null;
    private CategoryArchiveInfo(Context context) {  	
    	m_CategoryArchiveData = new SparseArray<CategoryArchiveStruct>();
    	this.context = context;
    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	Load();
    }  
    public static CategoryArchiveInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new CategoryArchiveInfo(context);
        }
        return m_instance;
    }  
    
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;
    	
    	return ret;
    }
}
