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
    public final static String TEST_STATUS_STOP = "停止";



    /**
     * 实验名称
     */
    public final static String KQL = "开启力";//分析峰值

    public final static String ZDL = "折断力";//分析峰值

    public final static String LSL = "拉伸力";//分析峰值，和峰值对应的位移

    /**
     * 拉伸力用到的参数key
     */
    public final static String WIDTH = "宽度";

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

    /**
     * 定力值，定位移实验阶段，实验结束置为0
     */
    public static int STAGE = 0;

    /**
     * 第二阶段命令是否已发
     */
    public static boolean STAGE2_SEND_FLAG = false;

    /**
     * 实验保持时间
     */
    public static Long KEEP_TIME = 0L;
    /**
     * 保持开始的时间
     */
    public static Long KEEP_START_TIME = 0L;

    /**
     * 力1 key
     */
    public final static String DLZ_LOAD1 = "力值1";
    /**
     * 力2 key
     */
    public final static String DLZ_LOAD2 = "力值2";

    /**
     * 时间1 key
     */
    public final static String TIME1 = "时间1";
    /**
     * 时间2 key
     */
    public final static String TIME2 = "时间2";

    public final static String DWY = "定位移";

    public final static String DWY_POS1 = "位移1";
    public final static String DWY_POS2 = "位移2";

    public final static String BLL = "剥离力";

    public final static String BLL_QSWY = "起始位移";

    public final static String BLL_JSWY = "结束位移";
    

    public final static String HSHDXCS = "活塞滑动性测试";



    public final static String SIMPLE_NAME = "试样名称";

    /**
     * 定力值，定位移用的data1
     */
    public static Float DATA1_VAL = 0F;
    /**
     * 定力值，定位移用的data2
     */
    public static Float DATA2_VAL = 0F;

    /**
     * 时间1值
     */
    public static Float TIME1_VAL = 0F;
    /**
     * 时间2值
     */
    public static Float TIME2_VAL = 0F;


    public static long TIME = 0L;

}
