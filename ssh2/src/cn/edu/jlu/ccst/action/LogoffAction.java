package cn.edu.jlu.ccst.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.edu.jlu.ccst.dao.UserServiceImpl;
import cn.edu.jlu.ccst.model.User;

@Component("logoffAction")
public class LogoffAction extends ActionSupport{
	private String username;
	private String password;
	private User user;
	private UserServiceImpl userServiceImpl;

	@Resource
	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	public User getUser() {
		return user;
	}

	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public String logoff() {
		ActionContext.getContext().getSession().remove("USERNAME");
		ActionContext.getContext().getSession().remove("PASSWORD");
		return SUCCESS;
	}

}
