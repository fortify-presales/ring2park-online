package com.dps.ring2park.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dps.ring2park.dao.VehicleDao;
import com.dps.ring2park.domain.Vehicle;

/**
 * Vehicle DAO implementation.
 * @author Kevin A. Lee
 * 
 */
@Repository("VehicleDao")
public class VehicleDaoImpl extends GenericDAOImpl<Vehicle, Long> implements VehicleDao {

	// custom repository method
	@SuppressWarnings("unchecked")
	public List<Vehicle> findByUsername(String username) {
		return (List<Vehicle>) super.entityManager
				.createQuery(
						"select v from Vehicle v where v.user.username = :username order by v.preferred desc")
				.setParameter("username", username).getResultList();
	}
	
	// custom repository method
	public void clearUsersPreferred(String username) {
		// clear any preferred flags
		super.entityManager
				.createQuery(
						"update Vehicle v set v.preferred = 0 where v.user.username = :username and v.preferred = 1")
				.setParameter("username", username).executeUpdate();
	}
		
	// custom repository method
	public void setUsersPreferred(String username, Vehicle vehicle) {
		// clear any preferred flags
		super.entityManager
				.createQuery(
						"update Vehicle v set v.preferred = 0 where v.user.username = :username and v.preferred = 1")
				.setParameter("username", username).executeUpdate();
		// set this vehicle to be preferred
		vehicle.setPreferred(true);
		this.merge(vehicle);
	}

}
