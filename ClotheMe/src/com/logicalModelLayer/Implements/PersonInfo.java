package com.logicalModelLayer.Implements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.common.clothe.CommonDefine;
import com.common.clothe.Log;
import com.daogen.clotheme.Meterial;
import com.daogen.clotheme.MeterialDao;
import com.daogen.clotheme.PersonInformation;
import com.daogen.clotheme.PersonInformationDao;
import com.logicalModelLayer.Interface.PersonInfoInterface;

public class PersonInfo implements PersonInfoInterface {
	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "personinformation";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static PersonInfo m_instance = null;  
	/*
	 * @brief 人员信息表map
	 * */
	private Map<Long,PersonInformation> m_PersonInformationData = null;
	private PersonInformationDao m_PersonInformationDao = null;
	
	/*
	 * @brief  人员信息表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private PersonInfo(Context context) {  	

    	m_PersonInformationData = new HashMap<Long,PersonInformation>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_PersonInformationDao = 
    			AssetsDatabaseManager.getInstance(context).getPersonInformationDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static PersonInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new PersonInfo(context);
        }
        return m_instance;
    }  
    
    /*
     * @brief  加载数据库人员信息表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;

    	List<PersonInformation> personInformationList = m_PersonInformationDao.loadAll();
    	if(personInformationList.size() == 0 || personInformationList.isEmpty()){
    		Log.w("PersonInformation has no elements in Load Function!");
    	}
    	for(int i = 0; i < personInformationList.size(); i++)  
        {  
    		PersonInformation personInformation = new PersonInformation();
    		personInformation = personInformationList.get(i);  
            m_PersonInformationData.put(personInformation.getId(), personInformation);
        }  
    	return ret;
    }
    /* 查
     * @brief  从人员信息表map中获取指定key的value
     * @param  in_PersonID 指定key
     * @param  out_PersonInformation value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int getPerson(long in_PersonID,PersonInformation out_PersonInformation){
		if (m_PersonInformationData == null || in_PersonID < 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_PersonInformationData.isEmpty() == true) {
			Log.w("m_PersonInformationData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_PersonInformationData.containsKey(in_PersonID) == true) {
			out_PersonInformation = m_PersonInformationData.get(in_PersonID);
			if (out_PersonInformation == null) {
				Log.w("PersonInformation get failed");
				return CommonDefine.SYSTEM_ERROR;
			}
		} else {
			Log.w("m_PersonInformationData does not contains key "
					+ String.valueOf(in_PersonID));
			return CommonDefine.SYSTEM_ERROR;
		}
		return CommonDefine.NO_ERROR;
    }
    /* 查
     * @brief  根据PersonName查询
     * @param  in_PersonName 指定key
     * @param  out_PersonInformation value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int getPerson(String in_PersonName,PersonInformation out_PersonInformation){
		if (m_PersonInformationData == null 
		|| in_PersonName == null
		|| in_PersonName.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_PersonInformationData.isEmpty() == true) {
			Log.w("m_PersonInformationData is Empty");
			return CommonDefine.SYSTEM_ERROR;
		}

		for (PersonInformation value : m_PersonInformationData.values()) {
			if (value.getPersonName().equals(in_PersonName)) {
				out_PersonInformation = value;
				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_PersonInformationData does not contains " + in_PersonName);
		return CommonDefine.SYSTEM_ERROR;
	}
	/* 增，改
     * @brief  设置map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_PersonID 指定key
     * @param  in_PersonInformation 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setPerson(long in_PersonID,PersonInformation in_PersonInformation){
		if (m_PersonInformationData == null 
		|| in_PersonInformation == null 
		|| in_PersonID < 0) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_PersonInformationData.containsKey(in_PersonID) == true) {
			Log.w("in_PersonID " + String.valueOf(in_PersonID)
					+ " is already exsists");

			PersonInformation personInformation;
			personInformation = m_PersonInformationData.get(in_PersonID);
			in_PersonInformation.setId(personInformation.getId());
			m_PersonInformationDao.update(in_PersonInformation);

			if (m_PersonInformationData.remove(in_PersonID) == null) {
				Log.w("PersonInformation remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}

			if (m_PersonInformationData.put(in_PersonID, in_PersonInformation) == null) {
				Log.w("PersonInformation put failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_PersonInformation.setId(GetMaxID());

		if (m_PersonInformationData.put(in_PersonID, in_PersonInformation) == null) {
			Log.w("PersonInformation put failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_PersonInformationDao.insert(in_PersonInformation);
		if (retDao != 0) {
			Log.e("m_PersonInformationDao insert failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 增，改
     * @brief  设置map中的value,如果已存在,则删除,添加新值
     * @param  in_PersonInformation 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setPerson(PersonInformation in_PersonInformation){
		if (m_PersonInformationData == null || in_PersonInformation == null) {
			Log.w("Param invalid,return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		PersonInformation out_PersonInformation = null;
		if (getPerson(in_PersonInformation.getPersonName(), out_PersonInformation) 
				== CommonDefine.NO_ERROR) {
			Log.w("PersonName " + in_PersonInformation.getPersonName()
					+ " is already exsists");

			in_PersonInformation.setId(out_PersonInformation.getId());
			m_PersonInformationDao.update(in_PersonInformation);

			if (m_PersonInformationData.remove(in_PersonInformation.getId()) == null) {
				Log.w("PersonInformation remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			if (m_PersonInformationData.put(in_PersonInformation.getId(), in_PersonInformation) == null) {
				Log.w("PersonInformation put failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			return CommonDefine.NO_ERROR;
		}

		in_PersonInformation.setId(GetMaxID());

		if (m_PersonInformationData.put(in_PersonInformation.getId(), in_PersonInformation) == null) {
			Log.w("PersonInformation put failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		long retDao = m_PersonInformationDao.insert(in_PersonInformation);
		if (retDao != 0) {
			Log.e("m_PersonInformationDao insert failed");
			return CommonDefine.SYSTEM_ERROR;
		}

		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定key的value
     * @param  in_PersonID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removePerson(long in_PersonID){
		if (m_PersonInformationData == null || in_PersonID < 0) {
			Log.w("param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}
		if (m_PersonInformationData.isEmpty() == true) {
			Log.w("m_PersonInformationData is Empty");
			return CommonDefine.NO_ERROR;
		}
		if (m_PersonInformationData.containsKey(in_PersonID) == true) {
			if (m_PersonInformationData.remove(in_PersonID) == null) {
				Log.w("remove failed");
				return CommonDefine.SYSTEM_ERROR;
			}
			m_PersonInformationDao.deleteByKey(in_PersonID);
			// m_PersonInformationDao.delete(entity);
		} else {
			Log.w("m_PersonInformationData does not contains key "
					+ String.valueOf(in_PersonID));
			return CommonDefine.NO_ERROR;
		}
		return CommonDefine.NO_ERROR;
	}
	/* 删
     * @brief  从map中删除指定的PersonName
     * @param  in_PersonName 指定的PersonName
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removePerson(String in_PersonName){
		if (m_PersonInformationData == null 
		|| in_PersonName == null
		|| in_PersonName.length() == 0) {
			Log.w("Param invalid, return SYSTEM_ERROR");
			return CommonDefine.SYSTEM_ERROR;
		}

		if (m_PersonInformationData.isEmpty() == true) {
			Log.w("m_PersonInformationData is Empty");
			return CommonDefine.NO_ERROR;
		}

		for (PersonInformation value : m_PersonInformationData.values()) {
			if (value.getPersonName().equals(in_PersonName)) {
				m_PersonInformationDao.deleteByKey(value.getId());

				if (m_PersonInformationData.remove(value.getId()) == null) {
					Log.w("remove failed");
					return CommonDefine.SYSTEM_ERROR;
				}

				return CommonDefine.NO_ERROR;
			}
		}

		Log.w("m_PersonInformationData does not contains PersonInformation " + in_PersonName);
		return CommonDefine.NO_ERROR;
	}
    
    /* 
     * @brief  获取最大的ID值
     * @param  无
     * @return 成功 最大的ID值 失败 -1
     * */
	private long GetMaxID(){
    	
    	long ret = -2;
    	
    	if(m_PersonInformationData == null){
           	Log.w("param invalid, return SYSTEM_ERROR");
           	return 0;
       	}
       	if(m_PersonInformationData.isEmpty() == true){
           	Log.w("m_CategoryData is Empty");
       		return 0;
       	}
       	
       	for (PersonInformation value : m_PersonInformationData.values()) {
       		if(value.getId() > ret){
       			ret = value.getId();
       		}
       	}
       	
       	ret = ret + 1;
       	
       	return ret;
    }
}
