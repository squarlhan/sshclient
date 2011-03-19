package cn.edu.jlu.ccst.dao;

import java.util.List;

import cn.edu.jlu.ccst.model.User;

public interface UserServiceInter {
	
	public List<User> findAll();

	public void save(User user);

	public void remove(int id);

	public User find(int id);

}
