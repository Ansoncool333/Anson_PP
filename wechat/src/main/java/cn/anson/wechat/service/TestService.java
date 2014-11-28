package cn.anson.wechat.service;

import org.apache.ibatis.session.SqlSession;

import cn.anson.wechat.dao.WechatOrm;
import cn.anson.wechat.entity.User;
import cn.anson.wechat.util.EnvUtils;
import cn.anson.wechat.util.SpringCtxUtils;

public class TestService {
	public String testSelect(){
//		WechatOrm orm = new WechatOrm();
//		WechatOrm orm = SpringCtxUtils.getBean(WechatOrm.class);
		SqlSession session = (new WechatOrm()).getSession().openSession();
//		SqlSession session = orm.getSession().openSession();
		 try {
		        User user = (User) session.selectOne("cn.anson.wechat.mapper.UserMapper.selectUserByID", 1);
//		        System.out.println(user.getUserAddress());
		        return user.getName();
		        } finally {
		        session.close();
		        }
	}
}
