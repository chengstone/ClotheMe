package com.common.clothe;

public class CommonDefine {

        public static final int NO_ERROR = 0;
        public static final int SYSTEM_ERROR = -1;
        public static final int WARNING_ERROR = -2;
        //是否使用logback作为log工具
        public static final boolean isUseLogback = true;
        //是否处于debug模式
        public static final boolean isDebug = true;
        //是否使用greenDAO
        public static final boolean isUseGreenDAO = false;
        //测试数据、代码
        public static final boolean isInTesting = true;

        public static final class CommonString {
        	//数据库文件路径
            public static final String DATABASEPATH = "/data/data/%s/database";
            //greenDAO数据库文件路径
            public static final String GREENDAODATABASEPATH = "/data/data/%s/databases";
            //数据库文件名
            public static final String DATABASENAME = "dat.db";
        } 
}

