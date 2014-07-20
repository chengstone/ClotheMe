package com.logicalModelLayer.Implements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.common.clothe.CommonDefine;
import com.common.clothe.Log;
import com.daogen.clotheme.Category;
import com.daogen.clotheme.CategoryDao;
import com.daogen.clotheme.Meterial;
import com.daogen.clotheme.MeterialDao;
import com.logicalModelLayer.Interface.MeterialInfoInterface;

public class MeterialInfo implements MeterialInfoInterface{
	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "meterial";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static MeterialInfo m_instance = null;  
	/*
	 * @brief 物品表map
	 * */
	private Map<Long,Meterial> m_MeterialData = null;
	private MeterialDao m_MeterialDao = null;
	
	/*
	 * @brief  物品表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private MeterialInfo(Context context) {  	

    	m_MeterialData = new HashMap<Long,Meterial>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_MeterialDao = AssetsDatabaseManager.getInstance(context).getMeterialDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static MeterialInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new MeterialInfo(context);
        }
        return m_instance;
    }  
    
    /** 
     * @brief  取得MeterialDao 
     *  
     * @param  
     * @return MeterialDao
     */ 
//    public MeterialDao getMeterialDao(){
//    	return m_MeterialDao;  
//    }
    
    /*
     * @brief  加载数据库物品表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;

    	List<Meterial> meterialList = m_MeterialDao.loadAll();
    	if(meterialList.size() == 0 || meterialList.isEmpty()){
    		Log.w("Meterial has no elements in Load Function!");
    	}
    	for(int i = 0; i < meterialList.size(); i++)  
        {  
    		Meterial meterial = new Meterial();
    		meterial = meterialList.get(i);  
            m_MeterialData.put(meterial.getId(), meterial);
        }  
    	return ret;
    }
    /* 查
     * @brief  从物品表map中获取指定key的value
     * @param  in_MeterialID 指定key
     * @param  out_Meterial value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int getMeterial(long in_MeterialID,Meterial out_Meterial){
		if (m_MeterialData == null
		|| in_MeterialID < 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_MeterialData.isEmpty() == true) {
			Log.w("m_MeterialData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_MeterialData.containsKey(in_MeterialID) == true) {
			out_Meterial = m_MeterialData.get(in_MeterialID);
			if (out_Meterial == null) {
				Log.w("Meterial get failed");
				return CommonDefine.SYSTEM_ERROR;
			}
		} else {
			Log.w("m_MeterialData does not contains key "
					+ String.valueOf(in_MeterialID));
			return CommonDefine.SYSTEM_ERROR;
		}
		return CommonDefine.NO_ERROR;
    }
    /* 查
     * @brief  根据Description查询
     * @param  in_Description 指定key
     * @param  out_Meterial value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int getMeterial(String in_Description,Meterial out_Meterial){
		if (m_MeterialData == null
		|| in_Description == null 
		|| in_Description.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_MeterialData.isEmpty() == true) {
			Log.w("m_MeterialData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}

		for (Meterial value : m_MeterialData.values()) {
			if (value.getDescription().equals(in_Description)) {
				out_Meterial = value;
				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_MeterialData does not contains " + in_Description);
		return CommonDefine.SYSTEM_ERROR;
	}
	/* 增，改
     * @brief  设置map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_MeterialID 指定key
     * @param  in_Meterial 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setMeterial(long in_MeterialID,Meterial in_Meterial){
		if (m_MeterialData == null || in_Meterial == null || in_MeterialID < 0) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_MeterialData.containsKey(in_MeterialID) == true) {
			Log.w("in_MeterialID " + String.valueOf(in_MeterialID)
					+ " is already exsists");

			Meterial meterial;
			meterial = m_MeterialData.get(in_MeterialID);
//			meterial.setBelongCategoryID(in_Meterial.getBelongCategoryID());
//			meterial.setIsSubCategory(in_Meterial.getIsSubCategory());
//			meterial.setName(in_Meterial.getName());
			in_Meterial.setId(meterial.getId());
			m_MeterialDao.update(in_Meterial);

			if (m_MeterialData.remove(in_MeterialID) == null) {
				Log.w("Meterial remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}

			if (m_MeterialData.put(in_MeterialID, in_Meterial) == null) {
				Log.w("Meterial put failed");
//				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_Meterial.setId(GetMaxID());

		if (m_MeterialData.put(in_MeterialID, in_Meterial) == null) {
			Log.w("Meterial put failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_MeterialDao.insert(in_Meterial);
		if (retDao != 0) {
			Log.e("m_MeterialDao insert failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_Meterial 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setMeterial(Meterial in_Meterial){
		if (m_MeterialData == null || in_Meterial == null) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		Meterial out_Meterial = null;
		if (getMeterial(in_Meterial.getDescription(), out_Meterial) 
				== CommonDefine.NO_ERROR) {
			Log.w("Description " + in_Meterial.getDescription()
					+ " is already exsists");

//			out_Meterial.setBelongCategoryID(in_Meterial.getBelongCategoryID());
//			out_Meterial.setIsSubCategory(in_Meterial.getIsSubCategory());
//			out_Meterial.setName(in_Meterial.getName());
			in_Meterial.setId(out_Meterial.getId());
			m_MeterialDao.update(in_Meterial);

			if (m_MeterialData.remove(in_Meterial.getId()) == null) {
				Log.w("Meterial remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			if (m_MeterialData.put(in_Meterial.getId(), in_Meterial) == null) {
				Log.w("Meterial put failed");
//				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_Meterial.setId(GetMaxID());

		if (m_MeterialData.put(in_Meterial.getId(), in_Meterial) == null) {
			Log.w("Meterial put failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_MeterialDao.insert(in_Meterial);
		if (retDao != 0) {
			Log.e("m_MeterialDao insert failed");
//			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定key的value
     * @param  in_MeterialID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeMeterial(long in_MeterialID){
		if (m_MeterialData == null || in_MeterialID < 0) {
			Log.w("param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_MeterialData.isEmpty() == true) {
			Log.w("m_MeterialData is Empty");
			return CommonDefine.NO_ERROR;
		}
		if (m_MeterialData.containsKey(in_MeterialID) == true) {
			if (m_MeterialData.remove(in_MeterialID) == null) {
				Log.w("remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			m_MeterialDao.deleteByKey(in_MeterialID);
			// m_MeterialDao.delete(entity);
		} else {
			Log.w("m_MeterialData does not contains key "
					+ String.valueOf(in_MeterialID));
			return CommonDefine.NO_ERROR;
		}
		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定的Description
     * @param  in_Description 指定的Description
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeMeterial(String in_Description){
		if (m_MeterialData == null 
		|| in_Description == null
		|| in_Description.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_MeterialData.isEmpty() == true) {
			Log.w("m_MeterialData is Empty");
			return CommonDefine.NO_ERROR;
		}

		for (Meterial value : m_MeterialData.values()) {
			if (value.getDescription().equals(in_Description)) {
				m_MeterialDao.deleteByKey(value.getId());

				if (m_MeterialData.remove(value.getId()) == null) {
					Log.w("remove failed");
					return CommonDefine.SYSTEM_ERROR;
				}

				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_MeterialData does not contains Description "
				+ in_Description);
		return CommonDefine.NO_ERROR;
	}
	
	/* 
     * @brief  获取最大的ID值
     * @param  无
     * @return 成功 最大的ID值 失败 -1
     * */
	private long GetMaxID(){
    	
    	long ret = -2;
    	
    	if(m_MeterialData == null){
           	Log.w("param invalid, return SYSTEM_ERROR");
           	return 0;
       	}
       	if(m_MeterialData.isEmpty() == true){
           	Log.w("m_CategoryData is Empty");
       		return 0;
       	}
       	
       	for (Meterial value : m_MeterialData.values()) {
       		if(value.getId() > ret){
       			ret = value.getId();
       		}
       	}
       	
       	ret = ret + 1;
       	
       	return ret;
    }
}
