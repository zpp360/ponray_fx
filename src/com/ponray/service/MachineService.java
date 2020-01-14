package com.ponray.service;

import com.ponray.utils.AccessHelper;
import com.ponray.utils.DataMap;

import java.sql.*;


public class MachineService {


    /**
     * 更新
     * @param ID
     * @param name
     * @param modelNumber
     * @param specification
     * @param maxSpeed
     * @param precision
     * @param serialNum
     * @param createTime
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(long ID,String name,String modelNumber,String specification,String maxSpeed,String precision,String serialNum,String createTime) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "update t_machine set name = ?,model_number=?,specification=?,max_speed=?,precision=?,serial_number=?,create_time=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setString(2,modelNumber);
        pstmt.setString(3,specification);
        pstmt.setInt(4,Integer.parseInt(maxSpeed));
        pstmt.setDouble(5,Double.parseDouble(precision));
        pstmt.setString(6,serialNum);
        pstmt.setString(7,createTime);
        pstmt.setLong(8,ID);
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新主机参数成功");
        }
        pstmt.close();
        return res;
    }

    /**
     * 插入
     * @param name
     * @param modelNumber
     * @param specification
     * @param maxSpeed
     * @param precision
     * @param serialNum
     * @param createTime
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insert(String name,String modelNumber,String specification,String maxSpeed,String precision,String serialNum,String createTime) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        //插入
        String sql = "insert into t_machine(name,model_number,specification,max_speed,precision,serial_number,create_time) values (?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setString(2,modelNumber);
        pstmt.setString(3,specification);
        pstmt.setInt(4,Integer.parseInt(maxSpeed));
        pstmt.setDouble(5,Double.parseDouble(precision));
        pstmt.setString(6,serialNum);
        pstmt.setString(7,createTime);
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入主机参数成功");
        }
        pstmt.close();
        return res;
    }


    public DataMap findMachine() throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = AccessHelper.getStatement(conn);
        String sql = "select * from t_machine";
        ResultSet set = statement.executeQuery(sql);
        DataMap map = null;
        if(set.next()){
            map = new DataMap();
            map.put("ID",set.getLong("ID"));
            map.put("name",set.getString("name"));
            map.put("model_number",set.getString("model_number"));
            map.put("specification",set.getString("specification"));
            map.put("max_speed",set.getInt("max_speed"));
            map.put("precision",set.getDouble("precision"));
            map.put("serial_number",set.getString("serial_number"));
            map.put("create_time",set.getString("create_time"));
        }
        return map;
    }

}
