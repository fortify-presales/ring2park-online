package com.dps.ring2park.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dps.ring2park.dao.BookingDao;
import com.dps.ring2park.domain.Booking;

/**
 * Booking DAO implementation.
 * @author Kevin A. Lee
 * 
 */
@Repository("BookingDao")
public class BookingDaoImpl extends GenericDAOImpl<Booking, Long> implements BookingDao {

	// custom repository method
	public List<Booking> findByUsername(String username) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> from = criteriaQuery.from(Booking.class);
		Path<Object> path = from.join("user").get("username");
		CriteriaQuery<Booking> select = criteriaQuery.select(from).orderBy(criteriaBuilder.desc(from.get("startDate")));   
		select.where(criteriaBuilder.equal(path, username));
		return (List<Booking>) super.entityManager.createQuery(select).getResultList();
	}

}
