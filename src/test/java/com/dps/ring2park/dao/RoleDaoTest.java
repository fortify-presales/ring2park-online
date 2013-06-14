package com.dps.ring2park.dao;

import static org.junit.Assert.assertEquals;

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

/**
 * Tests Role DAO.
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class RoleDaoTest {
    
    @Autowired
    private RoleDao roleDao = null;
    
    private static String AUTHORITY = "ROLE_CUSTOMER";
    
	@Before
	public void setup() {
	}

	@Test
	@Rollback
	public void testPersist() {
		Role role = DataSeeder.generateCustomerRole();
		roleDao.persist(role);
		assertEquals(AUTHORITY, roleDao.find(AUTHORITY).getAuthority());
	}

	@Test
	@Rollback
	public void testUpdate() {
		Role role = DataSeeder.generateCustomerRole();
		roleDao.persist(role);
		role.setAuthority("ROLE_POWERUSER");
		roleDao.merge(role);
		//assertEquals("ROLE_POWERUSER", roleDao.find("ROLE_POWERUSER").getAuthority());
	}

	@Test
	@Rollback
	public void testDelete() {
		Role role = DataSeeder.generateCustomerRole();
		roleDao.persist(role);
		assertEquals(4l, roleDao.findAll().size());
		roleDao.remove(role);
		assertEquals(3l, roleDao.findAll().size());
	}

	@Test
	@Rollback
	public void testRead() {
		Role role = DataSeeder.generateCustomerRole();
		roleDao.persist(role);
		Role result = roleDao.find(role.getAuthority());
		assertEquals(AUTHORITY, result.getAuthority());
	}
	
	@After
	public void tearDown(){
	}
    
}
