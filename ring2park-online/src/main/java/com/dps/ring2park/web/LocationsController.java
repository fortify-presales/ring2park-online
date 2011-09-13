package com.dps.ring2park.web;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dps.ring2park.domain.Location;
import com.dps.ring2park.service.BookingService;
import com.dps.ring2park.web.form.SearchCriteria;

@Controller
@RequestMapping("/locations/")
public class LocationsController {

	private BookingService bookingService;

	@Inject
	public LocationsController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(SearchCriteria criteria, Principal currentUser, Model model) {
		// TODO: add favourite locations
		//if (currentUser != null) {
		//	List<Locations> locations = bookingService.findFavouriteLocations(currentUser.getName());
		//	model.addAttribute(locations);
		//
		List<Location> locations = bookingService.findLocations(criteria);
		model.addAttribute(locations);
		return "locations/search";
	}

	@RequestMapping(value = "results", method = RequestMethod.GET)
	public String results(SearchCriteria criteria, Model model) {
		List<Location> locations = bookingService.findLocations(criteria);
		model.addAttribute(locations);
		return "locations/searchResults";
	}

	@RequestMapping(value = "details/{id}", method = RequestMethod.GET)
	public String details(@PathVariable Long id, Model model) {
		model.addAttribute(bookingService.findLocationById(id));
		return "locations/details";
	}

}