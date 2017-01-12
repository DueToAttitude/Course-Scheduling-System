package com.core;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by recycle on 1/6 0006.
 */
public class SettingProp {
    private int num;

    private static Properties properties = new Properties();

    public SettingProp() {
        try {
            InputStream in = getClass().getResourceAsStream("/com/info.properties");
            properties.load(in);
            num = Integer.parseInt(properties.getProperty("num"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        try {
            this.num = num;
            properties.setProperty("num", Integer.toString(num));
            OutputStream out = new FileOutputStream(new File((getClass().getResource("/com/info.properties")).toURI()));
            properties.store(out, "the primary key of article table");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
