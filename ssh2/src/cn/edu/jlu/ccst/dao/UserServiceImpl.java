package cn.edu.jlu.ccst.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jlu.ccst.model.User;


@Component("userServiceImpl")
@Transactional
public class UserServiceImpl {
	private EntityManager em;

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
		if (user.getId() <= 0) {
			// new
			em.persist(user);
		} else {
			// update
			em.merge(user);
		}
	}

	public void remove(int id) {
		User person = find(id);
		if (person != null) {
			em.remove(person);
		}
	}

	private EntityManager getEntityManager() {
		return em;
	}

	public User find(int id) {
		return em.find(User.class, id);
	}

}