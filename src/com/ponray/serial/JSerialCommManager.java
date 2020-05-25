package com.ponray.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.ponray.utils.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class JSerialCommManager {
    /**
     * 查找所有可用端口
     *
     * @return 可用端口名称列表
     */
    public static final Map<String,SerialPort> findPorts() {
        // 获得当前所有可用串口
        SerialPort[] portList = SerialPort.getCommPorts();
        Map<String,SerialPort> map = new HashMap<>();
        // 将可用串口名添加到List并返回该List
        if(portList!=null && portList.length>0){
            for (int i =0;i<portList.length;i++){
                map.put(portList[i].getSystemPortName(),portList[i]);
            }
        }
        return map;
    }

    /**
     * 打开串口
     *
     *             串口已被占用
     */
    public static final SerialPort openPort(SerialPort serialPort) {
        serialPort.openPort();
        return serialPort;
    }

    /**
     * 关闭串口
     *
     * @param serialPort
     *            待关闭的串口对象
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.closePort();
        }
    }

    /**
     * 往串口发送数据
     *
     * @param serialPort
     *            串口对象
     * @param order
     *            待发送数据
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) {
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从串口读取数据
     *
     * @param serialPort
     *            当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public static byte[] readFromPort(SerialPort serialPort) {
        InputStream in = null;
        byte[] bytes = {};
        try {
            in = serialPort.getInputStream();
            // 缓冲区大小为一个字节
            byte[] readBuffer = new byte[25];
            int bytesNum = in.read(readBuffer);
            while (bytesNum > 0) {
                bytes = ArrayUtils.concat(bytes, readBuffer);
                bytesNum = in.read(readBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }




}
