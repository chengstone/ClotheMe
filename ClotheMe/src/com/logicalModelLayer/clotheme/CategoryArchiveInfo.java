package com.logicalModelLayer.clotheme;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
import android.util.SparseArray;

import com.common.clothe.*;
/*
 * 分类存档表类
 * */
public class CategoryArchiveInfo {
	/*
	 * 分类存档结构
	 * */
	public class CategoryArchiveStruct {
//		int meterialID;					//物品ID
		public boolean isWashRemind;	//是否清洗提醒
		public String remindTime;		//提醒时间
		public String remindFrequency;	//提醒频率
		//CategoryArchiveStruct(){}
	}
	/*
	 * DB表名
	 * */
	private final String TABLENAME = "categoryarchive";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db;
	private static CategoryArchiveInfo m_instance = null;  
	/*
	 * 分类存档表map
	 * */
	private Map<Integer,CategoryArchiveStruct> m_CategoryArchiveData = null;
	/*
	 * 分类存档表类构造函数
	 * 参数：context 上下文
	 * 返回值：无
	 * */
    private CategoryArchiveInfo(Context context) {  	
    	m_CategoryArchiveData = new HashMap<Integer,CategoryArchiveStruct>();
    	this.context = context;
    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	Load();
    }  
    /*
     * 获取本类单例
     * 参数：context 上下文
     * 返回值：单例
     * */
    public static CategoryArchiveInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new CategoryArchiveInfo(context);
        }
        return m_instance;
    }  
    /*
     * 加载数据库分类存档表
     * 返回值：成功 NO_ERROR 失败 无
     * */
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
    		if(CommonDefine.isDebug){
        		Log.v("meterialID = " + String.valueOf(meterialID));
        		Log.v("isWashRemind = " + String.valueOf(cursor.getInt(2)));
        		Log.v("remindFrequency = " + cas.remindFrequency);
        		Log.v("remindTime = " + cas.remindTime);
    		}
    	}
    	cursor.close();
    	return ret;
    }
    /* 查
     * 从分类存档表map中获取指定key的value
     * 参数1：in_meterialID 指定key
     * 参数2：out_CategoryArchiveStruct value将保存其中
     * 返回值：成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int getCategoryArchiveStruct(int in_meterialID,CategoryArchiveStruct out_CategoryArchiveStruct){
    	if(m_CategoryArchiveData == null
    	|| out_CategoryArchiveStruct == null
    	|| in_meterialID <= 0){
    		if(CommonDefine.isDebug){
        		Log.v("return SYSTEM_ERROR");
    		}
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryArchiveData.isEmpty() == true){
    		if(CommonDefine.isDebug){
        		Log.v("m_CategoryArchiveData is Empty");
    		}
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryArchiveData.containsKey(in_meterialID) == true){
    		out_CategoryArchiveStruct = m_CategoryArchiveData.get(in_meterialID);
    		if(out_CategoryArchiveStruct == null){
    			if(CommonDefine.isDebug){
            		Log.v("get failed");
        		}
        		return CommonDefine.SYSTEM_ERROR;
    		}
    	}
    	else {
    		if(CommonDefine.isDebug){
        		Log.v("m_CategoryArchiveData does not contains key " + String.valueOf(in_meterialID));
    		}
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	return CommonDefine.NO_ERROR;
    }
    /* 增，改
     * 设置分类存档表map中指定key的value,如果该key已存在,则删除,添加新值
     * 参数1：in_meterialID 指定key
     * 参数2：in_CategoryArchiveStruct 将要写入map的value
     * 返回值：成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int setCategoryArchiveStruct(int in_meterialID,CategoryArchiveStruct in_CategoryArchiveStruct){
    	if(m_CategoryArchiveData == null
    	|| in_CategoryArchiveStruct == null
    	|| in_meterialID <= 0){
    		if(CommonDefine.isDebug){
        		Log.v("return SYSTEM_ERROR");
    		}
    		return CommonDefine.SYSTEM_ERROR;
    	}

    	if(m_CategoryArchiveData.containsKey(in_meterialID) == true){
    		if(CommonDefine.isDebug){
        		Log.v("meterialID " + String.valueOf(in_meterialID) + " is already exsists");
    		}
    		if(m_CategoryArchiveData.remove(in_meterialID) == null){
    			if(CommonDefine.isDebug){
            		Log.v("remove failed");
        		}
        		return CommonDefine.SYSTEM_ERROR;
    		}
//    		return CommonDefine.SYSTEM_ERROR;
    	}

    	if(m_CategoryArchiveData.put(in_meterialID,in_CategoryArchiveStruct) == null){
    		if(CommonDefine.isDebug){
        		Log.v("put failed");
    		}
    		return CommonDefine.SYSTEM_ERROR;
    	}

    	return CommonDefine.NO_ERROR;
    }
    /* 删
     * 从分类存档表map中删除指定key的value
     * 参数1：in_meterialID 指定key
     * 返回值：成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int removeCategoryArchiveStruct(int in_meterialID){
    	if(m_CategoryArchiveData == null
    	|| in_meterialID <= 0){
    		if(CommonDefine.isDebug){
        		Log.v("return SYSTEM_ERROR");
    		}
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryArchiveData.isEmpty() == true){
    		if(CommonDefine.isDebug){
        		Log.v("m_CategoryArchiveData is Empty");
    		}
    		return CommonDefine.NO_ERROR;
    	}
    	if(m_CategoryArchiveData.containsKey(in_meterialID) == true){
    		if(m_CategoryArchiveData.remove(in_meterialID) == null){
    			if(CommonDefine.isDebug){
            		Log.v("remove failed");
        		}
        		return CommonDefine.SYSTEM_ERROR;
    		}
    	}
    	else {
    		if(CommonDefine.isDebug){
        		Log.v("m_CategoryArchiveData does not contains key " + String.valueOf(in_meterialID));
    		}
    		return CommonDefine.NO_ERROR;
    	}
    	return CommonDefine.NO_ERROR;
    }
}
