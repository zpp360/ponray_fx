package com.ponray.service;

import com.ponray.entity.Test;
import com.ponray.entity.TestData;
import com.ponray.utils.AccessHelper;
import com.ponray.utils.DBFileHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TestService {

    public int insert(Test test) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        //先将其它设置为未选中
        String sql = "insert into t_test(test_num,test_time,program_name,standard_name,transform_sensor,load_unit,transform_unit,press_unit,save_file,img_file,speed,run_time,shape,max_load,deep,area,mpa,simple_name,lo,extension,min_load,avg_load,width,hou,dia,blqd,dlz_load1,dlz_load2,dwy_pos1,dwy_pos2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,test.getTestNum());
        pstmt.setDate(2,test.getTestTime());
        pstmt.setString(3,test.getProgramName());
        pstmt.setString(4,test.getStandardName());
        pstmt.setString(5,test.getTransformSensor());
        pstmt.setString(6,test.getLoadUnit());
        pstmt.setString(7,test.getTransformUnit());
        pstmt.setString(8,test.getPressUnit());
        pstmt.setString(9,test.getSaveFile());
        pstmt.setString(10,test.getImgFile());
        pstmt.setFloat(11,test.getSpeed());
        pstmt.setFloat(12,test.getRunTime());
        pstmt.setString(13,test.getShape());
        pstmt.setFloat(14,test.getMaxLoad());
        pstmt.setFloat(15,test.getDeep()==null?0F:test.getDeep());
        pstmt.setFloat(16,test.getArea()==null?0F:test.getArea());
        pstmt.setFloat(17,test.getMpa()==null?0F:test.getMpa());
        pstmt.setString(18,test.getSimpleName());
        pstmt.setFloat(19,test.getLo()==null?0F:test.getLo());
        pstmt.setFloat(20,test.getExtension()==null?0F:test.getExtension());
        pstmt.setFloat(21,test.getMinLoad()==null?0F:test.getMinLoad());
        pstmt.setFloat(22,test.getAvgLoad()==null?0F:test.getAvgLoad());
        pstmt.setFloat(23,test.getWidth()==null?0F:test.getWidth());
        pstmt.setFloat(24,test.getHou()==null?0F:test.getHou());
        pstmt.setFloat(25,test.getDia()==null?0F:test.getDia());
        pstmt.setFloat(26,test.getBlqd()==null?0F:test.getBlqd());
        pstmt.setFloat(27,test.getDlzLoad1()==null?0F:test.getDlzLoad1());
        pstmt.setFloat(28,test.getDlzLoad2()==null?0F:test.getDlzLoad2());
        pstmt.setFloat(29,test.getDwyPos1()==null?0F:test.getDwyPos1());
        pstmt.setFloat(30,test.getDwyPos2()==null?0F:test.getDwyPos2());
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
        pstmt.setDate(1,test.getTestTime());
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
     * 根据实验标准列出实验
     * @param stand
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Test> listByStandard(String stand) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select max(test_num) as test_num,max(test_time) as test_time,max(program_name) as program_name,max(standard_name) as standard_name, max(transform_sensor) as transform_sensor," +
                "max(load_unit) as load_unit,max(transform_unit) as transform_unit,max(press_unit) as press_unit,max(save_file) as save_file,max(speed) as speed," +
                "max(run_time) as run_time,max(shape) as shape from t_test where standard_name = ? group by save_file";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,stand);
        ResultSet set = pstmt.executeQuery();
        List<Test> list = new ArrayList<>();
        while(set.next()) {
            Test test = new Test();
            test.setTestNum(set.getLong("test_num"));
            test.setTestTime(set.getDate("test_time"));
            test.setProgramName(set.getString("program_name"));
            test.setStandardName(set.getString("standard_name"));
            test.setTransformSensor(set.getString("transform_sensor"));
            test.setLoadUnit(set.getString("load_unit"));
            test.setTransformUnit(set.getString("transform_unit"));
            test.setPressUnit(set.getString("press_unit"));
            test.setSaveFile(set.getString("save_file"));
            test.setSpeed(set.getFloat("speed"));
            test.setRunTime(set.getLong("run_time"));
            test.setShape(set.getString("shape"));
            list.add(test);
        }
        return list;
    }

    /**
     * 根据保存文件名查询所有试验
     * @param saveFile
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public List<Test> listBySaveFile(String saveFile) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "select * from t_test where save_file = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,saveFile);
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
            test.setTestTime(set.getDate("test_time"));
            test.setProgramName(set.getString("program_name"));
            test.setStandardName(set.getString("standard_name"));
            test.setTransformSensor(set.getString("transform_sensor"));
            test.setLoadUnit(set.getString("load_unit"));
            test.setTransformUnit(set.getString("transform_unit"));
            test.setPressUnit(set.getString("press_unit"));
            test.setSaveFile(set.getString("save_file"));
            test.setImgFile(set.getString("img_file"));
            test.setSpeed(set.getFloat("speed"));
            test.setRunTime(set.getLong("run_time"));
            test.setShape(set.getString("shape"));
            test.setMaxLoad(set.getFloat("max_load"));
            test.setMaxLoadPos(set.getFloat("max_load_pos"));
            test.setDeep(set.getFloat("deep"));
            test.setArea(set.getFloat("Area"));
            test.setMpa(set.getFloat("mpa"));
            test.setSimpleName(set.getString("Area"));
            test.setLo(set.getFloat("Lo"));
            test.setExtension(set.getFloat("extension"));
            test.setMinLoad(set.getFloat("min_load"));
            test.setAvgLoad(set.getFloat("avg_load"));
            test.setWidth(set.getFloat("width"));
            test.setHou(set.getFloat("hou"));
            test.setDia(set.getFloat("dia"));
            test.setBlqd(set.getFloat("blqd"));
            test.setDlzLoad1(set.getFloat("dlz_load1"));
            test.setDlzLoad2(set.getFloat("dlz_load2"));
            test.setDwyPos1(set.getFloat("dwy_pos1"));
            test.setDwyPos2(set.getFloat("dwy_pos2"));
            list.add(test);
        }
        return list;
    }

    /**
     * 批量保存实验数据
     * @param list
     */
    public void batchSaveTestData(List<TestData> list) throws SQLException, ClassNotFoundException {
        Connection conn = DBFileHelper.getConnection();
        String sql = "insert into t_test_data(test_num,time_val,load_val1,load_val2,load_val3,pos_val,deform_val) values (?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                TestData t = list.get(i);
                pstmt.setLong(1,t.getTestNum());
                pstmt.setLong(2,t.getTimeValue());
                pstmt.setFloat(3,t.getLoadVal1());
                pstmt.setFloat(4,t.getLoadVal2());
                pstmt.setFloat(5,t.getLoadVal3());
                pstmt.setFloat(6,t.getPosVal());
                pstmt.setFloat(7,t.getDeformVal());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            pstmt.clearBatch();
        }
        pstmt.close();
    }

    /**
     * 根据实验编号查询实验数据
     * @param testNum
     * @return
     */
    public List<TestData> listTestDateByTestNum(Long testNum) throws SQLException, ClassNotFoundException {
        Connection conn = DBFileHelper.getConnection();
        String sql = "select * from t_test_data where test_num = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1,testNum);
        ResultSet set = pstmt.executeQuery();
        return createTestDataList(set);
    }

    /**
     * 创建testData list
     * @param set
     * @return
     * @throws SQLException
     */
    private List<TestData> createTestDataList(ResultSet set) throws SQLException {
        List<TestData> list = new ArrayList<>();
        while(set.next()){
            TestData test = new TestData();
            test.setTestNum(set.getLong("test_num"));
            test.setTimeValue(set.getLong("time_val"));
            test.setLoadVal1(set.getFloat("load_val1"));
            test.setLoadVal2(set.getFloat("load_val2"));
            test.setLoadVal3(set.getFloat("load_val3"));
            test.setPosVal(set.getFloat("pos_val"));
            test.setDeformVal(set.getFloat("deform_val"));
            list.add(test);
        }
        return list;
    }

    /**
     * 保存实验参数
     */
    public void batchSaveTestParam(Long testNum, HashMap<String,String> param) throws SQLException, ClassNotFoundException {
        Connection conn = AccessHelper.getConnection();
        String sql = "insert into t_test_param(test_num,param) values (?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        Iterator iterator = param.values().iterator();
        while (iterator.hasNext()){
            pstmt.setLong(1,testNum);
            pstmt.setString(2, (String) iterator.next());
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        pstmt.clearBatch();
        pstmt.close();
    }


}
