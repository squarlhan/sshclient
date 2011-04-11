package cn.edu.jlu.ccst.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.edu.jlu.ccst.dao.UserServiceImpl;
import cn.edu.jlu.ccst.model.User;
import cn.edu.jlu.ccst.service.SimpleExample;
import cn.edu.jlu.ccst.service.UserService;

@Component("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport {

	private static final String REGIST = "regist";
	private static final String FORGET = "forget";

	private UserService userService;
	private UserServiceImpl userServiceImpl;
	private User user;
	private SimpleExample se;
	private String mykeyword;
	
	@Resource
	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	public String getMykeyword() {
		return mykeyword;
	}

	public void setMykeyword(String mykeyword) {
		this.mykeyword = mykeyword;
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

	public String login() {

		/*
		 * ActionContext ac = ActionContext.getContext(); Map<String, Object>
		 * map = (Map<String, Object>)ac.getSession(); map.put("USERNAME",
		 * user.getUsername()); map.put("PASSWORD", user.getPassword());
		 */

		if (userServiceImpl.exits(user.getUsername())) {
			User person = userServiceImpl.findBYusername(user);
			user=person;
			if (user.getPassword().equals(person.getPassword())||user.getPassword().equals(person.getCaptcha())) {
				ActionContext.getContext().getSession()
				.put("USERNAME", user.getUsername());
		ActionContext.getContext().getSession()
				.put("PASSWORD", user.getPassword());
				return SUCCESS;
			} else {
				return ERROR;
			}
		} else {
			return ERROR;
		}
	}
	
	

	public String regist() {
		return REGIST;
	}

	public String forgetPassword() {
		return FORGET;
	}

	
}
