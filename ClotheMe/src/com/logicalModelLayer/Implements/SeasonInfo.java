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
import com.daogen.clotheme.PersonInformationDao;
import com.daogen.clotheme.Season;
import com.daogen.clotheme.SeasonDao;
import com.logicalModelLayer.Interface.SeasonInfoInterface;

public class SeasonInfo implements SeasonInfoInterface {
	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "season";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static SeasonInfo m_instance = null;  
	/*
	 * @brief 季节表map
	 * */
	private Map<Long,Season> m_SeasonData = null;
	private SeasonDao m_SeasonDao = null;
	
	/*
	 * @brief  季节表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private SeasonInfo(Context context) {  	

    	m_SeasonData = new HashMap<Long,Season>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_SeasonDao = 
    			AssetsDatabaseManager.getInstance(context).getSeasonDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static SeasonInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new SeasonInfo(context);
        }
        return m_instance;
    }  
    
    /*
     * @brief  加载数据库季节表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;

    	List<Season> seasonList = m_SeasonDao.loadAll();
    	if(seasonList.size() == 0 || seasonList.isEmpty()){
    		Log.w("Season has no elements in Load Function!");
    	}
    	for(int i = 0; i < seasonList.size(); i++)  
        {  
    		Season season = new Season();
    		season = seasonList.get(i);  
            m_SeasonData.put(season.getId(), season);
        }  
    	return ret;
    }
    /* 查
     * @brief  从季节表map中获取指定key的value
     * @param  in_SeasonID 指定key
     * @param  out_Season value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int getSeason(long in_SeasonID,Season out_Season){
    	if (m_SeasonData == null || in_SeasonID < 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_SeasonData.isEmpty() == true) {
			Log.w("m_SeasonData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_SeasonData.containsKey(in_SeasonID) == true) {
			out_Season = m_SeasonData.get(in_SeasonID);
			if (out_Season == null) {
				Log.w("Season get failed");
				return CommonDefine.SYSTEM_ERROR;
			}
		} else {
			Log.w("m_SeasonData does not contains key "
					+ String.valueOf(in_SeasonID));
			return CommonDefine.SYSTEM_ERROR;
		}
		return CommonDefine.NO_ERROR;
    }
    /* 查
     * @brief  根据SeasonName查询
     * @param  in_SeasonName 指定key
     * @param  out_Season value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public Season getSeason(String in_SeasonName){//, out_Season){
		if (m_SeasonData == null 
		|| in_SeasonName == null
		|| in_SeasonName.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return null;//CommonDefine.SYSTEM_ERROR;
		}
		if (m_SeasonData.isEmpty() == true) {
			Log.w("m_SeasonData is Empty");
			return null;//CommonDefine.SYSTEM_ERROR;
		}

		for (Season value : m_SeasonData.values()) {
			if (value.getSeason().equals(in_SeasonName)) {
				//out_Season = value;
				return value;//CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_SeasonData does not contains " + in_SeasonName);
		return null;//CommonDefine.SYSTEM_ERROR;
	}
	
	/* 
     * @brief  返回所有的季节
     * @param  无
     * @return 成功 所有的季节字符串数组 失败 null
     * */
	public ArrayList<String> getAllSeason(){
		if (m_SeasonData == null) {
			Log.w("DataSource is null, return SYSTEM_ERROR");
			return null;
		}
		if (m_SeasonData.isEmpty() == true) {
			Log.w("m_SeasonData is Empty");
			return null;
		}

//		ArrayList al=new ArrayList(); 
		ArrayList<String> als = new ArrayList<String>(0);
		for (Season value : m_SeasonData.values()) {
			als.add(value.getSeason());
		}

		return als;
	}
	
	/* 增，改
     * @brief  设置map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_SeasonID 指定key
     * @param  in_Season 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setSeason(long in_SeasonID,Season in_Season){
		if (m_SeasonData == null 
		|| in_Season == null
		|| in_SeasonID < 0) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_SeasonData.containsKey(in_SeasonID) == true) {
			Log.w("in_SeasonID " + String.valueOf(in_SeasonID)
					+ " is already exsists");

			Season season;
			season = m_SeasonData.get(in_SeasonID);
			in_Season.setId(season.getId());
			m_SeasonDao.update(in_Season);

			if (m_SeasonData.remove(in_SeasonID) == null) {
				Log.w("Season remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}

			if (m_SeasonData.put(in_SeasonID, in_Season) == null) {
				Log.w("Season put failed");
//				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_Season.setId(GetMaxID());

		if (m_SeasonData.put(in_SeasonID, in_Season) == null) {
			Log.w("Season put failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_SeasonDao.insert(in_Season);
		if (retDao != 0) {
			Log.e("m_SeasonDao insert failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_Season 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setSeason(Season in_Season){
		if (m_SeasonData == null || in_Season == null) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		Season out_Season = getSeason(in_Season.getSeason()) ;
		if (out_Season != null) {
			Log.w("Season " + in_Season.getSeason()
					+ " is already exsists");

			in_Season.setId(out_Season.getId());
			m_SeasonDao.update(in_Season);

			if (m_SeasonData.remove(in_Season.getId()) == null) {
				Log.w("Season remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			if (m_SeasonData.put(in_Season.getId(), in_Season) == null) {
				Log.w("Season put failed");
//				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_Season.setId(GetMaxID());

		if (m_SeasonData.put(in_Season.getId(), in_Season) == null) {
			Log.w("Season put failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_SeasonDao.insert(in_Season);
		if (retDao != 0) {
			Log.e("m_SeasonDao insert failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定key的value
     * @param  in_SeasonID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeSeason(long in_SeasonID){
		if (m_SeasonData == null || in_SeasonID < 0) {
			Log.w("param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_SeasonData.isEmpty() == true) {
			Log.w("m_SeasonData is Empty");
			return CommonDefine.NO_ERROR;
		}
		if (m_SeasonData.containsKey(in_SeasonID) == true) {
			if (m_SeasonData.remove(in_SeasonID) == null) {
				Log.w("remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			m_SeasonDao.deleteByKey(in_SeasonID);
			// m_SeasonDao.delete(entity);
		} else {
			Log.w("m_SeasonData does not contains key "
					+ String.valueOf(in_SeasonID));
			return CommonDefine.NO_ERROR;
		}
		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定的SeasonName
     * @param  in_SeasonName 指定的SeasonName
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeSeason(String in_SeasonName){
		if (m_SeasonData == null 
		|| in_SeasonName == null
		|| in_SeasonName.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_SeasonData.isEmpty() == true) {
			Log.w("m_SeasonData is Empty");
			return CommonDefine.NO_ERROR;
		}

		for (Season value : m_SeasonData.values()) {
			if (value.getSeason().equals(in_SeasonName)) {
				m_SeasonDao.deleteByKey(value.getId());

				if (m_SeasonData.remove(value.getId()) == null) {
					Log.w("remove failed");
					return CommonDefine.SYSTEM_ERROR;
				}

				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_SeasonData does not contains Season "
				+ in_SeasonName);
		return CommonDefine.NO_ERROR;
	}
    
    /* 
     * @brief  获取最大的ID值
     * @param  无
     * @return 成功 最大的ID值 失败 -1
     * */
	private long GetMaxID(){
    	
    	long ret = -2;
    	
    	if(m_SeasonData == null){
           	Log.w("param invalid, return SYSTEM_ERROR");
           	return 0;
       	}
       	if(m_SeasonData.isEmpty() == true){
           	Log.w("m_CategoryData is Empty");
       		return 0;
       	}
       	
       	for (Season value : m_SeasonData.values()) {
       		if(value.getId() > ret){
       			ret = value.getId();
       		}
       	}
       	
       	ret = ret + 1;
       	
       	return ret;
    }
}
