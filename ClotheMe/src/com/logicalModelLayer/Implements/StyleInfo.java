package com.logicalModelLayer.Implements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.common.clothe.CommonDefine;
import com.common.clothe.Log;
import com.daogen.clotheme.Season;
import com.daogen.clotheme.StorageLocation;
import com.daogen.clotheme.StorageLocationDao;
import com.daogen.clotheme.Style;
import com.daogen.clotheme.StyleDao;
import com.logicalModelLayer.Interface.StyleInfoInterface;

public class StyleInfo implements StyleInfoInterface {
	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "style";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static StyleInfo m_instance = null;  
	/*
	 * @brief 风格表map
	 * */
	private Map<Long,Style> m_StyleData = null;
	private StyleDao m_StyleDao = null;
	
	/*
	 * @brief  风格表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private StyleInfo(Context context) {  	

    	m_StyleData = new HashMap<Long,Style>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_StyleDao = 
    			AssetsDatabaseManager.getInstance(context).getStyleDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static StyleInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new StyleInfo(context);
        }
        return m_instance;
    }  
    
    /*
     * @brief  加载数据库存放风格表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;

    	List<Style> styleList = m_StyleDao.loadAll();
    	if(styleList.size() == 0 || styleList.isEmpty()){
    		Log.w("Style has no elements in Load Function!");
    	}
    	for(int i = 0; i < styleList.size(); i++)  
        {  
    		Style style = new Style();
    		style = styleList.get(i);  
            m_StyleData.put(style.getId(), style);
        }  
    	return ret;
    }
    /* 查
     * @brief  从风格表map中获取指定key的value
     * @param  in_StyleID 指定key
     * @param  out_Style value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int getStyle(long in_StyleID,Style out_Style){
    	if (m_StyleData == null || in_StyleID < 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_StyleData.isEmpty() == true) {
			Log.w("m_StyleData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_StyleData.containsKey(in_StyleID) == true) {
			out_Style = m_StyleData.get(in_StyleID);
			if (out_Style == null) {
				Log.w("Style get failed");
				return CommonDefine.SYSTEM_ERROR;
			}
		} else {
			Log.w("m_StyleData does not contains key "
					+ String.valueOf(in_StyleID));
			return CommonDefine.SYSTEM_ERROR;
		}
		return CommonDefine.NO_ERROR;
    }
    /* 查
     * @brief  根据Style查询
     * @param  in_Style 指定key
     * @param  out_Style value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int getStyle(String in_Style,Style out_Style){
		if (m_StyleData == null 
		|| in_Style == null
		|| in_Style.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_StyleData.isEmpty() == true) {
			Log.w("m_StyleData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}

		for (Style value : m_StyleData.values()) {
			if (value.getStyle().equals(in_Style)) {
				out_Style = value;
				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_StyleData does not contains " + in_Style);
		return CommonDefine.SYSTEM_ERROR;
	}
	/* 增，改
     * @brief  设置map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_StyleID 指定key
     * @param  in_Style 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setStyle(long in_StyleID,Style in_Style){
		if (m_StyleData == null || in_Style == null || in_StyleID < 0) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_StyleData.containsKey(in_StyleID) == true) {
			Log.w("in_StyleID " + String.valueOf(in_StyleID)
					+ " is already exsists");

			Style style;
			style = m_StyleData.get(in_StyleID);
			in_Style.setId(style.getId());
			m_StyleDao.update(in_Style);

			if (m_StyleData.remove(in_StyleID) == null) {
				Log.w("Style remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}

			if (m_StyleData.put(in_StyleID, in_Style) == null) {
				Log.w("Style put failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_Style.setId(GetMaxID());

		if (m_StyleData.put(in_StyleID, in_Style) == null) {
			Log.w("Style put failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_StyleDao.insert(in_Style);
		if (retDao != 0) {
			Log.e("m_StyleDao insert failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_Style 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setStyle(Style in_Style){
		if (m_StyleData == null || in_Style == null) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		Style out_Style = null;
		if (getStyle(in_Style.getStyle(), out_Style) 
				== CommonDefine.NO_ERROR) {
			Log.w("Style " + in_Style.getStyle()
					+ " is already exsists");

			in_Style.setId(out_Style.getId());
			m_StyleDao.update(in_Style);

			if (m_StyleData.remove(in_Style.getId()) == null) {
				Log.w("Style remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			if (m_StyleData.put(in_Style.getId(), in_Style) == null) {
				Log.w("Style put failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_Style.setId(GetMaxID());

		if (m_StyleData.put(in_Style.getId(), in_Style) == null) {
			Log.w("Style put failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_StyleDao.insert(in_Style);
		if (retDao != 0) {
			Log.e("m_StyleDao insert failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_Style 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setStyle(String in_Style){
		Style style = new Style();
		style.setStyle(in_Style);
		return setStyle(style);
	}
	/* 删
     * @brief  从map中删除指定key的value
     * @param  in_StyleID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeStyle(long in_StyleID){
		if (m_StyleData == null || in_StyleID < 0) {
			Log.w("param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_StyleData.isEmpty() == true) {
			Log.w("m_StyleData is Empty");
			return CommonDefine.NO_ERROR;
		}
		if (m_StyleData.containsKey(in_StyleID) == true) {
			if (m_StyleData.remove(in_StyleID) == null) {
				Log.w("remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			m_StyleDao.deleteByKey(in_StyleID);
			// m_StyleDao.delete(entity);
		} else {
			Log.w("m_StyleData does not contains key "
					+ String.valueOf(in_StyleID));
			return CommonDefine.NO_ERROR;
		}
		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定的Style
     * @param  in_Style 指定的Style
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeStyle(String in_Style){
		if (m_StyleData == null 
		|| in_Style == null
		|| in_Style.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_StyleData.isEmpty() == true) {
			Log.w("m_StyleData is Empty");
			return CommonDefine.NO_ERROR;
		}

		for (Style value : m_StyleData.values()) {
			if (value.getStyle().equals(in_Style)) {
				m_StyleDao.deleteByKey(value.getId());

				if (m_StyleData.remove(value.getId()) == null) {
					Log.w("remove failed");
					return CommonDefine.SYSTEM_ERROR;
				}

				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_StyleData does not contains Style "
				+ in_Style);
		return CommonDefine.NO_ERROR;
	}
    
    /* 
     * @brief  获取最大的ID值
     * @param  无
     * @return 成功 最大的ID值 失败 -1
     * */
	private long GetMaxID(){
    	
    	long ret = -2;
    	
    	if(m_StyleData == null){
           	Log.w("param invalid, return SYSTEM_ERROR");
           	return 0;
       	}
       	if(m_StyleData.isEmpty() == true){
           	Log.w("m_CategoryData is Empty");
       		return 0;
       	}
       	
       	for (Style value : m_StyleData.values()) {
       		if(value.getId() > ret){
       			ret = value.getId();
       		}
       	}
       	
       	ret = ret + 1;
       	
       	return ret;
    }
}
