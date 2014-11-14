package cn.anson.wechat.entity;

import java.util.Date;

import javax.persistence.Id;

import org.hibernate.annotations.Entity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cn.anson.wechat.annotation.Display;
import cn.anson.wechat.annotation.Label;

@Label("用户表")
public class User {

	@Id
    @Label("用户ID")
    long userId;

    @Label("用户名")
    @Length(min=2, max=45)
    @NotEmpty
    String name;

    @Label("创建时间")
    Date createAt;
    
    @Label("最后登录时间")
    Date loginAt;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	@Display
    public String display() {
        StringBuilder buf = new StringBuilder();
        buf.append(userId).append('-').append(name);
        return buf.toString();
    }
}