package cn.anson.wechat.dao;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class WechatOrm {

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader; 
	
	static{
	    try{
	        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	        reader    = Resources.getResourceAsReader("Configuration.xml");
		}catch(Exception e){
		    e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSession(){
	    return sqlSessionFactory;
	}
}
