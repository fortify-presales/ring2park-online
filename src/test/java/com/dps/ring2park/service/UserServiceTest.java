package com.dps.ring2park.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dps.ring2park.dao.DataSeeder;
import com.dps.ring2park.domain.User;

/**
 * Tests User Service.
 * 
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class UserServiceTest {

	@Autowired
	private UserService userService = null;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static String USERNAME = "wilma";
	private static String PASSWORD = "password";

	private User user;
	
	@Before
	public void setup() {
		user = DataSeeder.generateUser();
		userService.addUser(user);
	}

	@Test
	@Rollback
	public void testAddUser() {
		String encodedPassword = passwordEncoder.encodePassword(PASSWORD, null);
		assertEquals(USERNAME, userService.findUserByUsername(USERNAME).getUsername());
		assertEquals(encodedPassword, userService.findUserByUsername(USERNAME).getPassword());
	}

	@Test
	@Rollback
	public void testUpdateUser() {
		User u2 = userService.findUserByUsername(USERNAME);
		u2.setPassword("changed");
		u2.setName("Betty Rubble");
		u2.setEmail("betty@flintstones.com");
		//String encodedPassword = passwordEncoder.encodePassword(u2.getPassword(), null);
		User u3 = userService.updateUser(u2);
		//assertEquals(encodedPassword, u3.getPassword());
		assertEquals("Betty Rubble", u3.getName());
		assertEquals("betty@flintstones.com", u3.getEmail());
	}

	@Test
	@Rollback
	public void testDeleteUser() {
		User u2 = userService.findUserByUsername(USERNAME);
		userService.deleteUser(u2);
		try {
			userService.findUserByUsername(USERNAME);
			fail("testDeleteUser() should throw EmptyResultDataAccessException");
		} catch (EmptyResultDataAccessException e) {
			assertEquals(e.getMessage(), "No entity found for query");
		}
	}

	@Test
	@Rollback
	public void testDeleteUserByUsername() {
		userService.deleteUserByUsername(USERNAME);
		try {
			userService.findUserByUsername(USERNAME);
			fail("testDeleteUserByUsername() should throw EmptyResultDataAccessException");
		} catch (EmptyResultDataAccessException e) {
			assertEquals(e.getMessage(), "No entity found for query");
		}
	}

	@After
	public void tearDown() {
		
	}

}
