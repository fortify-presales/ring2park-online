package com.dps.ring2park.web;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dps.ring2park.domain.Location;
import com.dps.ring2park.service.BookingService;
import com.dps.ring2park.web.form.SearchCriteria;

@Controller
@RequestMapping("/locations/")
public class LocationController {

	private BookingService bookingService;

	@Inject
	public LocationController(BookingService bookingService) {
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

	// find locations - via AJAX
	@RequestMapping(value = "find.json", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody List<Location> all() {
		List<Location> locations = bookingService.findAllLocations();
		return locations;
	}
	
	// find locations - via AJAX
	@RequestMapping(value = "search.json", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody List<Location> search(@RequestParam("criteria") SearchCriteria criteria) {
		List<Location> locations = bookingService.findLocations(criteria);
		return locations;
	}
	
	// find specific location - via AJAX
	@RequestMapping(value = "{id}/details.json", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody Location details(@PathVariable Long id) {
		Location location = bookingService.findLocationById(id);
		return location;
	}

}