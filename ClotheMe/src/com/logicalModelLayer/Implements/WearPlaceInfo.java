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
import com.daogen.clotheme.Style;
import com.daogen.clotheme.Thickness;
import com.daogen.clotheme.ThicknessDao;
import com.daogen.clotheme.WearPlace;
import com.daogen.clotheme.WearPlaceDao;
import com.logicalModelLayer.Interface.WearPlaceInfoInterface;

public class WearPlaceInfo implements WearPlaceInfoInterface {
	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "wearplace";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static WearPlaceInfo m_instance = null;  
	/*
	 * @brief 穿衣场合表map
	 * */
	private Map<Long,WearPlace> m_WearPlaceData = null;
	private WearPlaceDao m_WearPlaceDao = null;
	
	/*
	 * @brief  穿衣场合表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private WearPlaceInfo(Context context) {  	

    	m_WearPlaceData = new HashMap<Long,WearPlace>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_WearPlaceDao = 
    			AssetsDatabaseManager.getInstance(context).getWearPlaceDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static WearPlaceInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new WearPlaceInfo(context);
        }
        return m_instance;
    }  
    
    /*
     * @brief  加载数据库存放穿衣场合表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;

    	List<WearPlace> wearPlaceList = m_WearPlaceDao.loadAll();
    	if(wearPlaceList.size() == 0 || wearPlaceList.isEmpty()){
    		Log.w("WearPlace has no elements in Load Function!");
    	}
    	for(int i = 0; i < wearPlaceList.size(); i++)  
        {  
    		WearPlace wearPlace = new WearPlace();
    		wearPlace = wearPlaceList.get(i);  
            m_WearPlaceData.put(wearPlace.getId(), wearPlace);
        }  
    	return ret;
    }
    /* 查
     * @brief  从穿衣场合表map中获取指定key的value
     * @param  in_WearPlaceID 指定key
     * @param  out_WearPlace value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int getWearPlace(long in_WearPlaceID,WearPlace out_WearPlace){
    	if (m_WearPlaceData == null || in_WearPlaceID < 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_WearPlaceData.isEmpty() == true) {
			Log.w("m_WearPlaceData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_WearPlaceData.containsKey(in_WearPlaceID) == true) {
			out_WearPlace = m_WearPlaceData.get(in_WearPlaceID);
			if (out_WearPlace == null) {
				Log.w("WearPlace get failed");
				return CommonDefine.SYSTEM_ERROR;
			}
		} else {
			Log.w("m_WearPlaceData does not contains key "
					+ String.valueOf(in_WearPlaceID));
			return CommonDefine.SYSTEM_ERROR;
		}
		return CommonDefine.NO_ERROR;
    }
    /* 查
     * @brief  根据WearPlace查询
     * @param  in_WearPlace 指定key
     * @param  out_WearPlace value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public WearPlace getWearPlace(String in_WearPlace){//,WearPlace out_WearPlace){
		if (m_WearPlaceData == null 
		|| in_WearPlace == null 
		|| in_WearPlace.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return null;//CommonDefine.SYSTEM_ERROR;
		}
		if (m_WearPlaceData.isEmpty() == true) {
			Log.w("m_WearPlaceData is Empty");
			return null;//CommonDefine.SYSTEM_ERROR;
		}

		for (WearPlace value : m_WearPlaceData.values()) {
			if (value.getWearPlace().equals(in_WearPlace)) {
//				out_WearPlace = value;
				return value;//CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_WearPlaceData does not contains " + in_WearPlace);
		return null;//CommonDefine.SYSTEM_ERROR;
	}
	
	/* 
     * @brief  返回所有的穿衣场合
     * @param  无
     * @return 成功 所有的穿衣场合字符串数组 失败 null
     * */
	public ArrayList<String> getAllWearPlace(){
		if (m_WearPlaceData == null) {
			Log.w("DataSource is null, return SYSTEM_ERROR");
			return null;
		}
		if (m_WearPlaceData.isEmpty() == true) {
			Log.w("m_WearPlaceData is Empty");
			return null;
		}

//		ArrayList al=new ArrayList(); 
		ArrayList<String> als = new ArrayList<String>(0);
		for (WearPlace value : m_WearPlaceData.values()) {
			als.add(value.getWearPlace());
		}

		return als;
	}
	
	/* 增，改
     * @brief  设置map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_WearPlaceID 指定key
     * @param  in_WearPlace 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setWearPlace(long in_WearPlaceID,WearPlace in_WearPlace){
		if (m_WearPlaceData == null || in_WearPlace == null || in_WearPlaceID < 0) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_WearPlaceData.containsKey(in_WearPlaceID) == true) {
			Log.w("in_WearPlaceID " + String.valueOf(in_WearPlaceID)
					+ " is already exsists");

			WearPlace wearPlace;
			wearPlace = m_WearPlaceData.get(in_WearPlaceID);
			in_WearPlace.setId(wearPlace.getId());
			m_WearPlaceDao.update(in_WearPlace);

			if (m_WearPlaceData.remove(in_WearPlaceID) == null) {
				Log.w("WearPlace remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}

			if (m_WearPlaceData.put(in_WearPlaceID, in_WearPlace) == null) {
				Log.w("WearPlace put failed");
//				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_WearPlace.setId(GetMaxID());

		if (m_WearPlaceData.put(in_WearPlaceID, in_WearPlace) == null) {
			Log.w("WearPlace put failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_WearPlaceDao.insert(in_WearPlace);
		if (retDao != 0) {
			Log.e("m_WearPlaceDao insert failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_WearPlace 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setWearPlace(WearPlace in_WearPlace){
		if (m_WearPlaceData == null || in_WearPlace == null) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		WearPlace out_WearPlace = getWearPlace(in_WearPlace.getWearPlace()) ;
		if (out_WearPlace != null) {
			Log.w("WearPlace " + in_WearPlace.getWearPlace()
					+ " is already exsists");

			in_WearPlace.setId(out_WearPlace.getId());
			m_WearPlaceDao.update(in_WearPlace);

			if (m_WearPlaceData.remove(in_WearPlace.getId()) == null) {
				Log.w("WearPlace remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			if (m_WearPlaceData.put(in_WearPlace.getId(), in_WearPlace) == null) {
				Log.w("WearPlace put failed");
//				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_WearPlace.setId(GetMaxID());

		if (m_WearPlaceData.put(in_WearPlace.getId(), in_WearPlace) == null) {
			Log.w("WearPlace put failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_WearPlaceDao.insert(in_WearPlace);
		if (retDao != 0) {
			Log.e("m_WearPlaceDao insert failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_WearPlace 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setWearPlace(String in_WearPlace){
		WearPlace wearPlace = new WearPlace();
		wearPlace.setWearPlace(in_WearPlace);
		return setWearPlace(wearPlace);
	}
	/* 删
     * @brief  从map中删除指定key的value
     * @param  in_WearPlaceID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeWearPlace(long in_WearPlaceID){
		if (m_WearPlaceData == null || in_WearPlaceID < 0) {
			Log.w("param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_WearPlaceData.isEmpty() == true) {
			Log.w("m_WearPlaceData is Empty");
			return CommonDefine.NO_ERROR;
		}
		if (m_WearPlaceData.containsKey(in_WearPlaceID) == true) {
			if (m_WearPlaceData.remove(in_WearPlaceID) == null) {
				Log.w("remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			m_WearPlaceDao.deleteByKey(in_WearPlaceID);
			// m_WearPlaceDao.delete(entity);
		} else {
			Log.w("m_WearPlaceData does not contains key "
					+ String.valueOf(in_WearPlaceID));
			return CommonDefine.NO_ERROR;
		}
		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定的WearPlace
     * @param  in_WearPlace 指定的WearPlace
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeWearPlace(String in_WearPlace){
		if (m_WearPlaceData == null 
		|| in_WearPlace == null 
		|| in_WearPlace.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_WearPlaceData.isEmpty() == true) {
			Log.w("m_WearPlaceData is Empty");
			return CommonDefine.NO_ERROR;
		}

		for (WearPlace value : m_WearPlaceData.values()) {
			if (value.getWearPlace().equals(in_WearPlace)) {
				m_WearPlaceDao.deleteByKey(value.getId());

				if (m_WearPlaceData.remove(value.getId()) == null) {
					Log.w("remove failed");
					return CommonDefine.SYSTEM_ERROR;
				}

				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_WearPlaceData does not contains WearPlace " + in_WearPlace);
		return CommonDefine.NO_ERROR;
	}
    
    /* 
     * @brief  获取最大的ID值
     * @param  无
     * @return 成功 最大的ID值 失败 -1
     * */
	private long GetMaxID(){
    	
    	long ret = -2;
    	
    	if(m_WearPlaceData == null){
           	Log.w("param invalid, return SYSTEM_ERROR");
           	return 0;
       	}
       	if(m_WearPlaceData.isEmpty() == true){
           	Log.w("m_CategoryData is Empty");
       		return 0;
       	}
       	
       	for (WearPlace value : m_WearPlaceData.values()) {
       		if(value.getId() > ret){
       			ret = value.getId();
       		}
       	}
       	
       	ret = ret + 1;
       	
       	return ret;
    }
}
