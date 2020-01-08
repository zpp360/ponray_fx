package com.ponray.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties getProperties(String path) throws IOException {
        Properties prop = new Properties();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        prop.load(in);
        return prop;
    }

}
