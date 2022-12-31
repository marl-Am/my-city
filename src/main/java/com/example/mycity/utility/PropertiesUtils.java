package com.example.mycity.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
    private static final String PROPERTIES_FILE = "application-dev.properties";

    public static Map<String, String> getProperties() throws IOException {
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        Properties properties = new Properties();
        properties.load(inputStream);

        Map<String, String> map = new HashMap<>();
        for (String name : properties.stringPropertyNames()) {
            map.put(name, properties.getProperty(name));
        }
        return map;
    }
}

