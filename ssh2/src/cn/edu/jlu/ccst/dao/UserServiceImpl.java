package cn.edu.jlu.ccst.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jlu.ccst.model.User;
import cn.edu.jlu.ccst.service.UserService;

@Component("userServiceImpl")
@Transactional
public class UserServiceImpl {
	private EntityManager em;
	private UserService userService;
	private User user;
	private List<User> userlist;
	private List<String> resultlist;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Query query = getEntityManager().createQuery("select u FROM User u");
		return query.getResultList();
	}

	public void save(User user) {
		if (exits(user.getUsername())) {
			// update
			em.merge(user);

		} else {
			// new
			em.persist(user);

		}

	}

	public void remove(int id) {
		User person = findBYid(id);
		if (person != null) {
			em.remove(person);
		}
	}

	private EntityManager getEntityManager() {
		return em;
	}

	public User findBYid(int id) {
		return em.find(User.class, id);
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void update(int id) {
		User person = findBYid(id);
		if (exits(person.getUsername())) {
			// update
			em.merge(person);
		}
	}

	

	public boolean exits(String username) {
		Query query = getEntityManager().createQuery(
				"select u FROM User u where u.username = '"+username+"'");
		//System.out.println(query.getResultList().size());
		List<User> userlist=findAll();
		//System.out.println(userlist.size());
		if (query.getResultList().size() >= 1) {
			return true;
		} else {
			return false;
		}
	}

	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	
	@SuppressWarnings("unchecked")
	public User findBYusername(User user) {

	
		Query query = getEntityManager().createQuery(
				"select u FROM User u where u.username = username");
		userlist = query.getResultList();
		for (User u : userlist) {
			if (u.getUsername().equals(user.getUsername())) {
				return u;
			}
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public int findID(User user) {
		Query query = getEntityManager().createQuery(
				"select u FROM User u where u.username = username");
		userlist = query.getResultList();
		for (User u : userlist) {
			if (u.getUsername().equals(user.getUsername())) {
				return u.getId();
			}
		}
		return 0;

	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public List<User> getUserlist() {
		return userlist;
	}

	public void setResultlist(List<String> resultlist) {
		this.resultlist = resultlist;
	}

	public List<String> getResultlist() {
		return resultlist;
	}

}