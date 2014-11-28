package cn.anson.wechat.service;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import cn.anson.wechat.entity.User;

public class UserService {
	public String testSelect(){
		return "oh,yeah!";
	}
//	private static SqlSessionFactory sqlSessionFactory;
//    private static Reader reader; 
//
//    static{
//        try{
//            reader    = Resources.getResourceAsReader("Configuration.xml");
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static SqlSessionFactory getSession(){
//        return sqlSessionFactory;
//    }
//    
//    public static void main(String[] args) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//        User user = (User) session.selectOne("cn.anson.wechat.mapper.UserMapper.selectUserByID", 1);
////        System.out.println(user.getUserAddress());
//        System.out.println(user.getName());
//        } finally {
//        session.close();
//        }
//    }
//	public static void main(String[] args){
//		UserImpl userImpl = SpringCtxUtils.getBean(UserImpl.class);
//		List<User> list = userImpl.find();
//		System.out.println("list: "+list);
//	}
}
