package cn.edu.jlu.ccst.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;


import cn.edu.jlu.ccst.model.User;
@Component("userService")
public class UserService {
	private User user;

	public User getUser() {
		return user;
	}
	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public void save(User user) {
		System.out.println("USER:"+user.getUsername()+" ; "+user.getPassword());
	}
	
	public boolean exits(String username){
		return false;
	}
}
