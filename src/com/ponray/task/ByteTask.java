package com.ponray.task;

import com.ponray.main.UIOnline;
import com.ponray.utils.ArrayUtils;
import com.ponray.utils.ByteUtils;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

import java.io.InputStream;
import java.util.Arrays;

public class ByteTask extends ScheduledService<byte[]> {
    @Override
    protected Task<byte[]> createTask() {
        Task<byte[]> task = new Task<byte[]>() {
            @Override
            protected byte[] call() throws Exception {
                InputStream in = null;
                //定义用于缓存读入数据的数组
                byte[] readBuffer = new byte[25];
                try {
                        if( UIOnline.mSerialport != null){
                            in = UIOnline.mSerialport.getInputStream();
                            //如果可用字节数大于零则开始循环并获取数据
                            byte[] cache = new byte[2];
                            in.read(cache);
                            if(Arrays.equals(cache,ByteUtils.A55A)){
                                byte[] end = new byte[23];
                                in.read(end);
                                readBuffer = ArrayUtils.concat(cache, end);
                                UIOnline.byteList.offer(readBuffer);
                            }
//                        int bytesNum = in.read(readBuffer);
//                        while (!Arrays.equals(cache,ByteUtils.A55A)) {
//                            readBuffer = ArrayUtils.concat(readBuffer, cache);
//                        }

                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return readBuffer;
            }
        };
        return task;
    }
}
