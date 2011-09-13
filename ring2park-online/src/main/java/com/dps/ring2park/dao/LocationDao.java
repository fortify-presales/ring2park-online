package com.dps.ring2park.dao;


import java.util.List;

import com.dps.ring2park.domain.Location;
import com.dps.ring2park.web.form.SearchCriteria;

/**
 * Location DAO interface.
 * @author Kevin A. Lee
 * 
 */
public interface LocationDao {

	/**
     * Find Location by id.
     */
    public Location find(Long id);
    
    /**
     * Persist Location.
     */
    public void persist(Location Location);

    /**
     * Merge Location.
     */
    public void merge(Location Location);
    
    /**
     * Remove Location.
     */
    public void remove(Location Location);
    
    /**
     * Find all Locations.
     */
    public List<Location> findAll();
    
    /**
     * Find a range of Locations.
     */
    public List<Location> findInRange(int firstResult, int maxResults);
    
    /**
     * Count the number of Locations.
     */
    public long count();    
       
    /**
     * Find Locations by search criteria.
     */
     public List<Location> findLocations(SearchCriteria criteria);

}