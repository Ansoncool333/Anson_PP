package cn.anson.wechat.dao;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisDao {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader; 
	
	static{
	    try{
	        reader    = Resources.getResourceAsReader("Configuration.xml");
	        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		}catch(Exception e){
		    e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSession(){
	    return sqlSessionFactory;
	}
}
