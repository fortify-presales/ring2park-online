package com.dps.ring2park.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dps.ring2park.dao.UserDao;
import com.dps.ring2park.dao.PaymentCardDao;
import com.dps.ring2park.domain.User;
import com.dps.ring2park.domain.PaymentCard;
import com.dps.ring2park.service.PaymentCardService;

/**
 * PaymentCard Service
 * @author Kevin A. Lee 
 */
@Service("paymentCardService")
public class PaymentCardServiceImpl implements PaymentCardService {

	private List<String> cardTypes =
		Arrays.asList("Visa", "Mastercard", "Visa Debit",
				"American Express", "Diners Card");

	@Autowired
	private PaymentCardDao paymentCardDao;
	
	@Autowired
	private UserDao userDao;

	public void setPaymentCardDao(PaymentCardDao paymentCardDao) 
	{
		this.paymentCardDao = paymentCardDao;
	}
	
	public void setUserDao(UserDao userDao) 
	{
		this.userDao = userDao;
	}

	public List<PaymentCard> findPaymentCards(String username) {
		return paymentCardDao.findByUsername(username);
	}

	public PaymentCard findPaymentCardById(Long id) {
		return paymentCardDao.find(id);
	}

	public void addPaymentCard(PaymentCard paymentCard, String username) {
		User user = userDao.find(username);
		paymentCard.setUser(user);
		if (paymentCard.isPreferred()) {
			paymentCardDao.clearUsersPreferred(paymentCard.getUser().getUsername());
		} 
		paymentCardDao.persist(paymentCard);
	}
	
	public void updatePaymentCard(PaymentCard paymentCard) {
		if (paymentCard.isPreferred()) {
			paymentCardDao.setUsersPreferred(paymentCard.getUser().getUsername(), paymentCard);
		} else {
			paymentCardDao.merge(paymentCard);
		}
	}
		
	public void deletePaymentCard(PaymentCard paymentCard) {
		paymentCardDao.remove(paymentCard);
	}
	
	public void deletePaymentCardById(Long id) {
		PaymentCard paymentCard = paymentCardDao.find(id);
		paymentCardDao.remove(paymentCard);
	}
	
	public void setPreferredPaymentCard(String username, PaymentCard paymentCard) {
		paymentCardDao.setUsersPreferred(username, paymentCard);
	}
	
	public List<String> getTypes() {
		// TODO: get from database
		return cardTypes;
	}

}