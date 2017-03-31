package Util;

import java.util.Properties;

public class ReadProperties {
    private Properties cfg = new Properties();

    //读取配置文件
    public ReadProperties(String fileName) {
        try {
            cfg.load(this.getClass().getResourceAsStream(fileName));
           
        } catch (Exception e) {
        	
            e.printStackTrace();
        }
    }

    //返回字符结果
    public String getString(String key) {
        return cfg.getProperty(key);
    }

    //返回整数结果
    public int getInt(String key) {
        return Integer.parseInt(cfg.getProperty(key));
    }

    //返回高精浮点结果
    public Double getDouble(String key) {
        return Double.parseDouble(cfg.getProperty(key));
    }
}
