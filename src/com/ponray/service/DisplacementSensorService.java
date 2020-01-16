package com.ponray.service;

import com.ponray.utils.AccessHelper;
import com.ponray.utils.DataMap;

import java.sql.*;

public class DisplacementSensorService {

    /**
     * 插入或更新
     * @param ID
     * @param name
     * @param range
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertOrUpdate(long ID, String name, int range) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = AccessHelper.getStatement(conn);
        String sql = "select * from t_displacement_sensor";
        ResultSet set = statement.executeQuery(sql);
        int result = 0;
        if(set.next()){
            //存在更新
            sql = "update t_displacement_sensor set name = ?,range = ? where ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setInt(2,range);
            pstmt.setLong(3,ID);
            result = pstmt.executeUpdate();
        }else{
            sql = "insert into t_displacement_sensor(name,range) values (?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setInt(2,range);
            result = pstmt.executeUpdate();
        }
        return result;
    }

    /**
     * 获取数据
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public DataMap findOne() throws SQLException, ClassNotFoundException {
        DataMap data = null;
        Connection conn = AccessHelper.getConnection();
        Statement statement = AccessHelper.getStatement(conn);
        String sql = "select * from t_displacement_sensor";
        ResultSet set = statement.executeQuery(sql);
        if(set.next()){
            data = new DataMap();
            data.put("ID",set.getLong("ID"));
            data.put("name",set.getString("name"));
            data.put("range",set.getInt("range"));
        }
        return data;
    }

}
