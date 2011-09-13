package com.dps.ring2park.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.dps.ring2park.domain.PaymentCard;

/**
 * A service interface for updating and retrieving Payment Cards from a backing repository. 
 * @author Kevin A. Lee 
 */
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public interface PaymentCardService {

    /**
     * Find PaymentCards owned by the given user
     * @param username the user's name
     * @return their PaymentCards
     */
    public List<PaymentCard> findPaymentCards(String username);

    /**
     * Find PaymentCards by their identifier.
     * @param id the PaymentCard id
     * @return the PaymentCard
     */
    public PaymentCard findPaymentCardById(Long id);

    /**
     * Add a new PaymentCard instance.
     * @param PaymentCard the vehicle
     * @param userName the user name
     */
    @Transactional
    public void addPaymentCard(PaymentCard card, String username);
  
    /**
     * Updates an existing PaymentCard.
     * @param PaymentCard the PaymentCard
     */
    @Transactional
    public void updatePaymentCard(PaymentCard card);
       
    /**
     * Deletes an existing PaymentCard.
     * @param PaymentCard the PaymentCard
     */
    @Transactional
    public void deletePaymentCard(PaymentCard card);
    
    /**
     * Deletes an existing PaymentCard.
     * @param id the PaymentCard id
     */
    @Transactional
    public void deletePaymentCardById(Long id);   
    
    /**
     * Set PaymentCard to be user's preferred
     */
    @Transactional
    public void setPreferredPaymentCard(String username, PaymentCard card);
    
    /**
     * Get all selectable PaymentCard types.
     * @return list of PaymentCard types
     */
    public List<String> getTypes();

}

