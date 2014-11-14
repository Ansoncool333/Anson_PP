package cn.anson.wechat.entity;

import java.util.Date;

import javax.persistence.Id;

import org.hibernate.annotations.Entity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cn.anson.wechat.annotation.Display;
import cn.anson.wechat.annotation.Label;

@Label("�û���")
public class User {

	@Id
    @Label("�û�ID")
    long userId;

    @Label("�û���")
    @Length(min=2, max=45)
    @NotEmpty
    String name;

    @Label("����ʱ��")
    Date createAt;
    
    @Label("����¼ʱ��")
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