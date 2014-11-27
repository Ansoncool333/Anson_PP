package cn.anson.wechat.impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.anson.wechat.entity.User;
import cn.anson.wechat.mapper.UserMapper;
 
@Service
public class UserImpl {
 
	@Autowired
    private UserMapper userMapper;
   
    public User find(int id) {
       return userMapper.selectUserByID(id);
    }
 
    public List<User> find() {
       return userMapper.selectAll();
    }
 
    public UserMapper getUserMapper() {
       return userMapper;
    }
 
}