package com.dps.ring2park.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dps.ring2park.domain.Booking;
import com.dps.ring2park.domain.PaymentCard;
import com.dps.ring2park.domain.Vehicle;
import com.dps.ring2park.service.BookingService;
import com.dps.ring2park.service.PaymentCardService;
import com.dps.ring2park.service.UserService;
import com.dps.ring2park.service.VehicleService;

@Controller
@RequestMapping("/statements/*")
public class StatementsController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private PaymentCardService paymentCardService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Principal currentUser, Model model) {
		List<Booking> bookings;
		boolean isAdmin = false;
		if (userService.hasRole("ROLE_ADMIN"))
			isAdmin = true;
		/*
		 * UserDetails userDetails = userService.getUserDetails(); Collection<?
		 * extends GrantedAuthority> authorities = userDetails.getAuthorities();
		 * for (GrantedAuthority grantedAuthority : authorities) { if
		 * (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) { isAdmin =
		 * true; } }
		 */
		if (isAdmin)
			bookings = bookingService.findBookings();
		else
			bookings = bookingService.findBookings(currentUser.getName());
		model.addAttribute("bookingList", bookings);
		return "statements/list";
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String details(@PathVariable Long id, Model model) {
		model.addAttribute(bookingService.findBookingById(id));
		return "statements/details";
	}

	// find a users bookings - via AJAX
	@RequestMapping(value = "{username}/view.json", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<Booking> view(@PathVariable String username) {
		List<Booking> bookings = bookingService.findBookings(username);
		return bookings;
	}

	// find specific booking - via AJAX
	@RequestMapping(value = "{id}/details.json", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	Booking details(@PathVariable Long id) {
		Booking booking = bookingService.findBookingById(id);
		return booking;
	}

	// add a booking - via AJAX
	@RequestMapping(value = "/{username}/{location}/{vehicle}/{card}/add.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	Booking add(@PathVariable String username, @PathVariable Long locationId, 
			@PathVariable Long vehicleId, @PathVariable Long cardId) {
			//@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		Vehicle vehicle = new Vehicle();
		PaymentCard card = new PaymentCard();
		Booking booking = new Booking();
		try {
			if (username != null && locationId != null) {
				try {
					booking = bookingService.createBooking(locationId, username);
					vehicle = vehicleService.findVehicleById(vehicleId);
					card = paymentCardService.findPaymentCardById(cardId);
					booking.setVehicle(vehicle);
					booking.setCard(card);
					//booking.setStartDate(new Date(startDate));
					//booking.setEndDate(new Date(endDate));
				} catch (NoResultException e) {
					//
				}
			}
		} catch (Exception e) {
			//
		}
		return booking;
	}

}