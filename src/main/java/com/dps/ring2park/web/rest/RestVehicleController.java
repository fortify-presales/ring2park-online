package com.dps.ring2park.web.rest;

import com.dps.ring2park.domain.Vehicle;
import com.dps.ring2park.service.VehicleService;
import com.dps.ring2park.web.helpers.Message;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles/*")
public class RestVehicleController {

	@Autowired
	private VehicleService vehicleService;

	/*// view a specific vehicle - form
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
	public String update(@Valid Vehicle vehicle, BindingResult bindingResult, Model model,
                         final RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			List<String> brands = vehicleService.getBrands();
			model.addAttribute("brandList", brands);
			List<String> colors = vehicleService.getColors();
			model.addAttribute("colorList", colors);
			return "vehicles/edit";
		}
		vehicleService.updateVehicle(vehicle);
		String message = "Successfully updated vehicle " + vehicle.getLicense() + ".";
        redirectAttributes.addFlashAttribute(String.valueOf(Message.SUCCESS), message);

		return "redirect:/vehicles/" + vehicle.getId();
	}

	// add a vehicle
	@RequestMapping(method = RequestMethod.PUT)
	public String add(@Valid Vehicle vehicle, BindingResult bindingResult,
			Principal currentUser, Model model,
            final RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			List<String> brands = vehicleService.getBrands();
			model.addAttribute("brandList", brands);
			List<String> colors = vehicleService.getColors();
			model.addAttribute("colorList", colors);
			return "vehicles/add";
		}
		if (currentUser != null) {
			vehicleService.addVehicle(vehicle, currentUser.getName());
			String message = "Successfully added vehicle " + vehicle.getLicense() + ".";
            redirectAttributes.addFlashAttribute(String.valueOf(Message.SUCCESS), message);

			return "redirect:/vehicles/" + vehicle.getId();
		} else {
			// TODO: return error
			return "redirect:/vehicles/add";
		}
	}
	*/
	
	// find a users vehicles
	@RequestMapping(value = "/byuser/{username}", method = RequestMethod.GET)
	@JsonView(Vehicle.WithoutUserView.class)
	public List<Vehicle> userVehicles(@PathVariable String username) {
		List<Vehicle> vehicles = vehicleService.findVehicles(username);
		return vehicles;
	}
	
	// find specific vehicle
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@JsonView(Vehicle.WithoutUserView.class)
	public Vehicle vehicleById(@PathVariable Long id) {
		Vehicle vehicle = vehicleService.findVehicleById(id);
		return vehicle;
	}

	// delete a vehicle
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@JsonView(Vehicle.WithoutUserView.class)
	public void delete(@PathVariable Long id) {
		vehicleService.deleteVehicleById(id);
		return;
	}

}