package com.dps.ring2park.dao;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dps.ring2park.domain.Role;
import com.dps.ring2park.domain.User;

/**
 * Tests User DAO.
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class UserDaoTest {
    
    @Autowired
    private UserDao UserDao = null;

    private User user;
    
	@Before
	public void setup() {
		user = DataSeeder.generateUser();
		UserDao.persist(user);
	}

	@Test
	@Rollback
	public void testPersist() {
		assertEquals("Wilma Flintstone", UserDao.findByUsername(user.getUsername()).getName());
	}

	@Test
	@Rollback
	public void testUpdate() {
		assertEquals(2L, user.getRoles().size());
		user.setName("Wilma Boulder");
		Set<Role> roles = new HashSet<Role>();
		roles.add(DataSeeder.generateGuestRole());
		user.setRoles(roles);
		UserDao.merge(user);
		assertEquals(1L, user.getRoles().size());
		assertEquals("Wilma Boulder", UserDao.findByUsername(user.getUsername()).getName());
	}

	@Test
	@Rollback
	public void testDelete() {
		UserDao.remove(user);
		assertEquals(4l, UserDao.findAll().size());
	}

	@Test
	@Rollback
	public void testRead() {
		User result = UserDao.find(user.getUsername());
		assertEquals("wilma", result.getUsername());
		assertEquals("Wilma Flintstone", result.getName());
		assertEquals("password", result.getPassword());
		assertEquals("wilma@flintstones.com", result.getEmail());
		assertEquals("0777712345678", result.getMobile());
		assertEquals(new Boolean(false), result.getEnabled());
	}

	@Test
	@Rollback
	public void testExists() {
		assertEquals(true, UserDao.exists("wilma"));
		assertEquals(false, UserDao.exists("betty"));
	}
	
	@Test
	@Rollback
	public void testFindByUsername() {
		assertEquals("Wilma Flintstone", UserDao.findByUsername("wilma").getName());
	}
	
	@Test
	@Rollback
	public void testFindByUsernameAndPassword() {
		assertEquals("Wilma Flintstone", UserDao.findByUsernameAndPassword("wilma", "password").getName());
	}
	
	@Test
	@Rollback
	public void testFindByMobile() {
		assertEquals("Wilma Flintstone", UserDao.findByMobile("0777712345678").getName());
	}
	
	@After
	public void tearDown(){
	}
    
}
