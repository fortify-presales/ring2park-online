package com.dps.ring2park.dao;

import static org.junit.Assert.assertEquals;

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

import com.dps.ring2park.domain.PaymentCard;

/**
 * Tests PaymentCard DAO.
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class PaymentCardDaoTest {
    
    @Autowired
    private PaymentCardDao PaymentCardDao = null;
    
    @Autowired
    private UserDao UserDao = null;
    
    private PaymentCard card;

	@Before
	public void setup() {
		card = DataSeeder.generatePaymentCard();
		UserDao.persist(card.getUser());
	}

	@Test
	@Rollback
	public void testPersist() {
		PaymentCardDao.persist(card);
		assertEquals("1234567812345678", PaymentCardDao.find(card.getId()).getNumber());
	}

	@Test
	@Rollback
	public void testUpdate() {
		PaymentCardDao.persist(card);
		card.setNumber("1111111199999999");
		PaymentCardDao.merge(card);
		assertEquals("1111111199999999", PaymentCardDao.find(card.getId()).getNumber());
	}

	@Test
	@Rollback
	public void testDelete() {
		PaymentCardDao.persist(card);
		PaymentCardDao.remove(card);
		assertEquals(2l, PaymentCardDao.findAll().size());
	}

	@Test
	@Rollback
	public void testRead() {
		PaymentCardDao.persist(card);
		PaymentCard result = PaymentCardDao.findByUsername("wilma").get(0);
		assertEquals("1234567812345678", result.getNumber());
		assertEquals("1234", result.getSecurityCode());
		assertEquals(1, result.getExpiryMonth());
		assertEquals(2012, result.getExpiryYear());
		assertEquals("Wilma Flintstone", result.getUser().getName());
	}

	@Test
	@Rollback
	public void testFindByUsername() {
		PaymentCardDao.persist(card);
		assertEquals("1234567812345678", PaymentCardDao.findByUsername("wilma").get(0).getNumber());
	}
	
	@Test
	@Rollback
	public void testSetUsersPreferred() {
		PaymentCardDao.persist(card);
		assertEquals(false, PaymentCardDao.find(card.getId()).isPreferred());
		PaymentCardDao.setUsersPreferred(card.getUser().getUsername(), card);
		assertEquals(true, PaymentCardDao.find(card.getId()).isPreferred());
	}
	
	@After
	public void tearDown() {
	}
    
}
