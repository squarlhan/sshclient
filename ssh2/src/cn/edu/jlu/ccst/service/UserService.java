package cn.edu.jlu.ccst.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.dao.UserServiceImpl;
import cn.edu.jlu.ccst.model.User;

@Component("userService")
public class UserService {
	private User user;
	private UserServiceImpl userServiceImpl;
	//private List<User> userlist;

	public UserServiceImpl getUserServiceImpl() {
		return userServiceImpl;
	}

	@Resource
	public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public User getUser() {
		return user;
	}

	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public void save(User user) {
		System.out.println("USER:" + user.getUsername() + " ; "
				+ user.getPassword());
		userServiceImpl.save(user);
	}

	public List<User> findall() {
		//userlist = new ArrayList();
		userServiceImpl.setUserlist(userServiceImpl.findAll()) ;
		return userServiceImpl.getUserlist();
	}

	/*public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public List<User> getUserlist() {
		return userlist;
	}*/

	

}
