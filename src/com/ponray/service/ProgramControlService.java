package com.ponray.service;

import com.ponray.entity.ProgramControl;
import com.ponray.utils.AccessHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramControlService {
    public int insert(ProgramControl p) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        Statement statement = conn.createStatement();
        //先将其它设置为未选中
        String sql = "insert into t_program_control(program_id,num,start,start_value,start_unit,end,end_value,end_unit) values (?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,p.getProgram().getID());
        pstmt.setInt(2,p.getNum());
        pstmt.setString(3,p.getStart());
        pstmt.setString(4,p.getStartValue());
        pstmt.setString(5,p.getStartUnit());
        pstmt.setString(6,p.getEnd());
        pstmt.setString(7,p.getEndValue());
        pstmt.setString(8,p.getEndUnit());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入程控成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 标准更新
     * @param p
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int update(ProgramControl p) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_program_control set program_id=?, num=?, start=?, start_value=?, start_unit=?, end=?, end_value=?, end_unit=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,p.getProgram().getID());
        pstmt.setInt(2,p.getNum());
        pstmt.setString(3,p.getStart());
        pstmt.setString(4,p.getStartValue());
        pstmt.setString(5,p.getStartUnit());
        pstmt.setString(6,p.getEnd());
        pstmt.setString(7,p.getEndValue());
        pstmt.setString(8,p.getEndUnit());
        pstmt.setLong(9,p.getID());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新程控成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 批量插入程控方式
     * @param list
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void insertControl(List<ProgramControl> list,Long programId) throws SQLException,ClassNotFoundException{
        Connection conn = AccessHelper.getConnection();
        String sql = "insert into t_program_control(program_id,num,start,start_value,start_unit,end,end_value,end_unit) values (?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                ProgramControl p = list.get(i);
                pstmt.setLong(1,programId);
                pstmt.setInt(2,i+1);
                pstmt.setString(3,p.getStart());
                pstmt.setString(4,p.getStartValue());
                pstmt.setString(5,p.getStartUnit());
                pstmt.setString(6,p.getEnd());
                pstmt.setString(7,p.getEndValue());
                pstmt.setString(8,p.getEndUnit());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.clearBatch();
        }
        pstmt.close();
    }


    /**
     * 传感器列表
     * @return
     */
    public List<ProgramControl> listByProgramId(Long  programId) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_program_control where program_id = ? order by num asc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,programId);
        ResultSet set = pstmt.executeQuery();
        List<ProgramControl> list = new ArrayList<>();
        while(set.next()){
            ProgramControl p = new ProgramControl();
            p.setID(set.getLong("ID"));
            p.setNum(set.getInt("num"));
            p.setStart(set.getString("start"));
            p.setStartValue(set.getString("start_value"));
            p.setStartUnit(set.getString("start_unit"));
            p.setEnd(set.getString("end"));
            p.setEndValue(set.getString("end_value"));
            p.setEndUnit(set.getString("end_unit"));
            list.add(p);
        }
        pstmt.close();
        return list;
    }

    /**
     * 删除程控步骤
     * @param pID
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int delByProgramId(Long pID) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_program_control where program_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,pID);
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
        String sql = "delete from t_program_control where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }


}
