package com.ponray.service;

import com.ponray.entity.Formula;
import com.ponray.utils.AccessHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormulaService {
    public int insert(Formula formula) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = conn.createStatement();
        //先将其它设置为未选中
        String sql = "insert into t_formula(param_id,temp_vari,symbol,param_type1,param_name1,op_char,param_type2,param_name2) values (?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,formula.getParam().getID());
        pstmt.setString(2,formula.getTempVari());
        pstmt.setString(3,formula.getSymbol());
        pstmt.setString(4,formula.getParamTypeOne());
        pstmt.setString(5,formula.getParamNameOne());
        pstmt.setString(6,formula.getOpChar());
        pstmt.setString(7,formula.getParamTypeTwo());
        pstmt.setString(8,formula.getParamNameTwo());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入公式成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 公式更新
     * @param formula
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(Formula formula) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_formula set param_id=?,temp_vari=?,symbol=?,param_type1=?, param_name1=?,op_char=?,param_type2=?,param_name2=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,formula.getParam().getID());
        pstmt.setString(2,formula.getTempVari());
        pstmt.setString(3,formula.getSymbol());
        pstmt.setString(4,formula.getParamTypeOne());
        pstmt.setString(5,formula.getParamNameOne());
        pstmt.setString(6,formula.getOpChar());
        pstmt.setString(7,formula.getParamTypeTwo());
        pstmt.setString(8,formula.getParamNameTwo());
        pstmt.setLong(9,formula.getID());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新公式成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 公式列表
     * @return
     */
    public List<Formula> listByParamId(Long paramId) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_formula where param_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,paramId);
        ResultSet set = pstmt.executeQuery();
        List<Formula> list = new ArrayList<>();
        while(set.next()){
            Formula formula = new Formula();
            formula.setID(set.getLong("ID"));
            formula.setTempVari(set.getString("temp_vari"));
            formula.setSymbol(set.getString("symbol"));
            formula.setParamTypeOne(set.getString("param_type1"));
            formula.setParamNameOne(set.getString("param_name1"));
            formula.setOpChar(set.getString("op_char"));
            formula.setParamTypeTwo(set.getString("param_type2"));
            formula.setParamNameTwo(set.getString("param_name2"));
            list.add(formula);
        }
        return list;
    }


    /**
     * 删除
     * @param id
     */
    public int del(long id) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_formula where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

    public int deleteByParamId(Long paramId) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_formula where param_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,paramId);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }
}
