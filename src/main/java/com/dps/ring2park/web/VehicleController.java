package com.dps.ring2park.web;

import java.security.Principal;
import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dps.ring2park.domain.Vehicle;
import com.dps.ring2park.service.VehicleService;
import com.dps.ring2park.web.extensions.FlashMap;

@Controller
@RequestMapping("/vehicles/*")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;

	// list all of the users vehicles - form
	@RequestMapping(method = RequestMethod.GET)
	public String listForm(Model model, Principal currentUser) {
		List<Vehicle> vehicles = null;
		if (currentUser != null) {
			vehicles = vehicleService.findVehicles(currentUser.getName());
		}
		model.addAttribute("vehicleList", vehicles);
		return "vehicles/list";
	}

	// view a specific vehicle - form
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String viewForm(@PathVariable Long id, Model model) {
		Vehicle vehicle;
		try {
			vehicle = vehicleService.findVehicleById(id);
		} catch (NoResultException e) {
			return "vehicles/invalidVehicle";
		}
		model.addAttribute(vehicle);
		return "vehicles/view";
	}

	// edit a specific vehicle - form
	@RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
	public String editForm(@PathVariable Long id, Model model) {
		Vehicle vehicle;
		List<String> brands = vehicleService.getBrands();
		model.addAttribute("brandList", brands);
		List<String> colors = vehicleService.getColors();
		model.addAttribute("colorList", colors);
		try {
			vehicle = vehicleService.findVehicleById(id);
		} catch (NoResultException e) {
			return "vehicles/invalidVehicle";
		}
		model.addAttribute(vehicle);
		return "vehicles/edit";
	}

	// delete a specific vehicle - form
	@RequestMapping(value = "{id}/delete", method = RequestMethod.GET)
	public String deleteForm(@PathVariable Long id, Model model) {
		Vehicle vehicle;
		try {
			vehicle = vehicleService.findVehicleById(id);
		} catch (NoResultException e) {
			return "vehicles/invalidVehicle";
		}
		model.addAttribute(vehicle);
		return "vehicles/delete";
	}

	// add a new vehicle - form
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addForm(Model model) {
		List<String> brands = vehicleService.getBrands();
		model.addAttribute("brandList", brands);
		List<String> colors = vehicleService.getColors();
		model.addAttribute("colorList", colors);
		model.addAttribute(new Vehicle());
		return "vehicles/add";
	}

	// REST style action URIs

	// update a vehicle
	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid Vehicle vehicle, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<String> brands = vehicleService.getBrands();
			model.addAttribute("brandList", brands);
			List<String> colors = vehicleService.getColors();
			model.addAttribute("colorList", colors);
			return "vehicles/edit";
		}
		vehicleService.updateVehicle(vehicle);
		String message = "Succesfully updated vehicle " + vehicle.getLicense() + ".";
		FlashMap.setSuccessMessage(message);
		return "redirect:/vehicles/" + vehicle.getId();
	}

	// add a vehicle
	@RequestMapping(method = RequestMethod.PUT)
	public String add(@Valid Vehicle vehicle, BindingResult bindingResult,
			Principal currentUser, Model model) {
		if (bindingResult.hasErrors()) {
			List<String> brands = vehicleService.getBrands();
			model.addAttribute("brandList", brands);
			List<String> colors = vehicleService.getColors();
			model.addAttribute("colorList", colors);
			return "vehicles/add";
		}
		if (currentUser != null) {
			vehicleService.addVehicle(vehicle, currentUser.getName());
			String message = "Succesfully added vehicle " + vehicle.getLicense() + ".";
			FlashMap.setSuccessMessage(message);
			return "redirect:/vehicles/" + vehicle.getId();
		} else {
			// TODO: return error
			return "redirect:/vehicles/add";
		}
	}

	// delete a vehicle
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id, Model model) {
		vehicleService.deleteVehicleById(id);
		String message = "Succesfully deleted vehicle.";
		FlashMap.setSuccessMessage(message);
		return "redirect:../vehicles/";
	}
	
	// find a users vehicles - via AJAX
	@RequestMapping(value = "{username}/view.json", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody List<Vehicle> view(@PathVariable String username) {
		List<Vehicle> vehicles = vehicleService.findVehicles(username);
		return vehicles;
	}
	
	// find specific vehicle - via AJAX
	@RequestMapping(value = "{id}/details.json", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody Vehicle details(@PathVariable Long id) {
		Vehicle vehicle = vehicleService.findVehicleById(id);
		return vehicle;
	}

}