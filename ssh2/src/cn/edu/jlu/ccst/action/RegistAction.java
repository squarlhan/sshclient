package cn.edu.jlu.ccst.action;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.dao.UserServiceImpl;
import cn.edu.jlu.ccst.model.User;

import cn.edu.jlu.ccst.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@Component("registAction")
public class RegistAction extends ActionSupport {
	private UserService userService;
	private UserServiceImpl userServiceImpl;
	private User user;
	private String tip;


	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String addUser() {
		if (userServiceImpl.exits(user.getUsername())) {
			tip="The username is exist!";
			return ERROR;
		}
		userService.save(user);
		userServiceImpl.setUserlist(userService.findall()) ;
		tip="Regist successfully!Please login before you search!";
		return SUCCESS;
	}

	@Resource
	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}
}
