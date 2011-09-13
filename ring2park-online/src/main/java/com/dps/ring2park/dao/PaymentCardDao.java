package com.dps.ring2park.dao;


import java.util.List;

import com.dps.ring2park.domain.PaymentCard;

/**
 * PaymentCard DAO interface.
 * @author Kevin A. Lee
 * 
 */
public interface PaymentCardDao {

	/**
     * Find PaymentCard by id.
     */
    public PaymentCard find(Long id);
    
    /**
     * Persist PaymentCard.
     */
    public void persist(PaymentCard card);

    /**
     * Merge PaymentCard.
     */
    public void merge(PaymentCard card);
    
    /**
     * Remove PaymentCard.
     */
    public void remove(PaymentCard card);
    
    /**
     * Find all PaymentCards.
     */
    public List<PaymentCard> findAll();
    
    /**
     * Find a range of PaymentCard.
     */
    public List<PaymentCard> findInRange(int firstResult, int maxResults);
    
    /**
     * Count the number of PaymentCards.
     */
    public long count();    
       
    /**
     * Find PaymentCards by username.
     */
    public List<PaymentCard> findByUsername(String username);
    
    /**
     * Clear user's preferred PaymentCard
     */
    public void clearUsersPreferred(String username);
    
    /**
     * Set PaymentCard to be user's preferred
     */
    public void setUsersPreferred(String username, PaymentCard card);

}