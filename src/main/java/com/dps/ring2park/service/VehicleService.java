package com.dps.ring2park.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.dps.ring2park.domain.Vehicle;

/**
 * A service interface for updating and retrieving vehicles from a backing repository. 
 * @author Kevin A. Lee 
 */
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public interface VehicleService {

    /**
     * Find vehicles owned by the given user
     * @param username the user's name
     * @return their vehicles
     */
    public List<Vehicle> findVehicles(String username);

    /**
     * Find vehicles by their identifier.
     * @param id the vehicle id
     * @return the vehicle
     */
    public Vehicle findVehicleById(Long id);

    /**
     * Add a new vehicle instance.
     * @param vehicle the vehicle
     * @param userName the user name
     */
    @Transactional
    public void addVehicle(Vehicle vehicle, String username);
  
    /**
     * Updates an existing vehicle.
     * @param vehicle the vehicle
     */
    @Transactional
    public void updateVehicle(Vehicle vehicle);
       
    /**
     * Deletes an existing vehicle.
     * @param vehicle the Vehicle
     */
    @Transactional
    public void deleteVehicle(Vehicle vehicle);
    
    /**
     * Deletes an existing vehicle.
     * @param id the vehicle id
     */
    @Transactional
    public void deleteVehicleById(Long id);   
    
    /**
     * Set Vehicle to be user's preferred
     */
    @Transactional
    public void setPreferredVehicle(String username, Vehicle vehicle);
    
    /**
     * Get all selectable vehicle brands.
     * @return list of vehicle colors
     */
    public List<String> getBrands();
    
    /**
     * Get all selectable vehicle colors.
     * @return list of vehicle colors
     */
    public List<String> getColors();

}

