package com.dps.ring2park.dao;


import java.util.List;

import com.dps.ring2park.domain.Vehicle;

/**
 * Vehicle DAO interface.
 * @author Kevin A. Lee
 * 
 */
public interface VehicleDao {

	/**
     * Find Vehicle by id.
     */
    public Vehicle find(Long id);
    
    /**
     * Persist Vehicle.
     */
    public void persist(Vehicle Vehicle);

    /**
     * Merge Vehicle.
     */
    public void merge(Vehicle Vehicle);
    
    /**
     * Remove Vehicle.
     */
    public void remove(Vehicle vehicle);
    
    /**
     * Find all Vehicles.
     */
    public List<Vehicle> findAll();
    
    /**
     * Find a range of Vehicles.
     */
    public List<Vehicle> findInRange(int firstResult, int maxResults);
    
    /**
     * Count the number of Vehicles.
     */
    public long count();    
       
    /**
     * Find Vehicles by username.
     */
    public List<Vehicle> findByUsername(String username);
    
    /**
     * Clear user's preferred vehicle
     */
    public void clearUsersPreferred(String username);
    
    /**
     * Set Vehicle to be user's preferred
     */
    public void setUsersPreferred(String username, Vehicle vehicle);

}