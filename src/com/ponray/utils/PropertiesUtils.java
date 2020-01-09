package com.ponray.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties getProperties(InputStream inputStream) throws IOException {
        Properties prop = new Properties();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        prop.load(in);
        return prop;
    }

}
