package cn.anson.wechat.entity;

import java.util.Date;

public class User {

    long userId;

    String name;

    Date createAt;
    
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

    public String display() {
        StringBuilder buf = new StringBuilder();
        buf.append(userId).append('-').append(name);
        return buf.toString();
    }
}