package com.dps.ring2park.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dps.ring2park.dao.NewsDao;
import com.dps.ring2park.domain.News;

/**
 * News DAO implementation.
 * @author Kevin A. Lee
 * 
 */
@Repository("NewsDao")
public class NewsDaoImpl extends GenericDAOImpl<News, Long> implements NewsDao {

	// custom repository method
	@SuppressWarnings("unchecked")
	public List<News> findActive() {
		return (List<News>) super.entityManager.createQuery(
				"select n from News n where n.active = 1 order by n.startDate desc")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<News> findActiveMax(int maxResults) {
		return (List<News>) super.entityManager.createQuery(
				"select n from News n where n.active = 1 order by n.startDate desc")
				.setMaxResults(maxResults)
				.getResultList();
	}

	// helpers


}
