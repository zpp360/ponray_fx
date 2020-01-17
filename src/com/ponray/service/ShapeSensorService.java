package com.ponray.service;

import com.ponray.utils.AccessHelper;
import com.ponray.utils.DataMap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeSensorService {

    /**
     * 拉力传感器插入
     * @param name
     * @param shape
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insert(String name,float shape) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = conn.createStatement();
        //先将其它设置为未选中
        String sql = "update t_force_sensor set selected = false";
        statement.executeUpdate(sql);

        sql = "insert into t_shape_sensor(name,shape,selected) values (?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setFloat(2,shape);
        pstmt.setBoolean(3,true);
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入变形传感器成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 拉力传感器更新
     * @param ID
     * @param name
     * @param shape
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(long ID,String name,float shape) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_shape_sensor set name=?, shape=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setFloat(2,shape);
        pstmt.setLong(3,ID);
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新变形传感器成功");
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
        String sql = "select * from t_shape_sensor";
        ResultSet set = statement.executeQuery(sql);
        List<DataMap> list = new ArrayList<>();
        while(set.next()){
            DataMap map = new DataMap();
            map.put("ID",set.getLong("ID"));
            map.put("name",set.getString("name"));
            map.put("shape",set.getFloat("shape"));
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
        String sql = "update t_shape_sensor set selected = false";
        statement.executeUpdate(sql);
        sql = "update t_shape_sensor set selected = true where ID=?";
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
        String sql = "delete from t_shape_sensor where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }
}
