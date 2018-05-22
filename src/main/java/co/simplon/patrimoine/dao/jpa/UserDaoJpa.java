package co.simplon.patrimoine.dao.jpa;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import co.simplon.patrimoine.dao.UserDao;
import co.simplon.patrimoine.model.User;

public class UserDaoJpa implements UserDao{
	EntityManager em;
	
	public UserDaoJpa(EntityManager em) {
		this.em= em;
	}

	@Override
	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUserById(Long id) {
		// TODO Auto-generated method stub
		
	}	
}
