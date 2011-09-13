package com.dps.ring2park.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dps.ring2park.dao.UserDao;
import com.dps.ring2park.dao.VehicleDao;
import com.dps.ring2park.domain.User;
import com.dps.ring2park.domain.Vehicle;
import com.dps.ring2park.service.VehicleService;

/**
 * Vehicle Service
 * @author Kevin A. Lee 
 */
@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {

	private List<String> brands =
		Arrays.asList("Alfa Romeo", "BMW", "Citroen", "Dodge", "Fiat",
				"GMC", "Hyundai", "Jaguar", "Kia", "Land Rover",
				"Mazda", "Oldsmobile", "Porsche", "Renault",
				"Skoda", "Toyota", "Vauxhall", "Volkswagen");
	private List<String> colors =
		Arrays.asList("Blue", "Black", "Brown", "Gold", "Green",
				"Orange", "Pink", "Purple", "Red", "Silver",
				"White", "Yellow");

	@Autowired
	private VehicleDao vehicleDao;
	
	@Autowired
	private UserDao userDao;

	public void setVehicleDao(VehicleDao vehicleDao) 
	{
		this.vehicleDao = vehicleDao;
	}
	
	public void setUserDao(UserDao userDao) 
	{
		this.userDao = userDao;
	}

	public List<Vehicle> findVehicles(String username) {
		return vehicleDao.findByUsername(username);
	}

	public Vehicle findVehicleById(Long id) {
		return vehicleDao.find(id);
	}

	public void addVehicle(Vehicle vehicle, String username) {
		User user = userDao.find(username);
		vehicle.setUser(user);
		if (vehicle.isPreferred()) {
			vehicleDao.clearUsersPreferred(vehicle.getUser().getUsername());
		} 
		vehicleDao.persist(vehicle);
	}
	
	public void updateVehicle(Vehicle vehicle) {
		if (vehicle.isPreferred()) {
			vehicleDao.setUsersPreferred(vehicle.getUser().getUsername(), vehicle);
		} else {
			vehicleDao.merge(vehicle);
		}
	}
		
	public void deleteVehicle(Vehicle vehicle) {
		vehicleDao.remove(vehicle);
	}
	
	public void deleteVehicleById(Long id) {
		Vehicle vehicle = vehicleDao.find(id);
		vehicleDao.remove(vehicle);
	}
	
	public void setPreferredVehicle(String username, Vehicle vehicle) {
		vehicleDao.setUsersPreferred(username, vehicle);
	}
	
	public List<String> getBrands() {
		// TODO: get from database
		return brands;
	}
	
	public List<String> getColors() {
		// TODO: get from database
		return colors;
	}

}