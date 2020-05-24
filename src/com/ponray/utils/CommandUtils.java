package com.ponray.utils;


public class CommandUtils {

    public static final String A55A = "A55A";


    /**
     * 上升
     */
    public static final String UP = "05";
    /**
     * 停止
     */
    public static final String STOP = "06";
    /**
     * 下降
     */
    public static final String DOWN = "07";
    /**
     * 实验开始
     */
    public static final String START = "08";
    /**
     * 复位
     */
    public static final String RESET = "09";

    /**
     * 力
     */
    public static final String LOAD = "0A";
    /**
     * 位移
     */
    public static final String POS = "0B";
    /**
     * 变形
     */
    public static final String TRANSFORM = "0C";

    public static final String STR00 = "00";

    public static final String STR0 = "0";

    public static final String STR000000000 = "000000000000000000"; //18个0，9字节

    public static final String STR00000000 = "0000000000000000"; //16个0，8字节

    public static byte[] lastCommand = null;

    /**
     * 上升命令
     * @param speed
     * @return
     */
    public static byte[] commandUP(String speed){
        byte[] speedBytes = speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR00+UP+STR000000000);
        byte[] command = ByteUtils.byteMerger(header,speedBytes);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 停止命令
     * @return
     */
    public static byte[] commandStop(){
        byte[] command = ByteUtils.hexStringToBytes(A55A+STR00+STOP+STR000000000+STR00+STR00+STR00+STR00);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 下降
     * @param speed
     * @return
     */
    public static byte[] commandDown(String speed){
        byte[] speedBytes = speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR00+DOWN+STR000000000);
        byte[] command = ByteUtils.byteMerger(header,speedBytes);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 实验开始
     * @param direct 实验方向 拉向为1 压向2
     * @param speed 实验速度速度
     * @return
     */
    public static byte[] commandStart(int direct,String model,float data1,float data2,Float speed){
        byte[] speedBytes =speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR0+direct+START+model);
        byte[] data1Byte = ByteUtils.float2byte(data1);
        byte[] datta2Byte = ByteUtils.float2byte(data2);
        byte[] command = ByteUtils.byteMerger(header,data1Byte);
        command = ByteUtils.byteMerger(command,datta2Byte);
        command = ByteUtils.byteMerger(command,speedBytes);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 复位指令
     * @param speed
     * @return
     */
    public static byte[] commandReset(String speed){
        byte[] speedBytes =speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR00+RESET+STR000000000);
        byte[] command = ByteUtils.byteMerger(header,speedBytes);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 力清零
     * @param speed
     * @return
     */
    public static byte[] commandClearLoad(String speed){
        byte[] speedBytes =speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR00+LOAD+STR000000000);
        byte[] command = ByteUtils.byteMerger(header,speedBytes);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 位移清零
     * @param speed
     * @return
     */
    public static byte[] commandClearPos(String speed){
        byte[] speedBytes =speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR00+POS+STR000000000);
        byte[] command = ByteUtils.byteMerger(header,speedBytes);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 变形清零
     * @param speed
     * @return
     */
    public static byte[] commandClearTransform(String speed){
        byte[] speedBytes =speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR00+TRANSFORM+STR000000000);
        byte[] command = ByteUtils.byteMerger(header,speedBytes);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 获取速度的byte[]
     * @param speed
     * @return
     */
    public static byte[] speedBytes(String speed){
        byte[] speedBytes =ByteUtils.float2byte(Float.parseFloat(speed));
        //C++写入的字节顺序是从高到底，java写入的是从低到高，所以需要翻转byte[]
        byte[] reversalSpeedBytes = ByteUtils.byteReversal(speedBytes);
        return reversalSpeedBytes;
    }

    /**
     * 获取速度的byte[]
     * @param speed
     * @return
     */
    public static byte[] speedBytes(Float speed){
        byte[] speedBytes =ByteUtils.float2byte(speed);
        //C++写入的字节顺序是从高到底，java写入的是从低到高，所以需要翻转byte[]
        byte[] reversalSpeedBytes = ByteUtils.byteReversal(speedBytes);
        return reversalSpeedBytes;
    }

    /**
     * crc16校验，int生成4位byte，去掉前两位
     * @param command
     * @return
     */
    public static byte[] crc16(byte[] command){
        int crc16 =  CRC16Utils.calcCrc16(command);
        byte[] crc16Bytes = ByteUtils.getByteArray(crc16);
        byte[] crc16Bytes2 = new byte[2];
        System.arraycopy(crc16Bytes, 2, crc16Bytes2, 0, 2);
        return crc16Bytes2;
    }

}
