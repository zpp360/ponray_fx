package com.ponray.service;

import com.ponray.entity.Test;
import com.ponray.utils.AccessHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestService {

    public int insert(Test test) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        //先将其它设置为未选中
        String sql = "insert into t_test(test_num,test_time,program_name,standard_name,transform_sensor,load_unit,transform_unit,press_unit,save_file,speed,run_time,shape) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,test.getTestNum());
        pstmt.setDouble(2,test.getTestTime());
        pstmt.setString(3,test.getProgramName());
        pstmt.setString(4,test.getStandardName());
        pstmt.setString(5,test.getTransformSensor());
        pstmt.setString(6,test.getLoadUnit());
        pstmt.setString(7,test.getTransformUnit());
        pstmt.setString(8,test.getPressUnit());
        pstmt.setString(9,test.getSaveFile());
        pstmt.setFloat(10,test.getSpeed());
        pstmt.setFloat(11,test.getRunTime());
        pstmt.setString(12,test.getShape());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入实验成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 单位更新
     * @param test
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(Test test) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_test set test_time=?,program_name=?,standard_name=?,transform_sensor=?,load_unit=?,transform_unit=?,press_unit=?,save_file=?,speed=?,run_time=?,shape=? where test_num = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDouble(1,test.getTestTime());
        pstmt.setString(2,test.getProgramName());
        pstmt.setString(3,test.getStandardName());
        pstmt.setString(4,test.getTransformSensor());
        pstmt.setString(5,test.getLoadUnit());
        pstmt.setString(6,test.getTransformUnit());
        pstmt.setString(7,test.getPressUnit());
        pstmt.setString(8,test.getSaveFile());
        pstmt.setFloat(9,test.getSpeed());
        pstmt.setFloat(10,test.getRunTime());
        pstmt.setString(11,test.getShape());
        pstmt.setLong(12,test.getTestNum());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新实验成功");
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
        String sql = "delete from t_test where ID = ?";
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
    public List<Test> list() throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_test";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet set = pstmt.executeQuery();
        return createList(set);
    }

    /**
     * 获取test_num最大值
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Long maxNum() throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select max(test_num) as maxNum from t_test";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet set = pstmt.executeQuery();
        Long maxNum = 1L;
        while(set.next()){
            maxNum = set.getLong("maxNum");
        }
        return maxNum;
    }

    /**
     * 根据set返回实体list
     * @param set
     * @return
     * @throws SQLException
     */
    public List<Test> createList(ResultSet set) throws SQLException {
        List<Test> list = new ArrayList<>();
        while(set.next()){
            Test test = new Test();
            test.setTestNum(set.getLong("test_num"));
            test.setTestTime(set.getDouble("test_time"));
            test.setProgramName(set.getString("program_name"));
            test.setStandardName(set.getString("standard_name"));
            test.setTransformSensor(set.getString("transform_sensor"));
            test.setLoadUnit(set.getString("load_unit"));
            test.setTransformUnit(set.getString("transform_unit"));
            test.setPressUnit(set.getString("press_unit"));
            test.setSaveFile(set.getString("save_file"));
            test.setSpeed(set.getFloat("speed"));
            test.setRunTime(set.getFloat("run_time"));
            test.setShape(set.getString("shape"));
            list.add(test);
        }
        return list;
    }
}
