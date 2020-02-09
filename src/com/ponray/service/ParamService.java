package com.ponray.service;

import com.ponray.entity.Param;
import com.ponray.utils.AccessHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParamService {
    public int insert(Param param) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = conn.createStatement();
        //先将其它设置为未选中
        String sql = "insert into t_param(standard_id,name,type,unit) values (?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,param.getStandard().getId());
        pstmt.setString(2,param.getName());
        pstmt.setString(3,param.getType());
        pstmt.setString(4,param.getUnit());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入参数成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 标准更新
     * @param param
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(Param param) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_param set standard_id=?, name=?, type=?, unit=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,param.getStandard().getId());
        pstmt.setString(2,param.getName());
        pstmt.setString(3,param.getType());
        pstmt.setString(4,param.getUnit());
        pstmt.setLong(5,param.getID());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新参数成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 传感器列表
     * @return
     */
    public List<Param> listByStandId(Long  standardId) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_param where standard_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,standardId);
        ResultSet set = pstmt.executeQuery();
        List<Param> list = new ArrayList<>();
        while(set.next()){
            Param param = new Param();
            param.setID(set.getLong("ID"));
            param.setName(set.getString("name"));
            param.setType(set.getString("type"));
            param.setUnit(set.getString("unit"));
            list.add(param);
        }
        return list;
    }


    /**
     * 删除
     * @param id
     */
    public int del(long id) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_param where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

}
