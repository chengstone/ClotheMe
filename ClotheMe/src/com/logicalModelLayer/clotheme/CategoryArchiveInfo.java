package com.logicalModelLayer.clotheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
import android.util.SparseArray;

import com.common.clothe.*;
import com.daogen.clotheme.CategoryArchive;
import com.daogen.clotheme.CategoryArchiveDao;
import com.daogen.clotheme.CategoryArchiveDao.Properties;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;
/*
 * @brief 分类存档表类
 * */
public class CategoryArchiveInfo {
	/*
	 * @brief 分类存档结构
	 * */
//	public class CategoryArchiveStruct {
////		int meterialID;					//物品ID
//		public boolean isWashRemind;	//是否清洗提醒
//		public String remindTime;		//提醒时间
//		public String remindFrequency;	//提醒频率
//		//CategoryArchiveStruct(){}
//	}
	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "categoryarchive";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static CategoryArchiveInfo m_instance = null;  
	/*
	 * @brief 分类存档表map
	 * */
//	private Map<Integer,CategoryArchiveStruct> m_CategoryArchiveData = null;
	private Map<Integer,CategoryArchive> m_CategoryArchiveData = null;
	private CategoryArchiveDao m_CategoryArchiveDao = null;
	/*
	 * @brief  分类存档表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private CategoryArchiveInfo(Context context) {  	
//    	m_CategoryArchiveData = new HashMap<Integer,CategoryArchiveStruct>();
    	m_CategoryArchiveData = new HashMap<Integer,CategoryArchive>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_CategoryArchiveDao = AssetsDatabaseManager.getInstance(context).getCategoryArchiveDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static CategoryArchiveInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new CategoryArchiveInfo(context);
        }
        return m_instance;
    }  
    /*
     * @brief  加载数据库分类存档表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;
    	//Cursor cursor = m_db.query(TABLENAME, new String[]{"personid,name,age"}, "name like ?", new String[]{"%传智%"}, null, null, "personid desc", "1,2");
//    	Cursor cursor = m_db.rawQuery("select * from " + TABLENAME, null);
//    	while (cursor.moveToNext()) {
//    		CategoryArchiveStruct cas = new CategoryArchiveStruct();
//    		int meterialID;
//    		meterialID = cursor.getInt(1);
//    		cas.isWashRemind = cursor.getInt(2) == 0?false:true; 
//    		cas.remindFrequency = cursor.getString(4);
//    		cas.remindTime = cursor.getString(3);
//    		m_CategoryArchiveData.put(meterialID, cas);
//    		if(CommonDefine.isDebug){
//        		Log.d("meterialID = " + String.valueOf(meterialID));
//        		Log.d("isWashRemind = " + String.valueOf(cursor.getInt(2)));
//        		Log.d("remindFrequency = " + cas.remindFrequency);
//        		Log.d("remindTime = " + cas.remindTime);
//    		}
//    	}
//    	cursor.close();
    	List<CategoryArchive> ca = m_CategoryArchiveDao.loadAll();
    	for(int i = 0; i < ca.size(); i++)  
        {  
    		CategoryArchive cas = new CategoryArchive();
            cas = ca.get(i);  
            m_CategoryArchiveData.put(cas.getMeterialID(), cas);
        }  
    	return ret;
    }
    /* 查
     * @brief  从分类存档表map中获取指定key的value
     * @param  in_meterialID 指定key
     * @param  out_CategoryArchiveStruct value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
//    public int getCategoryArchiveStruct(int in_meterialID,CategoryArchiveStruct out_CategoryArchiveStruct){
    public int getCategoryArchive(int in_meterialID,CategoryArchive out_CategoryArchive){
    	if(m_CategoryArchiveData == null
    	|| out_CategoryArchive == null
    	|| in_meterialID <= 0){
        	Log.w("Param invalid, return SYSTEM_ERROR");
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryArchiveData.isEmpty() == true){
    		Log.w("m_CategoryArchiveData is Empty");
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryArchiveData.containsKey(in_meterialID) == true){
    		out_CategoryArchive = m_CategoryArchiveData.get(in_meterialID);
    		if(out_CategoryArchive == null){
            	Log.w("CategoryArchive get failed");
        		return CommonDefine.SYSTEM_ERROR;
    		}
    	}
    	else {
        	Log.w("m_CategoryArchiveData does not contains key " + String.valueOf(in_meterialID));
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	return CommonDefine.NO_ERROR;
    }
    /* 增，改
     * @brief  设置分类存档表map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_meterialID 指定key
     * @param  in_CategoryArchiveStruct 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int setCategoryArchive(int in_meterialID,CategoryArchive in_CategoryArchive){
    	if(m_CategoryArchiveData == null
    	|| in_CategoryArchive == null
    	|| in_meterialID <= 0){
        	Log.w("Param invalid,return SYSTEM_ERROR");
    		return CommonDefine.SYSTEM_ERROR;
    	}

    	if(m_CategoryArchiveData.containsKey(in_meterialID) == true){
        	Log.w("meterialID " + String.valueOf(in_meterialID) + " is already exsists");
    		if(m_CategoryArchiveData.remove(in_meterialID) == null){
            	Log.w("CategoryArchive remove failed");
        		return CommonDefine.SYSTEM_ERROR;
    		}
    		QueryBuilder<CategoryArchive> qb = m_CategoryArchiveDao.queryBuilder();
    		DeleteQuery<CategoryArchive> bd = qb.where(Properties.MeterialID.eq(in_meterialID)).buildDelete();
    		bd.executeDeleteWithoutDetachingEntities();
//    		return CommonDefine.SYSTEM_ERROR;
    	}

    	if(m_CategoryArchiveData.put(in_meterialID,in_CategoryArchive) == null){
        	Log.w("CategoryArchive put failed");
    		return CommonDefine.SYSTEM_ERROR;
    	}

    	m_CategoryArchiveDao.insert(in_CategoryArchive);
    	
    	return CommonDefine.NO_ERROR;
    }
    /* 删
     * @brief  从分类存档表map中删除指定key的value
     * @param  in_meterialID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int removeCategoryArchive(int in_meterialID){
    	if(m_CategoryArchiveData == null
    	|| in_meterialID <= 0){
        	Log.w("param invalid, return SYSTEM_ERROR");
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryArchiveData.isEmpty() == true){
        	Log.w("m_CategoryArchiveData is Empty");
    		return CommonDefine.NO_ERROR;
    	}
    	if(m_CategoryArchiveData.containsKey(in_meterialID) == true){
    		if(m_CategoryArchiveData.remove(in_meterialID) == null){
            	Log.w("remove failed");
        		return CommonDefine.SYSTEM_ERROR;
    		}
    		QueryBuilder<CategoryArchive> qb = m_CategoryArchiveDao.queryBuilder();
    		DeleteQuery<CategoryArchive> bd = qb.where(Properties.MeterialID.eq(in_meterialID)).buildDelete();
    		bd.executeDeleteWithoutDetachingEntities();
    	}
    	else {
        	Log.w("m_CategoryArchiveData does not contains key " + String.valueOf(in_meterialID));
    		return CommonDefine.NO_ERROR;
    	}
    	return CommonDefine.NO_ERROR;
    }
}
