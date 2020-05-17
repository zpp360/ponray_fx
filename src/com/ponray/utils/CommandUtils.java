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

    public static final String STR00 = "00";

    public static final String STR00000000 = "00000000";

    public static byte[] lastCommand = null;

    /**
     * 上升命令
     * @param speed
     * @return
     */
    public static byte[] commandUP(String speed){
        byte[] speedBytes = speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR00+UP);
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
        byte[] command = ByteUtils.hexStringToBytes(A55A+STR00+STOP+STR00000000);
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
        byte[] header = ByteUtils.hexStringToBytes(A55A+STR00+DOWN);
        byte[] command = ByteUtils.byteMerger(header,speedBytes);
        //crc16校验
        byte[] crc16Bytes = crc16(command);
        //记录上一次发送的命令
        lastCommand = ByteUtils.byteMerger(command,crc16Bytes);
        return lastCommand;
    }

    /**
     * 实验开始
     * @param speed 实验速度速度
     * @param programNum 实验方案标号，传给硬件
     * @return
     */
    public static byte[] commandStart(Float speed,String programNum){
        byte[] speedBytes =speedBytes(speed);
        byte[] header = ByteUtils.hexStringToBytes(A55A+programNum+START);
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
