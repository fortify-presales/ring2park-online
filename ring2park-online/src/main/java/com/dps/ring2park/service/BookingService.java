package com.dps.ring2park.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.dps.ring2park.domain.Booking;
import com.dps.ring2park.domain.Location;
import com.dps.ring2park.web.form.SearchCriteria;

/**
 * A service interface for retrieving locations and bookings from a backing repository. Also supports the ability to cancel
 * a booking.
 * @author Kevin A. Lee
 */
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public interface BookingService {

    /**
     * Find all bookings made by all users
     * @return all bookings
     */
    public List<Booking> findBookings();
    
    /**
     * Find bookings made by the given user
     * @param username the user's name
     * @return their bookings
     */
    public List<Booking> findBookings(String username);
    
    /**
     * Find a specific booking 
     * @param id the booking id
     * @return the booking
     */
    public Booking findBookingById(Long id);

    /**
     * Find locations available for booking by some criteria.
     * @param criteria the search criteria
     * @return a list of locations meeting the criteria
     */
    public List<Location> findLocations(SearchCriteria criteria);

    /**
     * Find locations by their identifier.
     * @param id the location id
     * @return the location
     */
    public Location findLocationById(Long id);

    /**
     * Create a new, transient parking booking instance for the given user.
     * @param locationId the locationId
     * @param userName the user name
     * @return the new transient booking instance
     */
    @Transactional
    public Booking createBooking(Long locationId, String userName);
    
    /**
     * Set the vehicle for a booking.
     * @param booking the booking
     * @param vehicleId the vehicleId
     */
    @Transactional
    public void setVehicle(Booking booking, Long vehicleId);
    
    /**
     * Set the Payment Card for a booking.
     * @param booking the booking
     * @param carId the cardId
     */
    @Transactional
    public void setPaymentCard(Booking booking, Long cardId);

    /**
     * Extend an existing booking.
     * @param id the booking id
     */
    @Transactional
    public void extendBooking(Long id);

}

