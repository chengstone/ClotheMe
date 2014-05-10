package com.logicalModelLayer.clotheme;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.common.clothe.*;

public class CategoryArchiveInfo {
	public class CategoryArchiveStruct {
//		int meterialID;
		public boolean isWashRemind;
		public String remindTime;
		public String remindFrequency;
		//CategoryArchiveStruct(){}
	}
	private final String TABLENAME = "categoryarchive";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db;
	private static CategoryArchiveInfo m_instance = null;  
	private Map<Integer,CategoryArchiveStruct> m_CategoryArchiveData = null;
    private CategoryArchiveInfo(Context context) {  	
    	m_CategoryArchiveData = new HashMap<Integer,CategoryArchiveStruct>();
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
    	//Cursor cursor = m_db.query(TABLENAME, new String[]{"personid,name,age"}, "name like ?", new String[]{"%传智%"}, null, null, "personid desc", "1,2");
    	Cursor cursor = m_db.rawQuery("select * from " + TABLENAME, null);
    	while (cursor.moveToNext()) {
    		CategoryArchiveStruct cas = new CategoryArchiveStruct();
    		int meterialID;
    		meterialID = cursor.getInt(1);
    		cas.isWashRemind = cursor.getInt(2) == 0?false:true; 
    		cas.remindFrequency = cursor.getString(4);
    		cas.remindTime = cursor.getString(3);
    		m_CategoryArchiveData.put(meterialID, cas);
    		Log.v("meterialID", String.valueOf(meterialID));
    		Log.v("isWashRemind", String.valueOf(cursor.getInt(2)));
    		Log.v("remindFrequency", cas.remindFrequency);
    		Log.v("remindTime", cas.remindTime);
    	}
    	cursor.close();
    	return ret;
    }
}
