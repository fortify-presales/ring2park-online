package com.dps.ring2park.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dps.ring2park.dao.PaymentCardDao;
import com.dps.ring2park.domain.PaymentCard;

/**
 * PaymentCard DAO implementation.
 * @author Kevin A. Lee
 * 
 */
@Repository("PaymentCardDao")
public class PaymentCardDaoImpl extends GenericDAOImpl<PaymentCard, Long> implements PaymentCardDao {

	// custom repository method
	@SuppressWarnings("unchecked")
	public List<PaymentCard> findByUsername(String username) {
		return (List<PaymentCard>) super.entityManager
				.createQuery(
						"select c from PaymentCard c where c.user.username = :username order by c.preferred desc")
				.setParameter("username", username).getResultList();
	}
	
	// custom repository method
	public void clearUsersPreferred(String username) {
		// clear any preferred flags
		super.entityManager
				.createQuery(
						"update PaymentCard c set c.preferred = 0 where c.user.username = :username and c.preferred = 1")
				.setParameter("username", username).executeUpdate();
	}
		
	// custom repository method
	public void setUsersPreferred(String username, PaymentCard card) {
		// clear any preferred flags
		super.entityManager
				.createQuery(
						"update PaymentCard c set c.preferred = 0 where c.user.username = :username and c.preferred = 1")
				.setParameter("username", username).executeUpdate();
		// set this PaymentCard to be preferred
		card.setPreferred(true);
		this.merge(card);
	}

}
