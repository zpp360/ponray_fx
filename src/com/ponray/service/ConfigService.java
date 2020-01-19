package com.ponray.service;

import com.ponray.utils.AccessHelper;
import com.ponray.utils.DataMap;

import java.sql.*;

public class ConfigService {
    /**
     * 插入或更新
     * @param ID
     * @param num
     * @param checked
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertOrUpdate(long ID, float num, boolean checked) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = AccessHelper.getStatement(conn);
        String sql = "select * from t_config";
        ResultSet set = statement.executeQuery(sql);
        int result = 0;
        if(set.next()){
            //存在更新
            sql = "update t_config set displacement_speed = ?,displacement_virtual = ? where ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1,num);
            pstmt.setBoolean(2,checked);
            pstmt.setLong(3,ID);
            result = pstmt.executeUpdate();
        }else{
            sql = "insert into t_config(displacement_speed,displacement_virtual) values (?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1,num);
            pstmt.setBoolean(2,checked);
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
        String sql = "select * from t_config";
        ResultSet set = statement.executeQuery(sql);
        if(set.next()){
            data = new DataMap();
            data.put("ID",set.getLong("ID"));
            data.put("displacement_speed",set.getFloat("displacement_speed"));
            data.put("displacement_virtual",set.getBoolean("displacement_virtual"));
        }
        return data;
    }
}
