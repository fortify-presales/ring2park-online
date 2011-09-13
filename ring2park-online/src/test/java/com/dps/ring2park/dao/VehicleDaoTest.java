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

import com.dps.ring2park.domain.Vehicle;

/**
 * Tests Vehicle DAO.
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class VehicleDaoTest {
    
    @Autowired
    private VehicleDao VehicleDao = null;
    
    @Autowired
    private UserDao UserDao = null;
    
    private Vehicle vehicle;

	@Before
	public void setup() {
		vehicle = DataSeeder.generateVehicle();
		UserDao.persist(vehicle.getUser());
	}

	@Test
	@Rollback
	public void testPersist() {
		VehicleDao.persist(vehicle);
		assertEquals("Ford", VehicleDao.find(vehicle.getId()).getBrand());
	}

	@Test
	@Rollback
	public void testUpdate() {
		VehicleDao.persist(vehicle);
		vehicle.setBrand("Honda");
		VehicleDao.merge(vehicle);
		assertEquals("Honda", VehicleDao.find(vehicle.getId()).getBrand());
	}

	@Test
	@Rollback
	public void testDelete() {
		VehicleDao.persist(vehicle);
		VehicleDao.remove(vehicle);
		assertEquals(3l, VehicleDao.findAll().size());
	}

	@Test
	@Rollback
	public void testRead() {
		VehicleDao.persist(vehicle);
		Vehicle result = VehicleDao.findByUsername("wilma").get(0);
		assertEquals("Ford", result.getBrand());
		assertEquals("Blue", result.getColor());
		assertEquals("XYZ 999", result.getLicense());
		assertEquals("Wilma Flintstone", result.getUser().getName());
	}

	@Test
	@Rollback
	public void testFindByUsername() {
		VehicleDao.persist(vehicle);
		assertEquals("Ford", VehicleDao.findByUsername("wilma").get(0).getBrand());
	}
	
	@Test
	@Rollback
	public void testSetUsersPreferred() {
		VehicleDao.persist(vehicle);
		assertEquals(false, VehicleDao.find(vehicle.getId()).isPreferred());
		VehicleDao.setUsersPreferred(vehicle.getUser().getUsername(), vehicle);
		assertEquals(true, VehicleDao.find(vehicle.getId()).isPreferred());
	}
	
	@After
	public void tearDown() {
	}
    
}
