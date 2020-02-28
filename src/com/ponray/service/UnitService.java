package com.ponray.service;

import com.ponray.entity.Unit;
import com.ponray.utils.AccessHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnitService {

    public int insert(Unit unit) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        //先将其它设置为未选中
        String sql = "insert into t_unit(unit_type,unit_code,param,base_unit_code) values (?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,unit.getUnitType());
        pstmt.setString(2,unit.getUnitCode());
        pstmt.setString(3,unit.getParam());
        pstmt.setString(4,unit.getBaseUnitCode());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入单位成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 单位更新
     * @param unit
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(Unit unit) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_unit set unit_type=?, unit_code=?, param=?,base_unit_code=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,unit.getUnitType());
        pstmt.setString(2,unit.getUnitCode());
        pstmt.setString(3,unit.getParam());
        pstmt.setString(4,unit.getBaseUnitCode());
        pstmt.setLong(5,unit.getID());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新标准成功");
        }
        pstmt.close();
        return res;
    }




    /**
     * 删除
     * @param id
     */
    public int del(long id) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_unit where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

    /**
     * 单位列表
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Unit> list() throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_unit order by unit_type";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet set = pstmt.executeQuery();
        return createList(set);
    }

    /**
     * 根据基本单位查询单位
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Unit> listByBaseCode(String baseCode) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_unit where base_unit_code = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,baseCode);
        ResultSet set = pstmt.executeQuery();
        return createList(set);
    }

    /**
     * 根据code获取unit
     * @param code
     * @param id
     * @return
     */
    public Unit findByCode(String code, Long id) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_unit where unit_code = ?";
        if(id!=null){
            sql = sql + " and ID <> ?";
        }
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,code);
        if(id!=null){
            pstmt.setLong(2,id);
        }
        ResultSet set = pstmt.executeQuery();
        while(set.next()){
            Unit unit = new Unit();
            unit.setID(set.getLong("ID"));
            unit.setUnitType(set.getString("unit_type"));
            unit.setUnitCode(set.getString("unit_code"));
            unit.setParam(set.getString("param"));
            unit.setBaseUnitCode(set.getString("base_unit_code"));
            return unit;
        }
        return null;
    }

    /**
     * 根据set返回实体list
     * @param set
     * @return
     * @throws SQLException
     */
    public List<Unit> createList(ResultSet set) throws SQLException {
        List<Unit> list = new ArrayList<>();
        while(set.next()){
            Unit unit = new Unit();
            unit.setID(set.getLong("ID"));
            unit.setUnitType(set.getString("unit_type"));
            unit.setUnitCode(set.getString("unit_code"));
            unit.setParam(set.getString("param"));
            unit.setBaseUnitCode(set.getString("base_unit_code"));
            list.add(unit);
        }
        return list;
    }
}
