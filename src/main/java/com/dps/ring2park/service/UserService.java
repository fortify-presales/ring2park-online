package com.dps.ring2park.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.dps.ring2park.domain.User;
import com.dps.ring2park.security.LoginStatus;

/**
 * A service interface for updating and retrieving users from a backing repository. 
 * @author Kevin A. Lee
 */
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public interface UserService {

    /**
     * Find all users
     * @return the users
     */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findUsers();

    /**
     * Find user by their username.
     * @param username the user's username
     * @return the user
     */
    public User findUserByUsername(String username);
    
    /**
     * Find user by their username and password.
     * @param username the user's username
     * @param password the user's password
     * @return the user
     */
    public User findUserByUsernameAndPassword(String username, String password);
    
    /**
     * Find user by their mobile.
     * @param mobile the user's mobile number
     * @return the user
     */
    public User findUserByMobile(String mobile);
    
    /**
     * Check if user already exists
     * @param username the user's username
     * @return true if user exists else false
     */
    public boolean userExists(String username);
    
    /**
     * Check if email address is already taken
     * @param email the email address
     * @return true if email address taken else false
     */
    public boolean emailTaken(String email);
    
    /**
     * Adds a new user.
     * @return the user
     */
    @Transactional
    public User addUser(User user);
    
    /**
     * Updates an existing user.
     * @return the user
     */
    @Transactional
    public User updateUser(User user);
    
    /**
     * Deletes an existing user.
     * @param user the User
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(User user);
    
    /**
     * Deletes an existing user.
     * @param username the user's username
     */
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUserByUsername(String username);    
    
    /**
     * Creates a new empty user for registration.
     * @return the user
     */
    @Transactional
    public User createNewUser();
    
    /**
     * Notifies a new user of their registration.
     * @param user the user
     * @param contextPath root context for links
     * @return true if email is sent else false
     */
    public boolean notifyNewUser(User user, String contextPath);
  
    /**
     * Verifies a new user's registration.
     * @param username the user's username
     * @param verifyCode the user's verification code
     * @return true if user is verified else false
     */
    @Transactional
    public boolean verifyNewUser(String username, String verifyCode);
    
    /**
     * Authenticates a user
     * @param user the User
     */
    public void authenticateUser(User user);
    
    /**
     * Logs a user in
     * @param username
     * @param password
     * @return
     */
    public LoginStatus login(String username, String password);

}

