package com.ponray.utils;

import java.io.File;
import java.sql.*;


public class DBFileHelper {

    private volatile  static DBFileHelper instance = null;

    private final static String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    //    private final static String DBPATH = AccessHelper.class.getResource("/db/db.mdb").getPath().replace("%20"," ");
    private final static String DBPATH = System.getProperty("user.dir") + File.separator + "db" + File.separator;

    private final static String USERNAME = null;

    private final static String PASSWORD = null;

    private static Connection conn = null;

    private static Statement statement = null;

    private DBFileHelper(){}

    public static DBFileHelper getInstance(String dbName) throws ClassNotFoundException, SQLException {
        //先检查实例是否存在，如果不存在才进入下面的同步模块
        if(instance == null){
            //同步块，线程安全的创建实例
            synchronized (AccessHelper.class){
                //再次检查实例是否存在，不存在才真的创建实例
                if(instance == null){
                    instance = new DBFileHelper();

                    Class.forName(DRIVER);
                    conn = DriverManager.getConnection("jdbc:ucanaccess://" + DBPATH + dbName,USERNAME,PASSWORD);
                    statement = conn.createStatement();
                }
            }
        }
        return instance;
    }


    /**
     * 获取connection
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public final static Connection getConnection() throws ClassNotFoundException, SQLException {
        return conn;
    }

    /**
     * 获取statement
     * @param connection
     * @return
     * @throws SQLException
     */
    public final static  Statement getStatement(Connection connection) throws SQLException {
        return statement;
    }


    public static ResultSet getResultSet(Statement statement, String sql) throws SQLException{
        return statement.executeQuery(sql);
    }


}

