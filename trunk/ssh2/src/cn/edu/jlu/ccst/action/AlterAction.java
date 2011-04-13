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
	private String tip;
	
	@Resource
	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public UserServiceImpl getUserServiceImpl() {
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
		String username = (String) ActionContext.getContext().getSession()
				.get("USERNAME");
		user.setUsername(username);
		String password = (String) ActionContext.getContext().getSession()
				.get("PASSWORD");
		user.setPassword(password);
		User person = userServiceImpl.findBYusername(user);
		if (person.getPassword().equals(currentpassword)
				|| person.getCaptcha().equals(currentpassword)) {
			person.setPassword(newpassword);

			userServiceImpl.update(userServiceImpl.findID(person));
			user = person;
			ActionContext.getContext().getSession()
					.put("PASSWORD", user.getPassword());
			setTip("You have altered your password successfully!");
			return SUCCESS;
		} else {
			tip="The password is not correct!";
			return ERROR;
		}
	}

	public void setCurrentpassword(String currentpassword) {
		this.currentpassword = currentpassword;
	}

	public String getCurrentpassword() {
		return currentpassword;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}

	

	

}
