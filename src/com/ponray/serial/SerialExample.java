package com.ponray.serial;

class SerialExample
{
    public static void main(String[] args)
    {
        //TO DO: Add your JAVA codes here
        SerialBean SB = new SerialBean(1);
        String Msg;
        SB.Initialize();
        for (int i = 5; i <= 10; i++)
        {
            Msg = SB.ReadPort(i);
            SB.WritePort("Reply: " + Msg);
        }
        SB.ClosePort();
    }
}
