package com.dps.ring2park.dao;

import java.util.List;

import com.dps.ring2park.domain.User;

/**
 * User DAO interface.
 * @author Kevin A. Lee
 * 
 */
public interface UserDao {

    /**
     * Find User by id.
     */
    public User find(String id);
    
    /**
     * Persist User.
     */
    public void persist(User user);

    /**
     * Merge User.
     */
    public void merge(User user);
    
    /**
     * Remove User.
     */
    public void remove(User user);
    
    /**
     * Find all Users.
     */
    public List<User> findAll();
    
    /**
     * Find a range of Users.
     */
    public List<User> findInRange(int firstResult, int maxResults);
    
    /**
     * Count the number of Users.
     */
    public long count();
    
    /**
     * Find User by username.
     */
    public User findByUsername(String username);
    
    /**
     * Find User by username and password.
     */
    public User findByUsernameAndPassword(String username, String password);
    
    /**
     * Find User by mobile.
     */
    public User findByMobile(String mobile);
    
    /**
     * Check if user already exists.
     */
    public boolean exists(String username);
    
    /**
     * Check if email already exists.
     */
    public boolean emailExists(String email);
    
}