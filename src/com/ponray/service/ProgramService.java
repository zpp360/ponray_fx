package com.ponray.service;

import com.ponray.entity.Program;
import com.ponray.entity.ProgramControl;
import com.ponray.entity.ProgramResultParam;
import com.ponray.entity.ProgramUserParam;
import com.ponray.utils.AccessHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramService {
    public int insert(Program p) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        //先将其它设置为未选中
        String sql = "insert into t_program(name,standard_code,dirct,shape_name," +
                "transform_sensor,clear_dot,transform_change,abandon_extend," +
                "deform_extend,auto_breakage,gt_force,lt_rate,lt_mearure," +
                "is_time,time_value,is_load,load_value,is_transform,transform_value," +
                "is_pos,pos_value,is_preload,preload_value,preload_speed,is_return,return_speed," +
                "is_default,is_clear_disp,is_clear_transform,is_clear_n) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,p.getName());
        pstmt.setString(2,p.getStandard());
        pstmt.setInt(3,p.getDirect());
        pstmt.setString(4,p.getShapeName());
        pstmt.setString(5,p.getTransformSensor());
        pstmt.setInt(6,p.getClearDot());
        pstmt.setBoolean(7,p.isTransformChange());
        pstmt.setBoolean(8,p.isAbandonExtend());
        pstmt.setFloat(9,p.getDeformExtend());
        pstmt.setBoolean(10,p.isAutoBreakage());
        pstmt.setFloat(11,p.getGtForce());
        pstmt.setFloat(12,p.getLtRate());
        pstmt.setFloat(13,p.getLtMearure());
        pstmt.setBoolean(14,p.isTime());
        pstmt.setFloat(15,p.getTimeValue());
        pstmt.setBoolean(16,p.isLoad());
        pstmt.setFloat(17,p.getLoadValue());
        pstmt.setBoolean(18,p.isTransform());
        pstmt.setFloat(19,p.getTransformValue());
        pstmt.setBoolean(20,p.isPos());
        pstmt.setFloat(21,p.getPosValue());
        pstmt.setBoolean(22,p.isPreload());
        pstmt.setFloat(23,p.getPreloadValue());
        pstmt.setFloat(24,p.getPreloadSpeed());
        pstmt.setBoolean(25,p.isReturn());
        pstmt.setFloat(26,p.getReturnSpeed());
        pstmt.setBoolean(27,p.isDefault());
        pstmt.setBoolean(28,p.isClearDisp());
        pstmt.setBoolean(29,p.isClearTransform());
        pstmt.setBoolean(30,p.isClearN());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("插入实验方案成功");
        }
        pstmt.close();
        return res;
    }


    /**
     * 单位更新
     * @param p
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int updateBase(Program p) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql ="update t_program set name=?,standard_code=?,dirct=?,shape_name=?," +
                "transform_sensor=?,clear_dot=?,transform_change=?,abandon_extend=?," +
                "deform_extend=?,auto_breakage=?,gt_force=?,lt_rate=?,lt_mearure=?," +
                "is_time=?,time_value=?,is_load=?,load_value=?,is_transform=?,transform_value=?," +
                "is_pos=?,pos_value=?,is_preload=?,preload_value=?,preload_speed=?,is_return=?,return_speed=?," +
                "is_default=?,is_clear_disp=?,is_clear_transform=?,is_clear_n=?,num=? where ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,p.getName());
        pstmt.setString(2,p.getStandard());
        pstmt.setInt(3,p.getDirect());
        pstmt.setString(4,p.getShapeName());
        pstmt.setString(5,p.getTransformSensor());
        pstmt.setInt(6,p.getClearDot());
        pstmt.setBoolean(7,p.isTransformChange());
        pstmt.setBoolean(8,p.isAbandonExtend());
        pstmt.setFloat(9,p.getDeformExtend());
        pstmt.setBoolean(10,p.isAutoBreakage());
        pstmt.setFloat(11,p.getGtForce());
        pstmt.setFloat(12,p.getLtRate());
        pstmt.setFloat(13,p.getLtMearure());
        pstmt.setBoolean(14,p.isTime());
        pstmt.setFloat(15,p.getTimeValue());
        pstmt.setBoolean(16,p.isLoad());
        pstmt.setFloat(17,p.getLoadValue());
        pstmt.setBoolean(18,p.isTransform());
        pstmt.setFloat(19,p.getTransformValue());
        pstmt.setBoolean(20,p.isPos());
        pstmt.setFloat(21,p.getPosValue());
        pstmt.setBoolean(22,p.isPreload());
        pstmt.setFloat(23,p.getPreloadValue());
        pstmt.setFloat(24,p.getPreloadSpeed());
        pstmt.setBoolean(25,p.isReturn());
        pstmt.setFloat(26,p.getReturnSpeed());
        pstmt.setBoolean(27,p.isDefault());
        pstmt.setBoolean(28,p.isClearDisp());
        pstmt.setBoolean(29,p.isClearTransform());
        pstmt.setBoolean(30,p.isClearN());
        pstmt.setString(31,"00");
        pstmt.setLong(32,p.getID());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新实验方案基本信息成功");
        }
        pstmt.close();
        return res;
    }

    /**
     * 更新程控方式
     * @param p
     * @return
     */
    public int updateControl(Program p) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "update t_program set is_pro_control = ?,general_speed=? where ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setBoolean(1,p.isControl());
        pstmt.setFloat(2,p.getGeneralSpeed());
        pstmt.setLong(3,p.getID());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新实验方案位移方式成功");
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
        String sql = "delete from t_program where ID = ?";
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
    public List<Program> list() throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_program";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet set = pstmt.executeQuery();
        return createList(set);
    }

    /**
     * 更新坐标轴 tab3
     * @param p
     * @return
     */
    public int updateAxis(Program p) throws Exception{
        Connection conn = AccessHelper.getConnection();
        String sql = "update t_program set screen_one_x = ?,screen_one_y=?,screen_two_x=?,screen_two_y=?,screen_three_x=?,screen_three_y=?,screen_four_x=?,screen_four_y=?" +
                ",unit_n=?,unit_transform=?,unit_load=? where ID=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,p.getOneX());
        pstmt.setString(2,p.getOneY());
        pstmt.setString(3,p.getTwoX());
        pstmt.setString(4,p.getTwoY());
        pstmt.setString(5,p.getThreeX());
        pstmt.setString(6,p.getThreeY());
        pstmt.setString(7,p.getFourX());
        pstmt.setString(8,p.getFourY());
        pstmt.setString(9,p.getUnitN());
        pstmt.setString(10,p.getUnitTransform());
        pstmt.setString(11,p.getUnitLoad());
        pstmt.setLong(12,p.getID());
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("更新实验方案区县坐标成功");
        }
        pstmt.close();
        return res;
    }

    /**
     * 根据set返回实体list
     * @param set
     * @return
     * @throws SQLException
     */
    public List<Program> createList(ResultSet set) throws SQLException {
        List<Program> list = new ArrayList<>();
        while (set.next()) {
            Program p = new Program();
            p.setID(set.getLong("ID"));
            p.setNum(set.getString("num"));
            p.setName(set.getString("name"));
            p.setStandard(set.getString("standard_code"));
            p.setDirect(set.getInt("dirct"));
            p.setShapeName(set.getString("shape_name"));
            p.setTransformSensor(set.getString("transform_sensor"));
            p.setClearDot(set.getInt("clear_dot"));
            p.setTransformChange(set.getBoolean("transform_change"));
            p.setAbandonExtend(set.getBoolean("abandon_extend"));
            p.setDeformExtend(set.getFloat("deform_extend"));
            p.setAutoBreakage(set.getBoolean("auto_breakage"));
            p.setGtForce(set.getFloat("gt_force"));
            p.setLtRate(set.getFloat("lt_rate"));
            p.setLtMearure(set.getFloat("lt_mearure"));
            p.setTime(set.getBoolean("is_time"));
            p.setTimeValue(set.getFloat("time_value"));
            p.setLoad(set.getBoolean("is_load"));
            p.setLoadValue(set.getFloat("load_value"));
            p.setTransform(set.getBoolean("is_transform"));
            p.setTransformValue(set.getFloat("transform_value"));
            p.setPos(set.getBoolean("is_pos"));
            p.setPosValue(set.getFloat("pos_value"));
            p.setPreload(set.getBoolean("is_preload"));
            p.setPreloadValue(set.getFloat("preload_value"));
            p.setPreloadSpeed(set.getFloat("preload_speed"));
            p.setReturn(set.getBoolean("is_return"));
            p.setReturnSpeed(set.getFloat("return_speed"));
            p.setDefault(set.getBoolean("is_default"));
            p.setClearDisp(set.getBoolean("is_clear_disp"));
            p.setClearTransform(set.getBoolean("is_clear_transform"));
            p.setClearN(set.getBoolean("is_clear_n"));
            p.setControl(set.getBoolean("is_pro_control"));
            p.setGeneralSpeed(set.getFloat("general_speed"));
            p.setOneX(set.getString("screen_one_x"));
            p.setOneY(set.getString("screen_one_y"));
            p.setTwoX(set.getString("screen_two_x"));
            p.setTwoY(set.getString("screen_two_y"));
            p.setThreeX(set.getString("screen_three_x"));
            p.setThreeY(set.getString("screen_three_y"));
            p.setFourX(set.getString("screen_four_x"));
            p.setFourY(set.getString("screen_four_y"));
            p.setUnitN(set.getString("unit_n"));
            p.setUnitTransform(set.getString("unit_transform"));
            p.setUnitLoad(set.getString("unit_load"));
            list.add(p);
        }
        return list;
    }

    /**
     * 删除用户参数
     * @param id
     * @return
     * @throws Exception
     */
    public int delUserParamByProgramId(Long id) throws Exception{
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_program_user_param where program_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

    /**
     * 批量保存用户参数
     * @param list
     * @param programId
     * @throws Exception
     */
    public void batchSaveUserParam(List<ProgramUserParam> list,Long programId) throws Exception{
        Connection conn = AccessHelper.getConnection();
        String sql = "insert into t_program_user_param(program_id,num,name,unit,default_val) values (?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                ProgramUserParam p = list.get(i);
                pstmt.setLong(1,programId);
                pstmt.setInt(2,p.getNum());
                pstmt.setString(3,p.getName());
                pstmt.setString(4,p.getUnit());
                pstmt.setString(5,p.getDefaultVal());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.clearBatch();
        }
        pstmt.close();
    }

    /**
     * 用户参数列表
     * @return
     * @throws Exception
     */
    public List<ProgramUserParam> listUserParam(Long programId) throws Exception{
            Connection conn = AccessHelper.getConnection();
            String sql = "select * from t_program_user_param where program_id = ? order by num asc";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,programId);
            ResultSet set = pstmt.executeQuery();
            List<ProgramUserParam> list = new ArrayList<>();
            while(set.next()){
                ProgramUserParam p = new ProgramUserParam();
                p.setID(set.getLong("ID"));
                p.setNum(set.getInt("num"));
                p.setName(set.getString("name"));
                p.setUnit(set.getString("unit"));
                p.setDefaultVal(set.getString("default_val"));
                list.add(p);
            }
            pstmt.close();
            return list;
    }


    /**
     * 删除结果参数
     * @param id
     * @return
     * @throws Exception
     */
    public int delResultParamByProgramId(Long id) throws Exception{
        Connection conn = AccessHelper.getConnection();
        String sql = "delete from t_program_result_param where program_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,id);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

    /**
     * 批量保存用户参数
     * @param list
     * @param programId
     * @throws Exception
     */
    public void batchSaveResultParam(List<ProgramResultParam> list, Long programId) throws Exception{
        Connection conn = AccessHelper.getConnection();
        String sql = "insert into t_program_result_param(program_id,num,name,unit,result_flag,up,low) values (?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                ProgramResultParam p = list.get(i);
                pstmt.setLong(1,programId);
                pstmt.setInt(2,p.getNum());
                pstmt.setString(3,p.getName());
                pstmt.setString(4,p.getUnit());
                pstmt.setBoolean(5,p.getResultFlag());
                pstmt.setString(6,p.getUp());
                pstmt.setString(7,p.getLow());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.clearBatch();
        }
        pstmt.close();
    }

    /**
     * 用户参数列表
     * @return
     * @throws Exception
     */
    public List<ProgramResultParam> listResultParam(Long programId) throws Exception{
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_program_result_param where program_id = ? order by num asc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,programId);
        ResultSet set = pstmt.executeQuery();
        List<ProgramResultParam> list = new ArrayList<>();
        while(set.next()){
            ProgramResultParam p = new ProgramResultParam();
            p.setID(set.getLong("ID"));
            p.setNum(set.getInt("num"));
            p.setName(set.getString("name"));
            p.setUnit(set.getString("unit"));
            p.setResultFlag(set.getBoolean("result_flag"));
            p.setUp(set.getString("up"));
            p.setLow(set.getString("low"));
            list.add(p);
        }
        pstmt.close();
        return list;
    }


}

