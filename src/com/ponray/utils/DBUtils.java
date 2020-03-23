package com.ponray.utils;

import com.healthmarketscience.jackcess.*;
import com.sun.xml.internal.messaging.saaj.util.TeeInputStream;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;

public class DBUtils {
    //实验结果文件存储目录
    private final static String TEST_FILE_PATH = System.getProperty("user.dir") + File.separator + "datafile" + File.separator;

    /**
     * 创建db
     * @param name
     * @return
     * @throws IOException
     */
    public static Database createDBFile(String name) throws IOException {
        File dir = new File(TEST_FILE_PATH );
        if(!dir.exists()){
            dir.mkdirs();
        }
        Database db = DatabaseBuilder.create(Database.FileFormat.V2000, new File(TEST_FILE_PATH + name + ".mdb"));
        return db;
    }

    /**
     * 创建数据表
     * @param db
     * @return
     */
    public static Table createTableTestData(Database db){
        Table table = null;
        try {
            //刚才是创建文件，这里是在文件里创建表，字段名，字段类型
            table = new TableBuilder("t_data")
                    .addColumn(new ColumnBuilder("ID")
                            .setSQLType(Types.INTEGER).setAutoNumber(true))
                    .addColumn(new ColumnBuilder("test_num")
                            .setSQLType(Types.BIGINT))
                    .addColumn(new ColumnBuilder("time_val")   //时间值
                            .setSQLType(Types.FLOAT))
                    .addColumn(new ColumnBuilder("load_val")   //力值
                            .setSQLType(Types.FLOAT))
                    .addColumn(new ColumnBuilder("pos_val")    //位移值
                            .setSQLType(Types.FLOAT))
                    .addColumn(new ColumnBuilder("deform_val") //变形值
                            .setSQLType(Types.FLOAT))
                    .toTable(db);
            //插入一条数据测试
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    public static void main(String[] arg) throws IOException {
        Database db = createDBFile("2015-01-02-03");
        createTableTestData(db);
    }
}
