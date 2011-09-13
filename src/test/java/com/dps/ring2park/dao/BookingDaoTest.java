package com.dps.ring2park.dao;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;

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

import com.dps.ring2park.domain.Booking;

/**
 * Tests Booking DAO.
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class BookingDaoTest {
    
    @Autowired
    private BookingDao BookingDao = null;
    
    @Autowired
    private LocationDao LocationDao = null;
    
    @Autowired
    private VehicleDao VehicleDao = null;
    
    @Autowired
    private UserDao UserDao = null;
    
    private Booking booking;

	@Before
	public void setup() {
		booking = DataSeeder.generateBooking();
		LocationDao.persist(booking.getLocation());
		UserDao.persist(booking.getUser());
		VehicleDao.persist(booking.getVehicle());
	}

	@Test
	@Rollback
	public void testPersist() {
		BookingDao.persist(booking);
		assertEquals(1, BookingDao.find(booking.getId()).getDays());
	}

	@Test
	@Rollback
	public void testUpdate() {
		BookingDao.persist(booking);
		Calendar calendar = Calendar.getInstance();
		booking.setStartDate(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, 5);
		booking.setEndDate(calendar.getTime());
		BookingDao.merge(booking);
		assertEquals(5, BookingDao.find(booking.getId()).getDays());
	}

	@Test
	@Rollback
	public void testDelete() {
		assertEquals(0l, BookingDao.findAll().size());
		BookingDao.persist(booking);
		assertEquals(1l, BookingDao.findAll().size());
		BookingDao.remove(booking);
		assertEquals(0l, BookingDao.findAll().size());
	}

	@Test
	@Rollback
	public void testRead() {
		BookingDao.persist(booking);
		Booking result = BookingDao.findByUsername("wilma").get(0);
		assertEquals(1, result.getDays());
		assertEquals(new BigDecimal("12.50"), result.getTotal());
		assertEquals("Bedrock Shopping Mall", result.getLocation().getName());
		assertEquals("XYZ 999", result.getVehicle().getLicense());
		assertEquals("Wilma Flintstone", result.getUser().getName());
	}
	
	@After
	public void tearDown() {
	}
    
}
