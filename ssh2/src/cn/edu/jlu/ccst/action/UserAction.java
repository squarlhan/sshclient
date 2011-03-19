package cn.edu.jlu.ccst.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.ResultSet;
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
	private String mykeyword;
	private List<String> resultlist;
	private List<User> userlist;

	public List<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public List<String> getResultlist() {
		return resultlist;
	}

	public void setResultlist(List<String> resultlist) {
		this.resultlist = resultlist;
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

	public String  addUser() {
		if(userService.exits(user.getUsername())){
			return ERROR;
		}
		userService.save(user);
		userlist = userService.findall();
		try {
			resultlist = se.doquery1(se.loadDB2nd(), mykeyword);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
