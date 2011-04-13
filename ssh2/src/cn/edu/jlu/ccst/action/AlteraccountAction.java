package cn.edu.jlu.ccst.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.dao.UserServiceImpl;
import cn.edu.jlu.ccst.model.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Component("alteraccountAction")
public class AlteraccountAction extends ActionSupport {


	private User user;
	private String currentpassword;
	private UserServiceImpl userServiceImpl;
	private String newusername;
	private String newsurname;
	private String newgivenname;
	private String neworganization;
	private String newphone;
	private String tip;

	@Resource
	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	

	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setCurrentpassword(String currentpassword) {
		this.currentpassword = currentpassword;
	}

	public String getCurrentpassword() {
		return currentpassword;
	}

	public String alteraccount() {
		String username = (String) ActionContext.getContext().getSession()
				.get("USERNAME");
		user.setUsername(username);
		String password = (String) ActionContext.getContext().getSession()
				.get("PASSWORD");
		user.setUsername(username);
		if (currentpassword.equals(password)) {
			User person = userServiceImpl.findBYusername(user);
			if (!newusername.isEmpty()&&(!userServiceImpl.exits(username))) {
				person.setUsername(newusername);
			}
			else {
				setTip("The username is exist!Please try another username!");
				return ERROR;
			}
			if (!newsurname.isEmpty()) {
				person.setSurname(newsurname);
			}
			if (!newgivenname.isEmpty()) {
				person.setGivenname(newgivenname);
			}
			if (!neworganization.isEmpty()) {
				person.setOrganization(neworganization);
			}
			if (!newphone.isEmpty()) {
				person.setPhone(newphone);
			}
			userServiceImpl.update(person.getId());
			return SUCCESS;
		} else{
			setTip("The password is not correct!");
			return ERROR;
		}
	}
	public void setNewusername(String newusername) {
		this.newusername = newusername;
	}

	public String getNewusername() {
		return newsurname;
	}

	public void setNewsurname(String newsurname) {
		this.newsurname = newsurname;
	}

	public String getNewsurname() {
		return newsurname;
	}

	public void setNewgivenname(String newgivenname) {
		this.newgivenname = newgivenname;
	}

	public String getNewgivenname() {
		return newgivenname;
	}

	public void setNeworganization(String neworganization) {
		this.neworganization = neworganization;
	}

	public String getNeworganization() {
		return neworganization;
	}

	public void setNewphone(String newphone) {
		this.newphone = newphone;
	}

	public String getNewphone() {
		return newphone;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}

}
