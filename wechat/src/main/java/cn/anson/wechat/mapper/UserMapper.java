package cn.anson.wechat.mapper;
 
import java.util.List;

import cn.anson.wechat.entity.User;
 
public interface UserMapper {
 
    public User selectUser(int id);
   
    public List<User> selectAll();
   
}