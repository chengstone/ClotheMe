package com.logicalModelLayer.Implements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.common.clothe.CommonDefine;
import com.common.clothe.Log;
import com.daogen.clotheme.Style;
import com.daogen.clotheme.StyleDao;
import com.daogen.clotheme.Thickness;
import com.daogen.clotheme.ThicknessDao;
import com.logicalModelLayer.Interface.ThicknessInfoInterface;

public class ThicknessInfo implements ThicknessInfoInterface {
	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "thickness";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static ThicknessInfo m_instance = null;  
	/*
	 * @brief 厚度表map
	 * */
	private Map<Long,Thickness> m_ThicknessData = null;
	private ThicknessDao m_ThicknessDao = null;
	
	/*
	 * @brief  厚度表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private ThicknessInfo(Context context) {  	

    	m_ThicknessData = new HashMap<Long,Thickness>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_ThicknessDao = 
    			AssetsDatabaseManager.getInstance(context).getThicknessDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static ThicknessInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new ThicknessInfo(context);
        }
        return m_instance;
    }  
    
    /*
     * @brief  加载数据库存放厚度表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;

    	List<Thickness> thicknessList = m_ThicknessDao.loadAll();
    	if(thicknessList.size() == 0 || thicknessList.isEmpty()){
    		Log.w("Thickness has no elements in Load Function!");
    	}
    	for(int i = 0; i < thicknessList.size(); i++)  
        {  
    		Thickness thickness = new Thickness();
    		thickness = thicknessList.get(i);  
            m_ThicknessData.put(thickness.getId(), thickness);
        }  
    	return ret;
    }
    /* 查
     * @brief  从厚度表map中获取指定key的value
     * @param  in_ThicknessID 指定key
     * @param  out_Thickness value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int getThickness(long in_ThicknessID,Thickness out_Thickness){
    	if (m_ThicknessData == null || in_ThicknessID < 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_ThicknessData.isEmpty() == true) {
			Log.w("m_ThicknessData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_ThicknessData.containsKey(in_ThicknessID) == true) {
			out_Thickness = m_ThicknessData.get(in_ThicknessID);
			if (out_Thickness == null) {
				Log.w("Thickness get failed");
				return CommonDefine.SYSTEM_ERROR;
			}
		} else {
			Log.w("m_ThicknessData does not contains key "
					+ String.valueOf(in_ThicknessID));
			return CommonDefine.SYSTEM_ERROR;
		}
		return CommonDefine.NO_ERROR;
    }
    
    /* 查
     * @brief  根据Thickness查询
     * @param  in_Thickness 指定厚度
     * @param  out_Thickness value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int getThickness(String in_Thickness,Thickness out_Thickness){
		if (m_ThicknessData == null 
		|| in_Thickness == null
		|| in_Thickness.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_ThicknessData.isEmpty() == true) {
			Log.w("m_ThicknessData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}

		for (Thickness value : m_ThicknessData.values()) {
			if (value.getThickness().equals(in_Thickness)) {
				out_Thickness = value;
				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_ThicknessData does not contains " + in_Thickness);
		return CommonDefine.SYSTEM_ERROR;
	}
    
    /* 增，改
     * @brief  设置map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_ThicknessID 指定key
     * @param  in_Thickness 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setThickness(long in_ThicknessID,Thickness in_Thickness){
		if (m_ThicknessData == null || in_Thickness == null || in_ThicknessID < 0) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_ThicknessData.containsKey(in_ThicknessID) == true) {
			Log.w("in_ThicknessID " + String.valueOf(in_ThicknessID)
					+ " is already exsists");

			Thickness thickness;
			thickness = m_ThicknessData.get(in_ThicknessID);
			in_Thickness.setId(thickness.getId());
			m_ThicknessDao.update(in_Thickness);

			if (m_ThicknessData.remove(in_ThicknessID) == null) {
				Log.w("Thickness remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}

			if (m_ThicknessData.put(in_ThicknessID, in_Thickness) == null) {
				Log.w("Thickness put failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_Thickness.setId(GetMaxID());

		if (m_ThicknessData.put(in_ThicknessID, in_Thickness) == null) {
			Log.w("Thickness put failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_ThicknessDao.insert(in_Thickness);
		if (retDao != 0) {
			Log.e("m_ThicknessDao insert failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_Thickness 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setThickness(Thickness in_Thickness){
		if (m_ThicknessData == null || in_Thickness == null) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		Thickness out_Thickness = null;
		if (getThickness(in_Thickness.getThickness(), out_Thickness) 
				== CommonDefine.NO_ERROR) {
			Log.w("Thickness " + in_Thickness.getThickness()
					+ " is already exsists");

			in_Thickness.setId(out_Thickness.getId());
			m_ThicknessDao.update(in_Thickness);

			if (m_ThicknessData.remove(in_Thickness.getId()) == null) {
				Log.w("Thickness remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			if (m_ThicknessData.put(in_Thickness.getId(), in_Thickness) == null) {
				Log.w("Thickness put failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_Thickness.setId(GetMaxID());

		if (m_ThicknessData.put(in_Thickness.getId(), in_Thickness) == null) {
			Log.w("Thickness put failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_ThicknessDao.insert(in_Thickness);
		if (retDao != 0) {
			Log.e("m_ThicknessDao insert failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定key的value
     * @param  in_ThicknessID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeThickness(long in_ThicknessID){
		if (m_ThicknessData == null || in_ThicknessID < 0) {
			Log.w("param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_ThicknessData.isEmpty() == true) {
			Log.w("m_ThicknessData is Empty");
			return CommonDefine.NO_ERROR;
		}
		if (m_ThicknessData.containsKey(in_ThicknessID) == true) {
			if (m_ThicknessData.remove(in_ThicknessID) == null) {
				Log.w("remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			m_ThicknessDao.deleteByKey(in_ThicknessID);
			// m_ThicknessDao.delete(entity);
		} else {
			Log.w("m_ThicknessData does not contains key "
					+ String.valueOf(in_ThicknessID));
			return CommonDefine.NO_ERROR;
		}
		return CommonDefine.NO_ERROR;
	}
    
    /* 
     * @brief  获取最大的ID值
     * @param  无
     * @return 成功 最大的ID值 失败 -1
     * */
	private long GetMaxID(){
    	
    	long ret = -2;
    	
    	if(m_ThicknessData == null){
           	Log.w("param invalid, return SYSTEM_ERROR");
           	return 0;
       	}
       	if(m_ThicknessData.isEmpty() == true){
           	Log.w("m_CategoryData is Empty");
       		return 0;
       	}
       	
       	for (Thickness value : m_ThicknessData.values()) {
       		if(value.getId() > ret){
       			ret = value.getId();
       		}
       	}
       	
       	ret = ret + 1;
       	
       	return ret;
    }
}
