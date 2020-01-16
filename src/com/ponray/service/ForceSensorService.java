package com.ponray.service;

import com.ponray.utils.AccessHelper;
import com.ponray.utils.DataMap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ForceSensorService {

    /**
     * 拉力传感器插入
     * @param name
     * @param range
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insert(String name,float range) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = conn.createStatement();
        //先将其它设置为未选中
        String sql = "update t_force_sensor set selected = false";
        statement.executeUpdate(sql);

        sql = "insert into t_force_sensor(name,range,LN,YN,LP,YP,LH,YH,selected) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setFloat(2,range);
        pstmt.setInt(3,1);
        pstmt.setInt(4,1);
        pstmt.setInt(5,1);
        pstmt.setInt(6,1);
        pstmt.setInt(7,1);
        pstmt.setInt(8,1);
        pstmt.setBoolean(9,true);
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入力传感器成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 拉力传感器更新
     * @param ID
     * @param name
     * @param range
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(long ID,String name,float range) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_force_sensor set name=?, range=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setFloat(2,range);
        pstmt.setLong(3,ID);
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新力传感器成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 传感器列表
     * @return
     */
    public List<DataMap> list() throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = AccessHelper.getStatement(conn);
        String sql = "select * from t_force_sensor";
        ResultSet set = statement.executeQuery(sql);
        List<DataMap> list = new ArrayList<>();
        while(set.next()){
            DataMap map = new DataMap();
            map.put("ID",set.getLong("ID"));
            map.put("name",set.getString("name"));
            map.put("range",set.getFloat("range"));
            map.put("LN",set.getInt("LN"));
            map.put("YN",set.getInt("YN"));
            map.put("LP",set.getInt("LP"));
            map.put("YP",set.getInt("YP"));
            map.put("LH",set.getInt("LH"));
            map.put("YH",set.getInt("YH"));
            map.put("selected",set.getBoolean("selected"));
            list.add(map);
        }
        return list;
    }

    /**
     * 选中传感器
     * @param id
     */
    public int selected(long id) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = conn.createStatement();
        String sql = "update t_force_sensor set selected = false";
        statement.executeUpdate(sql);
        sql = "update t_force_sensor set selected = true where ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

    /**
     * 删除
     * @param id
     */
    public int del(long id) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_force_sensor where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }
}
