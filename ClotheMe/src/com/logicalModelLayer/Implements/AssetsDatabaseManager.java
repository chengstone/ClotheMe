package com.logicalModelLayer.Implements;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.HashMap;  
import java.util.List;
import java.util.Map;  
  
import com.common.clothe.*;
import com.common.clothe.CommonDefine.CommonString;
import com.daogen.clotheme.Category;
import com.daogen.clotheme.CategoryArchive;
import com.daogen.clotheme.CategoryArchiveDao;
import com.daogen.clotheme.CategoryDao;
import com.daogen.clotheme.DaoMaster;
import com.daogen.clotheme.DaoMaster.OpenHelper;
import com.daogen.clotheme.DaoSession;
import com.daogen.clotheme.InitialFlag;
import com.daogen.clotheme.InitialFlagDao;
import com.daogen.clotheme.InitialFlagDao.Properties;
import com.daogen.clotheme.Meterial;
import com.daogen.clotheme.MeterialDao;
import com.daogen.clotheme.PersonInformation;

import com.daogen.clotheme.PersonInformationDao;
import com.daogen.clotheme.Season;
import com.daogen.clotheme.SeasonDao;
import com.daogen.clotheme.StorageLocation;
import com.daogen.clotheme.StorageLocationDao;
import com.daogen.clotheme.Style;
import com.daogen.clotheme.StyleDao;
import com.daogen.clotheme.Thickness;
import com.daogen.clotheme.ThicknessDao;
import com.daogen.clotheme.WearPlace;
import com.daogen.clotheme.WearPlaceDao;
import com.example.clotheme.R;

import de.greenrobot.dao.query.QueryBuilder;
//import android.R;
import android.content.Context;  
import android.content.SharedPreferences;  
import android.content.res.AssetManager;  
import android.database.sqlite.SQLiteDatabase;  
import android.util.Log;  
  
/** 
 * This is a Assets Database Manager 
 * Use it, you can use a assets database file in you application 
 * It will copy the database file to "/data/data/[your application package name]/database" when you first time you use it 
 * Then you can get a SQLiteDatabase object by the assets database file  
 * @author RobinTang 
 * @time 2012-09-20 
 *  
 *  
 * How to use: 
 * 1. Initialize AssetsDatabaseManager 
 * 2. Get AssetsDatabaseManager 
 * 3. Get a SQLiteDatabase object through database file 
 * 4. Use this database object 
 *  
 * Using example: 
 * AssetsDatabaseManager.initManager(getApplication()); // this method is only need call one time 
 * AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();   // get a AssetsDatabaseManager object 
 * SQLiteDatabase db1 = mg.getDatabase("db1.db");   // get SQLiteDatabase object, db1.db is a file in assets folder 
 * db1.???  // every operate by you want 
 * Of cause, you can use AssetsDatabaseManager.getManager().getDatabase("xx") to get a database when you need use a database 
 */  
public class AssetsDatabaseManager {  
    private static String tag = "AssetsDatabase"; // for LogCat  
    private static String databasepath = CommonDefine.CommonString.DATABASEPATH; // %s is packageName  
      
      
    // A mapping from assets database file to SQLiteDatabase object  
    private Map<String, SQLiteDatabase> databases = new HashMap<String, SQLiteDatabase>();  
      
    // Context of application  
    private Context context = null;  
      
    // Singleton Pattern  
    private static AssetsDatabaseManager m_instance = null;  
    
    private static DaoMaster daoMaster = null;  
    private static DaoSession daoSession = null;  
      
    /** 
     * Initialize AssetsDatabaseManager 
     * @param context, context of application 
     */  
    public static AssetsDatabaseManager getInstance(Context context){  
        if(m_instance == null){  
        	m_instance = new AssetsDatabaseManager(context);  
        }  
        return m_instance;
    }  
      
    /** 
     * Get a AssetsDatabaseManager object 
     * @return, if success return a AssetsDatabaseManager object, else return null 
     */  
    public static AssetsDatabaseManager getInstance(){  
        return m_instance;  
    }  
      
    private AssetsDatabaseManager(Context context){  
        this.context = context;  
    }  
      
    public SQLiteDatabase getDatabase(){
    	return getDatabase(CommonString.DATABASENAME);
    }
    
    /** 
     * @brief  取得DaoMaster 
     *  
     * @param  context 
     * @return daoMaster
     */  
    public DaoMaster getDaoMaster() {  
        if (daoMaster == null) {  
            OpenHelper helper = new DaoMaster.DevOpenHelper(context,CommonDefine.CommonString.DATABASENAME, null);  
            daoMaster = new DaoMaster(helper.getWritableDatabase());  
        }  
        return daoMaster;  
    }  
    
    /** 
     * @brief  取得DaoSession 
     *  
     * @param  context 
     * @return daoSession
     */  
    public DaoSession getDaoSession() {  
        if (daoSession == null) {  
            if (daoMaster == null) {  
                daoMaster = getDaoMaster();  
            }  
            daoSession = daoMaster.newSession();  
        }  
        verifyDataBaseFile();
        return daoSession;  
    }  
    
    /** 
     * @brief  对数据库进行数据初始化，将预先数据写入数据库 
     *  
     * @param   
     * @return 
     */  
    private void InitOriginalData(){
    	Log.d(tag, "开始数据初始化。");
    	//初始数据
    	//Season
    	Log.d(tag, "开始Season数据初始化。");
    	SeasonDao seasonDao = daoSession.getSeasonDao();
    	Season[] season = new Season[4];  
    	for(int i = 0;i < 4;i++){
    		season[i] = new Season();
    		season[i].setId(i);
    	}

    	season[0].setSeason("春");
    	season[0].setRecommendedThick(5);
    	season[0].setProbablyTemp("10");

    	season[1].setSeason("夏");
    	season[1].setRecommendedThick(1);
    	season[1].setProbablyTemp("30");

    	season[2].setSeason("秋");
    	season[2].setRecommendedThick(7);
    	season[2].setProbablyTemp("5");

    	season[3].setSeason("冬");
    	season[3].setRecommendedThick(10);
    	season[3].setProbablyTemp("-20");

    	for(int i = 0; i < 4; i++){
    		seasonDao.insert(season[i]); 
    	}

    	//Style
    	Log.d(tag, "开始Style数据初始化。");
    	StyleDao styleDao = daoSession.getStyleDao();
    	Style[] style = new Style[10];  
    	for(int i = 0;i < 10;i++){
    		style[i] = new Style();
    		style[i].setId(i);
    	}
    	
    	style[0].setStyle("性感");
    	style[1].setStyle("保守");
    	style[2].setStyle("流行");
    	style[3].setStyle("孤款");
    	style[4].setStyle("百搭");
    	style[5].setStyle("抢眼");
    	style[6].setStyle("昂贵");
    	style[7].setStyle("实惠");
    	style[8].setStyle("正式");
    	style[9].setStyle("休息");
    	
    	for(int i = 0; i < 10; i++){
    		styleDao.insert(style[i]);
    	}
    	 

    	//Category
    	Log.d(tag, "开始Category数据初始化。");
    	CategoryDao categoryDao = daoSession.getCategoryDao();
    	Category[] category = new Category[9];  
    	for(int i = 0;i < 9;i++){
    		category[i] = new Category();
    		category[i].setId(i);
    		category[i].setIsSubCategory(0);
        	category[i].setBelongCategoryID(0);
    	}

    	category[0].setName("袜子");
    	category[1].setName("包");
    	category[2].setName("鞋");
    	category[3].setName("外套");
    	category[4].setName("上衣");
    	category[5].setName("裤子");
    	category[6].setName("半身裙");
    	category[7].setName("帽子围巾手套");
    	category[8].setName("连身装");

    	for(int i = 0; i < 9; i++){
    		categoryDao.insert(category[i]); 
    	}
    	
    	//WearPlace
    	Log.d(tag, "开始WearPlace数据初始化。");
    	WearPlaceDao wearPlaceDao = daoSession.getWearPlaceDao();
    	WearPlace wearPlace = new WearPlace();  
    	wearPlace.setWearPlace("户外");
    	wearPlaceDao.insert(wearPlace); 
    	//Thickness
    	Log.d(tag, "开始Thickness数据初始化。");
    	ThicknessDao thicknessDao = daoSession.getThicknessDao();
    	Thickness thickness = new Thickness();  
    	thickness.setThickness("偏薄");
    	thickness.setTemperature(10);
    	thickness.setWhether("晴");
    	thicknessDao.insert(thickness); 
    	
    	//测试数据
    	long retDao = -1;
    	if(CommonDefine.isInTesting){
    		Log.d(tag, "开始测试数据初始化。");
    		//StorageLocation
    		StorageLocationDao storageLocationDao = daoSession.getStorageLocationDao();
    		StorageLocation storageLocation = new StorageLocation();  
    		storageLocation.setLocation("衣柜");
    		storageLocationDao.insert(storageLocation); 
    		//PersonInformation
    		PersonInformationDao personInformationDaoDao = daoSession.getPersonInformationDao();
    		PersonInformation personInformation = new PersonInformation();  
    		personInformation.setPersonName("chengstone");
    		personInformation.setStyleID(2);
    		personInformationDaoDao.insert(personInformation); 
    		//Meterial
    		MeterialDao meterialDao = daoSession.getMeterialDao();
    		Meterial meterial = new Meterial();
    		meterial.setDescription("裤子");
    		meterial.setBelongCategoryID(5);
    		meterial.setLocationID(1);
    		meterial.setPersonID(1);
    		meterial.setSeasonID(1);
    		meterial.setLastWashDate("2014/05/01");
    		meterial.setThicknessID(1);
    		meterial.setUseDate("2014/05/10");
    		meterial.setWearPlaceID("1");
    		meterial.setStyleID(2);
    		meterialDao.insert(meterial);
    		//CategoryArchive
    		CategoryArchiveDao categoryArchiveDao = daoSession.getCategoryArchiveDao();
    		CategoryArchive categoryArchive = new CategoryArchive();  
    		categoryArchive.setMeterialID(1);
    		categoryArchive.setIsWashRemind(0);
    		categoryArchive.setRemindTime("2014/05/15 20:00:00");
    		categoryArchive.setRemindFrequency("0");
    		retDao = categoryArchiveDao.insert(categoryArchive); 
    	}
    	Log.d(tag, "数据库初始化结束。");
    }
    /** 
     * @brief  判断数据库是否已经初始化，即程序是否第一次运行 
     *  
     * @param  初始化表DAO
     * @return 
     */ 
    private void verifyDataBaseFile(){
    	String dbfile = CommonString.DATABASENAME;
    	String spath = getGreenDAODatabaseFilepath();  
        String sfile = getGreenDAODatabaseFile(dbfile);  
        InitialFlagDao in_InitialFlagDao = daoSession.getInitialFlagDao();
          
        boolean initFlag = false;
        if(in_InitialFlagDao.count() == 0){
        	initFlag = true;
        }
//        else{
//        	QueryBuilder qb = in_InitialFlagDao.queryBuilder();
//            qb.where(Properties.InitialFlag.eq(false));
//
//            List youngJoes = qb.list();
//	
//        }
        if(initFlag == true){
        	InitOriginalData();
        	
        	InitialFlag initflag = new InitialFlag();  
        	initflag.setInitialFlag(true);
        	in_InitialFlagDao.insert(initflag);  
        }
//        File file = new File(sfile);  
//        SharedPreferences dbs = context.getSharedPreferences(AssetsDatabaseManager.class.toString(), 0);  
//        boolean flag = dbs.getBoolean(dbfile, false); // Get Database file flag, if true means this database file was copied and valid  
//        if(!flag || !file.exists()){  //判断数据库文件是否存在
//            file = new File(spath);  
//            if(!file.exists() && !file.mkdirs()){  
//                Log.i(tag, "Create \""+spath+"\" fail!");  
//            }  
//            if(!copyAssetsToFilesystem(dbfile, sfile)){  
//                Log.i(tag, String.format("Copy %s to %s fail!", dbfile, sfile));  
//            }  
//              
//            dbs.edit().putBoolean(dbfile, true).commit();  
//        }  
    }
    
    /** 
     * @brief  取得CategoryArchiveDao 
     *  
     * @param  
     * @return CategoryArchiveDao
     */ 
    public CategoryArchiveDao getCategoryArchiveDao(){
    	return getDaoSession().getCategoryArchiveDao();  
    }
    
    /** 
     * @brief  取得CategoryDao 
     *  
     * @param  
     * @return CategoryDao
     */ 
    public CategoryDao getCategoryDao(){
    	return getDaoSession().getCategoryDao();  
    }
    
    /** 
     * @brief  取得StyleDao 
     *  
     * @param  
     * @return StyleDao
     */ 
    public StyleDao getStyleDao(){
    	return getDaoSession().getStyleDao();  
    }
    
    /** 
     * @brief  取得ThicknessDao 
     *  
     * @param  
     * @return ThicknessDao
     */ 
    public ThicknessDao getThicknessDao(){
    	return getDaoSession().getThicknessDao();  
    }
    
    /** 
     * @brief  取得WearPlaceDao 
     *  
     * @param  
     * @return WearPlaceDao
     */ 
    public WearPlaceDao getWearPlaceDao(){
    	return getDaoSession().getWearPlaceDao();  
    }
    
    /** 
     * @brief  取得MeterialDao 
     *  
     * @param  
     * @return MeterialDao
     */ 
    public MeterialDao getMeterialDao(){
    	return getDaoSession().getMeterialDao();  
    }
    
    /** 
     * @brief  取得PersonInformationDao 
     *  
     * @param  
     * @return PersonInformationDao
     */ 
    public PersonInformationDao getPersonInformationDao(){
    	return getDaoSession().getPersonInformationDao();  
    }
    
    /** 
     * @brief  取得SeasonDao 
     *  
     * @param  
     * @return SeasonDao
     */ 
    public SeasonDao getSeasonDao(){
    	return getDaoSession().getSeasonDao();  
    }
    
    /** 
     * @brief  取得StorageLocationDao 
     *  
     * @param  
     * @return StorageLocationDao
     */ 
    public StorageLocationDao getStorageLocationDao(){
    	return getDaoSession().getStorageLocationDao();  
    }
    
    /** 
     * Get a assets database, if this database is opened this method is only return a copy of the opened database 
     * @param dbfile, the assets file which will be opened for a database 
     * @return, if success it return a SQLiteDatabase object else return null 
     */  
    public SQLiteDatabase getDatabase(String dbfile) {  
        if(databases.get(dbfile) != null){  
            Log.i(tag, String.format("Return a database copy of %s", dbfile));  
            return (SQLiteDatabase) databases.get(dbfile);  
        }  
        if(context==null)  
            return null;  
          
        Log.i(tag, String.format("Create database %s", dbfile));  
        String spath = getDatabaseFilepath();  
        String sfile = getDatabaseFile(dbfile);  
          
        File file = new File(sfile);  
        SharedPreferences dbs = context.getSharedPreferences(AssetsDatabaseManager.class.toString(), 0);  
        boolean flag = dbs.getBoolean(dbfile, false); // Get Database file flag, if true means this database file was copied and valid  
        if(!flag || !file.exists()){  
            file = new File(spath);  
            if(!file.exists() && !file.mkdirs()){  
                Log.i(tag, "Create \""+spath+"\" fail!");  
                return null;  
            }  
            if(!copyAssetsToFilesystem(dbfile, sfile)){  
                Log.i(tag, String.format("Copy %s to %s fail!", dbfile, sfile));  
                return null;  
            }  
              
            dbs.edit().putBoolean(dbfile, true).commit();  
        }  
          
        SQLiteDatabase db = SQLiteDatabase.openDatabase(sfile, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);  
        if(db != null){  
            databases.put(dbfile, db);  
        }  
        return db;  
    }  
      
    private String getDatabaseFilepath(){  
        return String.format(databasepath, context.getApplicationInfo().packageName);  
    }
    
    private String getGreenDAODatabaseFilepath(){  
        return String.format(CommonDefine.CommonString.GREENDAODATABASEPATH, context.getApplicationInfo().packageName);  
    }  
      
    private String getDatabaseFile(String dbfile){  
        return getDatabaseFilepath()+"/"+dbfile;  
    }  
    
    private String getGreenDAODatabaseFile(String dbfile){  
        return getGreenDAODatabaseFilepath()+"/"+dbfile;  
    }  
      
    private boolean copyAssetsToFilesystem(String assetsSrc, String des){  
        Log.i(tag, "Copy "+assetsSrc+" to "+des);  
        InputStream istream = null;  
        OutputStream ostream = null;  
        try{  
            AssetManager am = context.getAssets();  
            istream = am.open(assetsSrc);  
            ostream = new FileOutputStream(des);  
            byte[] buffer = new byte[1024];  
            int length;  
            while ((length = istream.read(buffer))>0){  
                ostream.write(buffer, 0, length);  
            }  
            istream.close();  
            ostream.close();  
        }  
        catch(Exception e){  
            e.printStackTrace();  
            try{  
                if(istream!=null)  
                    istream.close();  
                if(ostream!=null)  
                    ostream.close();  
            }  
            catch(Exception ee){  
                ee.printStackTrace();  
            }  
            return false;  
        }  
        return true;  
    }  
      
    /** 
     * Close assets database 
     * @param dbfile, the assets file which will be closed soon 
     * @return, the status of this operating 
     */  
    public boolean closeDatabase(String dbfile){  
        if(databases.get(dbfile) != null){  
            SQLiteDatabase db = (SQLiteDatabase) databases.get(dbfile);  
            db.close();  
            databases.remove(dbfile);  
            return true;  
        }  
        return false;  
    }  
      
    /** 
     * Close all assets database 
     */  
    static public void closeAllDatabase(){  
        Log.i(tag, "closeAllDatabase");  
        if(m_instance != null){  
            for(int i = 0; i < m_instance.databases.size(); ++i){  
                if(m_instance.databases.get(i)!=null){  
                	m_instance.databases.get(i).close();  
                }  
            }  
            m_instance.databases.clear();  
        }  
    }  
}  