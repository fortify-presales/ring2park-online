package com.dps.ring2park.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dps.ring2park.dao.UserDao;
import com.dps.ring2park.domain.User;

/**
 * User DAO implementation.
 * @author Kevin A. Lee
 * 
 */
@Repository("UserDao")
public class UserDaoImpl extends GenericDAOImpl<User, String> implements UserDao {

	public void persist(User entity) {
		if (entity.getUsername() != null) { 
			super.entityManager.merge(entity);
		} else { 
			super.entityManager.persist(entity);
		}
	}
	
	// custom repository method
	public User findByUsername(String username) {
		return (User) super.entityManager.createQuery("Select u from User u where u.username = :username")
				.setParameter("username", username).getSingleResult();
	}
	
	// custom repository method
	public User findByUsernameAndPassword(String username, String password) {
		return (User) super.entityManager.createQuery("Select u from User u where u.username = :username and u.password = :password")
				.setParameter("username", username)
				.setParameter("password", password).getSingleResult();
	}
	
	// custom repository method
	public User findByMobile(String mobile) {
		return (User) super.entityManager.createQuery("Select u from User u where u.mobile = :mobile")
				.setParameter("mobile", mobile).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public boolean exists(String username) {
		 List<User> results = (List<User>)super.entityManager.createQuery("Select u from User u where u.username = :username")
				.setParameter("username", username).getResultList();
		if (results.isEmpty()) 
			return false;
	    else if (results.size() == 1) 
	    	return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean emailExists(String email) {
		 List<User> results = (List<User>)super.entityManager.createQuery("Select u from User u where u.email = :email")
				.setParameter("email", email).getResultList();
		if (results.isEmpty()) 
			return false;
	    else if (results.size() == 1) 
	    	return true;
		return false;
	}
	
}
