package com.dps.ring2park.dao;


import java.util.List;

import com.dps.ring2park.domain.News;

/**
 * News DAO interface.
 * @author Kevin A. Lee
 * 
 */
public interface NewsDao {

	/**
     * Find News article by id.
     */
    public News find(Long id);
    
    /**
     * Persist News article.
     */
    public void persist(News News);

    /**
     * Merge News article.
     */
    public void merge(News News);
    
    /**
     * Remove News article.
     */
    public void remove(News News);
    
    /**
     * Find all News articles.
     */
    public List<News> findAll();
    
    /**
     * Find a range of News articles.
     */
    public List<News> findInRange(int firstResult, int maxResults);
    
    /**
     * Count the number of News articles.
     */
    public long count();    
    
    /**
     * Find all active News articles.
     */
    public List<News> findActive();
    
    /**
     * Find limited number of active News articles.
     */
    public List<News> findActiveMax(int maxResults);

}