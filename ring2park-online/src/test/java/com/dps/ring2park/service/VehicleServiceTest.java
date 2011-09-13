package com.dps.ring2park.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

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

import com.dps.ring2park.dao.DataSeeder;
import com.dps.ring2park.dao.UserDao;
import com.dps.ring2park.domain.Vehicle;

/**
 * Tests Vehicle Service.
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class VehicleServiceTest {
    
    @Autowired
    private VehicleService vehicleService = null;
    
    @Autowired
    private UserDao userDao = null;
    
    private Vehicle vehicle;

	@Before
	public void setup(){
		vehicle = DataSeeder.generateVehicle();
		userDao.persist(vehicle.getUser());
	}

	@Test
	@Rollback
	public void testAddVehicle() {
		vehicleService.addVehicle(vehicle, vehicle.getUser().getUsername());
		assertEquals("Ford", vehicleService.findVehicleById(vehicle.getId()).getBrand());
	}
	
	@Test
	@Rollback
	public void testUpdateVehicle() {
		vehicleService.addVehicle(vehicle, vehicle.getUser().getUsername());
		Vehicle v2 = vehicleService.findVehicleById(vehicle.getId());
		v2.setBrand("Honda");
		v2.setColor("White");
		v2.setLicense("UPD 070");
		v2.setPreferred(true);
		vehicleService.updateVehicle(v2);
		assertEquals("Honda", vehicleService.findVehicleById(v2.getId()).getBrand());
		assertEquals("White", vehicleService.findVehicleById(v2.getId()).getColor());
		assertEquals("UPD 070", vehicleService.findVehicleById(v2.getId()).getLicense());
		assertEquals(true, vehicleService.findVehicleById(v2.getId()).isPreferred());
	}
	
	@Test
	@Rollback
	public void testDeleteVehicle() {
		vehicleService.addVehicle(vehicle, vehicle.getUser().getUsername());
		Vehicle v2 = vehicleService.findVehicleById(vehicle.getId());
		vehicleService.deleteVehicle(v2);
		assertEquals(null, vehicleService.findVehicleById(vehicle.getId()));
	}
	
	@Test
	@Rollback
	public void testDeleteVehicleById() {
		vehicleService.addVehicle(vehicle, vehicle.getUser().getUsername());
		vehicleService.deleteVehicleById(vehicle.getId());
		assertEquals(null, vehicleService.findVehicleById(vehicle.getId()));
	}
	
	@Test
	@Rollback
	public void testGetBrands() {
		List <String> brands = vehicleService.getBrands();
		assertEquals(18L, brands.size());
	}

	
	@After
	public void tearDown(){
	}
    
}
