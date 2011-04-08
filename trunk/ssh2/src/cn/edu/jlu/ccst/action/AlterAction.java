package cn.edu.jlu.ccst.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.dao.UserServiceImpl;
import cn.edu.jlu.ccst.model.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Component("alterAction")
public class AlterAction extends ActionSupport {
	private String newpassword;
	private String renewpassword;
	private User user;
	private String currentpassword;
	private UserServiceImpl userServiceImpl;
	
@Resource
	public void setUserServiceImpl(UserServiceImpl userServiceImpl){
		this.userServiceImpl=userServiceImpl;
	}
public UserServiceImpl getUserServiceImpl(){
	return userServiceImpl;
}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setRenewpassword(String renewpassword) {
		this.renewpassword = renewpassword;
	}

	public String getRenewpassword() {
		return renewpassword;
	}

	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String alterpass() {
		String username=(String)ActionContext.getContext().getSession().get("USERNAME");
		user.setUsername(username);
		String password=(String)ActionContext.getContext().getSession().get("PASSWORD");
		user.setPassword(password);
		if (user.getPassword().equals(currentpassword)) {
			user.setPassword(newpassword);
			User person=userServiceImpl.findBYusername(user);
			person.setPassword(newpassword);
			user=person;
			userServiceImpl.update(userServiceImpl.findID(person));
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public void setCurrentpassword(String currentpassword) {
		this.currentpassword = currentpassword;
	}

	public String getCurrentpassword() {
		return currentpassword;
	}

}
