package com.dps.ring2park.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dps.ring2park.dao.BookingDao;
import com.dps.ring2park.dao.LocationDao;
import com.dps.ring2park.dao.PaymentCardDao;
import com.dps.ring2park.dao.UserDao;
import com.dps.ring2park.dao.VehicleDao;
import com.dps.ring2park.domain.Booking;
import com.dps.ring2park.domain.Location;
import com.dps.ring2park.domain.PaymentCard;
import com.dps.ring2park.domain.User;
import com.dps.ring2park.domain.Vehicle;
import com.dps.ring2park.service.BookingService;
import com.dps.ring2park.web.form.SearchCriteria;

/**
 * Booking Service
 * @author Kevin A. Lee
 */
@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private VehicleDao vehicleDao;
	
	@Autowired
	private PaymentCardDao cardDao;
	
	public void setBookingDao(BookingDao bookingDao) {
		this.bookingDao = bookingDao;
	}
	
	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setVehicleDao(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	public void setPaymentCardDao(PaymentCardDao cardDao) {
		this.cardDao = cardDao;
	}

	public List<Booking> findBookings() {
		return bookingDao.findAll();
	}
	
	public List<Booking> findBookings(String username) {
		return bookingDao.findByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public Booking findBookingById(Long id) {
		return bookingDao.find(id);
	}

	@Transactional(readOnly = true)
	public List<Location> findAllLocations() {
		return locationDao.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Location> findLocations(SearchCriteria criteria) {
		return locationDao.findLocations(criteria);
	}

	@Transactional(readOnly = true)
	public Location findLocationById(Long id) {
		return locationDao.find(id);
	}

	@Transactional(readOnly = true)
	public Booking createBooking(Long locationId, String username) {
		Location location = locationDao.find(locationId);
		User user = userDao.findByUsername(username);
		Booking booking = new Booking(location, user);
		Vehicle vehicle = vehicleDao.findByUsername(username).get(0);
		booking.setVehicle(vehicle);
		bookingDao.persist(booking);
		return booking;
	}

	@Transactional(readOnly = true)
    public void setVehicle(Booking booking, Long vehicleId)
    {
		if (booking != null) {
			Vehicle vehicle =  vehicleDao.find(vehicleId);
	    	if (vehicle != null) {
	    		booking.setVehicle(vehicle);
	    	}
		}
    }
	
	@Transactional(readOnly = true)
    public void setPaymentCard(Booking booking, Long cardId)
    {
		if (booking != null) {
			PaymentCard card =  cardDao.find(cardId);
	    	if (card != null) {
	    		booking.setCard(card);
	    	}
		}
    }
	
	@Transactional
	public void extendBooking(Long id) {
		//Booking booking = bookingDao.find(id);
		// TODO: extend booking
	}

}