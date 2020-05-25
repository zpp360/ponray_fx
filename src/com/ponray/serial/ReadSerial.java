package com.ponray.serial;

import java.io.IOException;
import java.io.InputStream;

public class ReadSerial extends Thread
{
    private SerialBuffer ComBuffer;
    private InputStream ComPort;
    /**
     *
     * Constructor
     *
     * @param SB The buffer to save the incoming messages.
     * @param Port The InputStream from the specific serial port.
     *
     */
    public ReadSerial(SerialBuffer SB, InputStream Port)
    {
        ComBuffer = SB;
        ComPort = Port;
    }
    public void run()
    {
        int c;
        try
        {
            while (true)
            {
                c = ComPort.read();
                ComBuffer.PutChar(c);
            }
        } catch (IOException e) {}
    }
}

