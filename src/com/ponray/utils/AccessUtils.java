package com.ponray.utils;

import javax.xml.transform.Result;
import java.sql.*;

public class AccessUtils {

    private final static String DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";

    private final static String DBPATH = System.getProperties().getProperty("user.dir") + "\\db\\db.mdb";

    private final static String USERNAME = null;

    private final static String PASSWORD = null;


    /**
     * 获取connection
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection("jdbc:ucanaccess://" + DBPATH,USERNAME,PASSWORD);
        return conn;
    }

    /**
     * 获取statement
     * @param connection
     * @return
     * @throws SQLException
     */
    public static  Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
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
