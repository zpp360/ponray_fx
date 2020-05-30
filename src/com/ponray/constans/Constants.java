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
    public final static String KQL = "开启力";//分析峰值

    public final static String ZDL = "折断力";//分析峰值

    public final static String LSL = "拉伸力";//分析峰值，和峰值对应的位移

    /**
     * 拉伸力用到的参数key
     */
    public final static String LSL_K = "宽度";

    public final static String LSL_H = "厚度";

    public final static String LSL_D = "直径";

    public final static String LSL_LO = "标距";

    public final static String BANCAI = "板材";

    public final static String BANGCAI = "棒材";



    public final static String CCL = "穿刺力";//穿刺力

    /**
     * 穿刺深度key
     */
    public final static String CCL_DEEP = "穿刺深度";

    public final static String DLZ = "定力值";

    public final static String DWY = "定位移";

    public final static String BLL = "剥离力";

    public final static String HSHDXCS = "活塞滑动性测试";



    public final static String SIMPLE_NAME = "试样名称";

}
