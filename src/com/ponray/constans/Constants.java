package com.ponray.constans;

import java.util.Properties;

public class Constants {

    public static Properties language = null;

    public final static String BLANK = "";

    /**
     * 添加操作
     */
    public final static String ADD = "add";
    /**
     * 插入
     */
    public final static String INSERT = "insert";
    /**
     * 修改操作
     */
    public final static String EDIT = "edit";
    /**
     * 删除操作
     */
    public final static String DEL = "del";

    /**
     * 添加参数
     */
    public final static String ADD_PARAM = "add_param";
    /**
     * 添加公式
     */
    public final static String ADD_FORMULA = "add_formula";
    /**
     * 修改参数
     */
    public final static String EDIT_PARAM = "edit_param";
    /**
     * 修改公式
     */
    public final static String EDIT_FORMULA = "edit_formula";

    /**
     * 基本单位 力
     */
    public final static String BASE_UNIT_N = "N";
    /**
     * 基本单位 变形
     */
    public final static String BASE_UNIT_TRANSFORM = "mm";
    /**
     * 基本单位 应力
     */
    public final static String BASE_UNIT_YL = "MPa";

    public final static int INT_ZERO = 0;
    //拉
    public final static int INT_ONE = 1;
    //压
    public final static int INT_TWO = 2;

    public final static String STR_ZERO = "0";


    public final static String A55A = "A55A";

    /**
     * 实验状态
     */
    public final static String TEST_STATUS = "状态";
    public final static String TEST_STATUS_NOSTART = "未开始";
    public final static String TEST_STATUS_ING = "进行中";
    public final static String TEST_STATUS_END = "已结束";

    /**
     * 实验名称
     */
    public final static String KQL = "开启力";

    public final static String ZDL = "折断力";

}
