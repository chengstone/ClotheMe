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
import com.daogen.clotheme.CategoryDao.Properties;
import com.logicalModelLayer.Interface.CategoryInfoInterface;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

public class CategoryInfo implements CategoryInfoInterface {

	/*
	 * @brief DB表名
	 * */
	private final String TABLENAME = "category";
	// Context of application  
    private Context context = null;  
	private SQLiteDatabase m_db = null;
	private static CategoryInfo m_instance = null;  
	/*
	 * @brief 分类表map
	 * */
	private Map<Long,Category> m_CategoryData = null;
	private CategoryDao m_CategoryDao = null;
	
	/*
	 * @brief  分类表类构造函数
	 * @param  context 上下文
	 * @return 无
	 * */
    private CategoryInfo(Context context) {  	

    	m_CategoryData = new HashMap<Long,Category>();
    	this.context = context;
//    	m_db = AssetsDatabaseManager.getInstance(context).getDatabase();
    	m_CategoryDao = AssetsDatabaseManager.getInstance(context).getCategoryDao();
    	Load();
    }  
    /*
     * @brief  获取本类单例
     * @param  context 上下文
     * @return 单例
     * */
    public static CategoryInfo getInstance(Context context) {  
        if(m_instance == null){
        	m_instance = new CategoryInfo(context);
        }
        return m_instance;
    }  
    
    /*
     * @brief  加载数据库分类表
     * @return 成功 NO_ERROR 失败 无
     * */
    public int Load(){
    	int ret = CommonDefine.NO_ERROR;

    	List<Category> categoryList = m_CategoryDao.loadAll();
    	if(categoryList.size() == 0 || categoryList.isEmpty()){
    		Log.w("Category has no elements in Load Function!");
    	}
    	for(int i = 0; i < categoryList.size(); i++)  
        {  
    		Category category = new Category();
    		category = categoryList.get(i);  
            m_CategoryData.put(category.getId(), category);
        }  
    	return ret;
    }
    
    /* 查
     * @brief  从分类表map中获取指定key的value
     * @param  in_CategoryID 指定key
     * @param  out_Category value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
    public int getCategory(long in_CategoryID,Category out_Category){
    	if(m_CategoryData == null
//    	|| out_Category == null
    	|| in_CategoryID < 0){
        	Log.w("Param invalid, return SYSTEM_ERROR");
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryData.isEmpty() == true){
    		Log.w("m_CategoryData is Empty");
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryData.containsKey(in_CategoryID) == true){
    		out_Category = m_CategoryData.get(in_CategoryID);
    		if(out_Category == null){
            	Log.w("Category get failed");
        		return CommonDefine.SYSTEM_ERROR;
    		}
    	}
    	else {
        	Log.w("m_CategoryData does not contains key " + String.valueOf(in_CategoryID));
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	return CommonDefine.NO_ERROR;
    }
    
    /* 查
     * @brief  根据分类Name查询
     * @param  in_CategoryName 指定key
     * @param  out_Category value将保存其中
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int getCategory(String in_CategoryName,Category out_Category){
		if(m_CategoryData == null
//    	|| out_Category == null
    	|| in_CategoryName == null
    	|| in_CategoryName.length() == 0){
        	Log.w("Param invalid, return SYSTEM_ERROR");
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryData.isEmpty() == true){
    		Log.w("m_CategoryData is Empty");
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	
    	for (Category value : m_CategoryData.values()) {
    		if(value.getName().equals(in_CategoryName)){
    			out_Category = value;
//        		if(out_Category == null){
//                	Log.w("Category get failed");
//    	      		return CommonDefine.SYSTEM_ERROR;
//        		}
        		return CommonDefine.NO_ERROR;
    		}
    	}
        
    	Log.w("m_CategoryData does not contains " + in_CategoryName);
    	return CommonDefine.SYSTEM_ERROR;
    	
	}
	
	/* 增，改
     * @brief  设置分类表map中指定key的value,如果该key已存在,则删除,添加新值
     * @param  in_CategoryID 指定key
     * @param  in_Category 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setCategory(long in_CategoryID,Category in_Category){
		if(m_CategoryData == null
    	|| in_Category == null
    	|| in_CategoryID < 0){
        	Log.w("Param invalid,return SYSTEM_ERROR");
	   		return CommonDefine.SYSTEM_ERROR;
	   	}

	  	if(m_CategoryData.containsKey(in_CategoryID) == true){
	    	Log.w("in_CategoryID " + String.valueOf(in_CategoryID) + " is already exsists");

	  		Category category;
	  		category = m_CategoryData.get(in_CategoryID);
	   		category.setBelongCategoryID(in_Category.getBelongCategoryID());
    		category.setIsSubCategory(in_Category.getIsSubCategory());
    		category.setName(in_Category.getName());
    		m_CategoryDao.update(category);
		        	
        	if(m_CategoryData.remove(in_CategoryID) == null){
        		Log.w("Category remove failed");
	       		return CommonDefine.SYSTEM_ERROR;
	   		}

	      	if(m_CategoryData.put(in_CategoryID,in_Category) == null){
	           	Log.w("Category put failed");
        		return CommonDefine.SYSTEM_ERROR;
        	}
	      	return CommonDefine.NO_ERROR;
		}
		    	
    	in_Category.setId(GetMaxID());

    	if(m_CategoryData.put(in_CategoryID,in_Category) == null){
	       	Log.w("Category put failed");
	   		return CommonDefine.SYSTEM_ERROR;
    	}

	   	long retDao = m_CategoryDao.insert(in_Category);
    	if(retDao != 0){
    		Log.e("m_CategoryDao insert failed");
	   		return CommonDefine.SYSTEM_ERROR;
	   	}
		    	
    	return CommonDefine.NO_ERROR;
	}
	
	/* 增，改
     * @brief  设置分类表map中的value,如果已存在,则删除,添加新值
     * @param  in_Category 将要写入map的value
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int setCategory(Category in_Category){
		if(m_CategoryData == null
		|| in_Category == null){
		    Log.w("Param invalid,return SYSTEM_ERROR");
		    return CommonDefine.SYSTEM_ERROR;
		}

		Category out_Category = null;
		if(getCategory(in_Category.getName(),out_Category) == CommonDefine.NO_ERROR){
		    Log.w("CategoryName " + in_Category.getName() + " is already exsists");
		    
		    out_Category.setBelongCategoryID(in_Category.getBelongCategoryID());
		    out_Category.setIsSubCategory(in_Category.getIsSubCategory());
		    out_Category.setName(in_Category.getName());
    		m_CategoryDao.update(out_Category);
		    
		    if(m_CategoryData.remove(in_Category.getId()) == null){
		        Log.w("Category remove failed");
		        return CommonDefine.SYSTEM_ERROR;
		    }
		    if(m_CategoryData.put(in_Category.getId(),in_Category) == null){
	           	Log.w("Category put failed");
        		return CommonDefine.SYSTEM_ERROR;
        	}
	      	return CommonDefine.NO_ERROR;
		}

		in_Category.setId(GetMaxID());

    	if(m_CategoryData.put(in_Category.getId(),in_Category) == null){
	       	Log.w("Category put failed");
	   		return CommonDefine.SYSTEM_ERROR;
    	}

	   	long retDao = m_CategoryDao.insert(in_Category);
    	if(retDao != 0){
    		Log.e("m_CategoryDao insert failed");
	   		return CommonDefine.SYSTEM_ERROR;
	   	}
		    	
    	return CommonDefine.NO_ERROR;
	}
	
	/* 删
     * @brief  从分类表map中删除指定key的value
     * @param  in_CategoryID 指定key
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeCategory(long in_CategoryID){
		if(m_CategoryData == null
    	|| in_CategoryID < 0){
        	Log.w("param invalid, return SYSTEM_ERROR");
    		return CommonDefine.SYSTEM_ERROR;
    	}
    	if(m_CategoryData.isEmpty() == true){
        	Log.w("m_CategoryData is Empty");
    		return CommonDefine.NO_ERROR;
    	}
    	if(m_CategoryData.containsKey(in_CategoryID) == true){
    		if(m_CategoryData.remove(in_CategoryID) == null){
            	Log.w("remove failed");
	       		return CommonDefine.SYSTEM_ERROR;
	   		}
//	   		QueryBuilder<Category> qb = m_CategoryDao.queryBuilder();
//    		DeleteQuery<Category> bd = qb.where(Properties.Id.eq(in_CategoryID)).buildDelete();
//		    		bd.executeDeleteWithoutDetachingEntities();
	   		m_CategoryDao.deleteByKey(in_CategoryID);
//    		m_CategoryDao.delete(entity);
    	}
    	else {
        	Log.w("m_CategoryData does not contains key " + String.valueOf(in_CategoryID));
    		return CommonDefine.NO_ERROR;
    	}
    	return CommonDefine.NO_ERROR;
	}
	
	/* 删
     * @brief  从分类表map中删除指定的分类Name
     * @param  in_CategoryName 指定的分类Name
     * @return 成功 NO_ERROR 失败 SYSTEM_ERROR
     * */
	public int removeCategory(String in_CategoryName){
		if(m_CategoryData == null
    	|| in_CategoryName == null
    	|| in_CategoryName.length() == 0){
        	Log.w("Param invalid, return SYSTEM_ERROR");
    		return CommonDefine.SYSTEM_ERROR;
    	}

    	if(m_CategoryData.isEmpty() == true){
        	Log.w("m_CategoryData is Empty");
    		return CommonDefine.NO_ERROR;
	   	}
    	
    	for (Category value : m_CategoryData.values()) {
    		if(value.getName().equals(in_CategoryName)){
    	   		m_CategoryDao.deleteByKey(value.getId());
    	   		
    			if(m_CategoryData.remove(value.getId()) == null){
    	           	Log.w("remove failed");
    	       		return CommonDefine.SYSTEM_ERROR;
    	   		}

        		return CommonDefine.NO_ERROR;
    		}
    	}

		Log.w("m_CategoryData does not contains CategoryName "
				+ in_CategoryName);
		return CommonDefine.NO_ERROR;

	}
	
	/* 
     * @brief  获取最大的ID值
     * @param  无
     * @return 成功 最大的ID值 失败 -1
     * */
	private long GetMaxID(){
    	
    	long ret = -2;
    	
    	if(m_CategoryData == null){
           	Log.w("param invalid, return SYSTEM_ERROR");
           	return 0;
       	}
       	if(m_CategoryData.isEmpty() == true){
           	Log.w("m_CategoryData is Empty");
       		return 0;
       	}
       	
       	for (Category value : m_CategoryData.values()) {
       		if(value.getId() > ret){
       			ret = value.getId();
       		}
       	}
       	
       	ret = ret + 1;
       	
       	return ret;
    }
}
