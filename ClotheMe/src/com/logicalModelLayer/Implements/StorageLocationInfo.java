package com.logicalModelLayer.Implements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.common.clothe.CommonDefine;
import com.common.clothe.Log;
import com.daogen.clotheme.PersonInformation;
import com.daogen.clotheme.Season;
import com.daogen.clotheme.SeasonDao;
import com.daogen.clotheme.StorageLocation;
import com.daogen.clotheme.StorageLocationDao;
import com.logicalModelLayer.Interface.StorageLocationInfoInterface;

public class StorageLocationInfo implements StorageLocationInfoInterface {
	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "storagelocation";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static StorageLocationInfo m_instance = null;  
	/*
	 * @brief 存放位置表map
	 * */
	private Map<Long,StorageLocation> m_StorageLocationData = null;
	private StorageLocationDao m_StorageLocationDao = null;
	
	/*
	 * @brief  存放位置表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private StorageLocationInfo(Context context) {  	

    	m_StorageLocationData = new HashMap<Long,StorageLocation>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_StorageLocationDao = 
    			AssetsDatabaseManager.getInstance(context).getStorageLocationDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static StorageLocationInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new StorageLocationInfo(context);
        }
        return m_instance;
    }  
    
    /*
     * @brief  加载数据库存放位置表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;

    	List<StorageLocation> storageLocationList = m_StorageLocationDao.loadAll();
    	if(storageLocationList.size() == 0 || storageLocationList.isEmpty()){
    		Log.w("StorageLocation has no elements in Load Function!");
    	}
    	for(int i = 0; i < storageLocationList.size(); i++)  
        {  
    		StorageLocation storageLocation = new StorageLocation();
    		storageLocation = storageLocationList.get(i);  
            m_StorageLocationData.put(storageLocation.getId(), storageLocation);
        }  
    	return ret;
    }
    /* 查
     * @brief  从存放位置表map中获取指定key的value
     * @param  in_StorageLocationID 指定key
     * @param  out_StorageLocation value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int getStorageLocation(long in_StorageLocationID,
			StorageLocation out_StorageLocation) {
		if (m_StorageLocationData == null || in_StorageLocationID < 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_StorageLocationData.isEmpty() == true) {
			Log.w("m_StorageLocationData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_StorageLocationData.containsKey(in_StorageLocationID) == true) {
			out_StorageLocation = m_StorageLocationData.get(in_StorageLocationID);
			if (out_StorageLocation == null) {
				Log.w("StorageLocation get failed");
				return CommonDefine.SYSTEM_ERROR;
			}
		} else {
			Log.w("m_StorageLocationData does not contains key "
					+ String.valueOf(in_StorageLocationID));
			return CommonDefine.SYSTEM_ERROR;
		}
		return CommonDefine.NO_ERROR;
	}
	/* 查
     * @brief  根据StorageLocation查询
     * @param  in_StorageLocation 指定key
     * @param  out_StorageLocation value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int getStorageLocation(String in_StorageLocation,
			StorageLocation out_StorageLocation) {
		if (m_StorageLocationData == null 
		|| in_StorageLocation == null
		|| in_StorageLocation.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_StorageLocationData.isEmpty() == true) {
			Log.w("m_StorageLocationData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}

		for (StorageLocation value : m_StorageLocationData.values()) {
			if (value.getLocation().equals(in_StorageLocation)) {
				out_StorageLocation = value;
				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_StorageLocationData does not contains " + in_StorageLocation);
		return CommonDefine.SYSTEM_ERROR;
	}
	
	/* 
     * @brief  返回所有的存放位置
     * @param  无
     * @return 成功 所有的存放位置字符串数组 失败 null
     * */
	public ArrayList<String> getAllLocation(){
		if (m_StorageLocationData == null) {
			Log.w("DataSource is null, return SYSTEM_ERROR");
			return null;
		}
		if (m_StorageLocationData.isEmpty() == true) {
			Log.w("m_StorageLocationData is Empty");
			return null;
		}

//		ArrayList al=new ArrayList(); 
		ArrayList<String> als = new ArrayList<String>(0);
		for (StorageLocation value : m_StorageLocationData.values()) {
			als.add(value.getLocation());
		}

		return als;
	}
	
	/* 增，改
     * @brief  设置map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_StorageLocationID 指定key
     * @param  in_StorageLocation 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setStorageLocation(long in_StorageLocationID,
			StorageLocation in_StorageLocation) {
		if (m_StorageLocationData == null 
		|| in_StorageLocation == null 
		|| in_StorageLocationID < 0) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_StorageLocationData.containsKey(in_StorageLocationID) == true) {
			Log.w("in_StorageLocationID " + String.valueOf(in_StorageLocationID)
					+ " is already exsists");

			StorageLocation storageLocation;
			storageLocation = m_StorageLocationData.get(in_StorageLocationID);
			in_StorageLocation.setId(storageLocation.getId());
			m_StorageLocationDao.update(in_StorageLocation);

			if (m_StorageLocationData.remove(in_StorageLocationID) == null) {
				Log.w("StorageLocation remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}

			if (m_StorageLocationData.put(in_StorageLocationID, in_StorageLocation) == null) {
				Log.w("StorageLocation put failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_StorageLocation.setId(GetMaxID());

		if (m_StorageLocationData.put(in_StorageLocationID, in_StorageLocation) == null) {
			Log.w("Season put failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_StorageLocationDao.insert(in_StorageLocation);
		if (retDao != 0) {
			Log.e("m_StorageLocationDao insert failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_StorageLocation 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setStorageLocation(StorageLocation in_StorageLocation) {
		if (m_StorageLocationData == null || in_StorageLocation == null) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		StorageLocation out_StorageLocation = null;
		if (getStorageLocation(in_StorageLocation.getLocation(), out_StorageLocation) 
				== CommonDefine.NO_ERROR) {
			Log.w("StorageLocation " + in_StorageLocation.getLocation()
					+ " is already exsists");

			in_StorageLocation.setId(out_StorageLocation.getId());
			m_StorageLocationDao.update(in_StorageLocation);

			if (m_StorageLocationData.remove(in_StorageLocation.getId()) == null) {
				Log.w("StorageLocation remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			if (m_StorageLocationData.put(in_StorageLocation.getId(), in_StorageLocation) == null) {
				Log.w("StorageLocation put failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_StorageLocation.setId(GetMaxID());

		if (m_StorageLocationData.put(in_StorageLocation.getId(), in_StorageLocation) == null) {
			Log.w("StorageLocation put failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_StorageLocationDao.insert(in_StorageLocation);
		if (retDao != 0) {
			Log.e("m_StorageLocationDao insert failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定key的value
     * @param  in_StorageLocationID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeStorageLocation(long in_StorageLocationID) {
		if (m_StorageLocationData == null || in_StorageLocationID < 0) {
			Log.w("param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_StorageLocationData.isEmpty() == true) {
			Log.w("m_StorageLocationData is Empty");
			return CommonDefine.NO_ERROR;
		}
		if (m_StorageLocationData.containsKey(in_StorageLocationID) == true) {
			if (m_StorageLocationData.remove(in_StorageLocationID) == null) {
				Log.w("remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			m_StorageLocationDao.deleteByKey(in_StorageLocationID);
			// m_StorageLocationDao.delete(entity);
		} else {
			Log.w("m_StorageLocationData does not contains key "
					+ String.valueOf(in_StorageLocationID));
			return CommonDefine.NO_ERROR;
		}
		return CommonDefine.NO_ERROR;
	}

	/* 删
     * @brief  从map中删除指定的StorageLocation
     * @param  in_StorageLocation 指定的StorageLocation
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeStorageLocation(String in_StorageLocation) {
		if (m_StorageLocationData == null 
		|| in_StorageLocation == null
		|| in_StorageLocation.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_StorageLocationData.isEmpty() == true) {
			Log.w("m_StorageLocationData is Empty");
			return CommonDefine.NO_ERROR;
		}

		for (StorageLocation value : m_StorageLocationData.values()) {
			if (value.getLocation().equals(in_StorageLocation)) {
				m_StorageLocationDao.deleteByKey(value.getId());

				if (m_StorageLocationData.remove(value.getId()) == null) {
					Log.w("remove failed");
					return CommonDefine.SYSTEM_ERROR;
				}

				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_StorageLocationData does not contains Location " + in_StorageLocation);
		return CommonDefine.NO_ERROR;
	}
    
    /* 
     * @brief  获取最大的ID值
     * @param  无
     * @return 成功 最大的ID值 失败 -1
     * */
	private long GetMaxID(){
    	
    	long ret = -2;
    	
    	if(m_StorageLocationData == null){
           	Log.w("param invalid, return SYSTEM_ERROR");
           	return 0;
       	}
       	if(m_StorageLocationData.isEmpty() == true){
           	Log.w("m_CategoryData is Empty");
       		return 0;
       	}
       	
       	for (StorageLocation value : m_StorageLocationData.values()) {
       		if(value.getId() > ret){
       			ret = value.getId();
       		}
       	}
       	
       	ret = ret + 1;
       	
       	return ret;
    }
}
