package com.dps.ring2park.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dps.ring2park.dao.DataSeeder;
import com.dps.ring2park.dao.UserDao;
import com.dps.ring2park.domain.PaymentCard;

/**
 * Tests PaymentCard Service.
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class PaymentCardServiceTest {
    
    @Autowired
    private PaymentCardService paymentCardService = null;
    
    @Autowired
    private UserDao userDao = null;
    
    private PaymentCard paymentCard;

	@Before
	public void setup(){
		paymentCard = DataSeeder.generatePaymentCard();
		userDao.persist(paymentCard.getUser());
	}

	@Test
	@Rollback
	public void testAddPaymentCard() {
		paymentCardService.addPaymentCard(paymentCard, paymentCard.getUser().getUsername());
		assertEquals("1234567812345678", paymentCardService.findPaymentCardById(paymentCard.getId()).getNumber());
		PaymentCard p2 = new PaymentCard();
		p2.setNumber("1234567887654321");
		p2.setType(PaymentCard.CardType.VISA);
		p2.setExpiryMonth(12);
		p2.setExpiryYear(2015);
		p2.setUser(paymentCard.getUser());
		paymentCardService.addPaymentCard(p2, p2.getUser().getUsername());
		assertEquals("1234567887654321", paymentCardService.findPaymentCardById(p2.getId()).getNumber());
	}
	
	@Test
	@Rollback
	public void testUpdatePaymentCard() {
		paymentCardService.addPaymentCard(paymentCard, paymentCard.getUser().getUsername());
		PaymentCard p2 = paymentCardService.findPaymentCardById(paymentCard.getId());
		p2.setNumber("1111111199999999");
		p2.setExpiryMonth(12);
		p2.setExpiryYear(2015);
		p2.setPreferred(true);
		paymentCardService.updatePaymentCard(p2);
		assertEquals("1111111199999999", paymentCardService.findPaymentCardById(p2.getId()).getNumber());
		assertEquals(12, paymentCardService.findPaymentCardById(p2.getId()).getExpiryMonth());
		assertEquals(2015, paymentCardService.findPaymentCardById(p2.getId()).getExpiryYear());
		assertEquals(true, paymentCardService.findPaymentCardById(p2.getId()).isPreferred());
	}
	
	@Test
	@Rollback
	public void testDeletePaymentCard() {
		paymentCardService.addPaymentCard(paymentCard, paymentCard.getUser().getUsername());
		PaymentCard p2 = paymentCardService.findPaymentCardById(paymentCard.getId());
		paymentCardService.deletePaymentCard(p2);
		assertEquals(null, paymentCardService.findPaymentCardById(paymentCard.getId()));
	}
	
	@Test
	@Rollback
	public void testDeletePaymentCardById() {
		paymentCardService.addPaymentCard(paymentCard, paymentCard.getUser().getUsername());
		paymentCardService.deletePaymentCardById(paymentCard.getId());
		assertEquals(null, paymentCardService.findPaymentCardById(paymentCard.getId()));
	}
	
	@Test
	@Rollback
	public void testGetCardTypes() {
		List <String> types = paymentCardService.getTypes();
		assertEquals(5L, types.size());
	}

	
	@After
	public void tearDown(){
	}
    
}
