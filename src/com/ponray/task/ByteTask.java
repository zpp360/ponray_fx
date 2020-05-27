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
                            byte[] cache1 = new byte[1];
                            in.read(cache1);
                            if(Arrays.equals(cache1,ByteUtils.A5)){
                                byte[] cache2 = new byte[1];
                                in.read(cache2);
                                if(Arrays.equals(cache2,ByteUtils.A6)){
                                    byte[] cache3 = new byte[23];
                                    in.read(cache3);
                                    readBuffer = ArrayUtils.concat(cache1, cache2);
                                    readBuffer = ArrayUtils.concat(readBuffer, cache3);
                                }
                                UIOnline.byteList.offer(readBuffer);
                            }


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
