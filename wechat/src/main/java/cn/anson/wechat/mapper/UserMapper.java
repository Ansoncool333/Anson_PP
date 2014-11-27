package cn.anson.wechat.mapper;
 
import java.util.List;

import org.apache.ibatis.annotations.Select;

import cn.anson.wechat.entity.User;
 
public interface UserMapper {
 
	@Select("select * from user where userId=#{id}")
    public User selectUserByID(int id);
   
    public List<User> selectAll();
   
}