package cn.edu.jlu.ccst.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import cn.edu.jlu.ccst.model.User;
import cn.edu.jlu.ccst.service.SimpleExample;
import cn.edu.jlu.ccst.service.UserService;

@Component("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport {

	private UserService userService; 
	private User user;
	private SimpleExample se;
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public SimpleExample getSe() {
		return se;
	}

	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String  addUser() {
		if(userService.exits(user.getUsername())){
			return ERROR;
		}
		userService.save(user);
		try {
			se.doquery(se.loadDB2nd(), null);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
