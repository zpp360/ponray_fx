package com.ponray.service;

import com.ponray.entity.Standard;
import com.ponray.utils.AccessHelper;
import com.ponray.utils.DataMap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StandardService {
    public int insert(String code,String name,String remark) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = conn.createStatement();
        //先将其它设置为未选中
        String sql = "insert into t_standard(code,name,remark) values (?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,code);
        pstmt.setString(2,name);
        pstmt.setString(3,remark);
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入标准成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 标准更新
     * @param ID
     * @param name
     * @param remark
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(long ID,String code,String name,String remark) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_standard set code=?, name=?, remark=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,code);
        pstmt.setString(2,name);
        pstmt.setString(3,remark);
        pstmt.setLong(4,ID);
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新标准成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 传感器列表
     * @return
     */
    public List<Standard> list() throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = AccessHelper.getStatement(conn);
        String sql = "select * from t_standard";
        ResultSet set = statement.executeQuery(sql);
        List<Standard> list = new ArrayList<>();
        int i = 1;
        while(set.next()){
            Standard standard = new Standard();
            standard.setId(set.getLong("ID"));
            standard.setNum(i++);
            standard.setCode(set.getString("code"));
            standard.setName(set.getString("name"));
            standard.setRemark(set.getString("remark"));
            list.add(standard);
        }
        return list;
    }


    /**
     * 删除
     * @param id
     */
    public int del(long id) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_standard where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

    public List<Standard> select(String code, String name) throws SQLException, ClassNotFoundException{
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_standard where code like ? and name like ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,"%"+code+"%");
        pstmt.setString(2,"%"+name+"%");
        ResultSet set = pstmt.executeQuery();
        List<Standard> list = new ArrayList<>();
        int i = 1;
        while(set.next()){
            Standard standard = new Standard();
            standard.setId(set.getLong("ID"));
            standard.setNum(i++);
            standard.setCode(set.getString("code"));
            standard.setName(set.getString("name"));
            standard.setRemark(set.getString("remark"));
            list.add(standard);
        }
        return list;
    }

    /**
     * 根据code查询
     * @param code
     * @return
     */
    public Standard findByCode(String code) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_standard where code = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,code);
        ResultSet set = pstmt.executeQuery();
        Standard standard = null;
        while(set.next()){
            standard = new Standard();
            standard.setId(set.getLong("ID"));
            standard.setCode(set.getString("code"));
            standard.setName(set.getString("name"));
            standard.setRemark(set.getString("remark"));
        }
        return standard;
    }
}
