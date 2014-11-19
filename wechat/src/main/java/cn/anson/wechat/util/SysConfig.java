package cn.anson.wechat.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SysConfig{
	private Properties properties = new Properties();
	
	public SysConfig(String fileName) throws FileNotFoundException, IOException {
		properties.load(new BufferedInputStream(new FileInputStream(fileName)));
	}

	public String getConfig(String key){
		return properties.getProperty(key);
	}
}
