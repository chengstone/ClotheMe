package com.logicalModelLayer.clotheme;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.util.HashMap;  
import java.util.List;
import java.util.Map;  
  
import com.common.clothe.*;
import com.common.clothe.CommonDefine.CommonString;
import com.daogen.clotheme.CategoryArchiveDao;
import com.daogen.clotheme.DaoMaster;
import com.daogen.clotheme.DaoMaster.OpenHelper;
import com.daogen.clotheme.DaoSession;
import com.daogen.clotheme.InitialFlag;
import com.daogen.clotheme.InitialFlagDao;
import com.daogen.clotheme.InitialFlagDao.Properties;
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
        verifyDataBaseFile(daoSession.getInitialFlagDao());
        return daoSession;  
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
     * @brief  判断数据库是否已经初始化，即程序是否第一次运行 
     *  
     * @param  初始化表DAO
     * @return 
     */ 
    private void verifyDataBaseFile(InitialFlagDao in_InitialFlagDao){
    	String dbfile = CommonString.DATABASENAME;
    	String spath = getGreenDAODatabaseFilepath();  
        String sfile = getGreenDAODatabaseFile(dbfile);  
          
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