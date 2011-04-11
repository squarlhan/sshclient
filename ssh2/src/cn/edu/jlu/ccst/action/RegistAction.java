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
		/*
		 * user.setUsername(username); user.setPassword(password);
		 * user.setSurname(surname); user.setRepassword(repassword);
		 * user.setQuestion(question); user.setAnswer(answer);
		 */
		if (userServiceImpl.exits(user.getUsername())) {
			return ERROR;
		}
		userService.save(user);
		userServiceImpl.setUserlist(userService.findall()) ;
		return SUCCESS;
	}

	@Resource
	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}
}