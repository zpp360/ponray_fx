package com.ponray.utils;

import java.sql.*;

public class AccessHelper {

    private volatile  static AccessHelper instance = null;

    private final static String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    private final static String DBPATH = System.getProperties().getProperty("user.dir") + "\\db\\db.mdb";

    private final static String USERNAME = null;

    private final static String PASSWORD = null;

    private static Connection conn = null;

    private static Statement statement = null;

    private AccessHelper(){}

    public static AccessHelper getInstance() throws ClassNotFoundException, SQLException {
        //先检查实例是否存在，如果不存在才进入下面的同步模块
        if(instance == null){
            //同步块，线程安全的创建实例
            synchronized (AccessHelper.class){
                //再次检查实例是否存在，不存在才真的创建实例
                if(instance == null){
                    instance = new AccessHelper();

                    Class.forName(DRIVER);
                    conn = DriverManager.getConnection("jdbc:ucanaccess://" + DBPATH,USERNAME,PASSWORD);
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


    public static ResultSet getResultSet(Statement statement,String sql) throws SQLException{
        return statement.executeQuery(sql);
    }



    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName(DRIVER);

        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + DBPATH,USERNAME,PASSWORD);

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT id, name, selected FROM t_language");

        while (rs.next()) {

            long id = rs.getLong("id");
            String name = rs.getString("name");
            Boolean selected = rs.getBoolean("selected");

            System.out.println(id + "\t" + name + "\t" + selected);
        }

        rs.close();
        stmt.close();
        conn.close();
    }
}
