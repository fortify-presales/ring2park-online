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

import com.dps.ring2park.domain.News;

/**
 * Tests News DAO.
 * @author Kevin A. Lee
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class NewsDaoTest {
    
    @Autowired
    private NewsDao NewsDao = null;
    
    private News news;

	@Before
	public void setup() {
		news = DataSeeder.generateNews();
	}

	@Test
	@Rollback
	public void testPersist() {
		NewsDao.persist(news);
		assertEquals("New Location...", NewsDao.find(news.getId()).getTitle());
	}

	@Test
	@Rollback
	public void testUpdate() {
		NewsDao.persist(news);
		news.setTitle("Updated Location");
		NewsDao.merge(news);
		assertEquals("Updated Location", NewsDao.find(news.getId()).getTitle());
	}

	@Test
	@Rollback
	public void testDelete() {
		NewsDao.persist(news);
		NewsDao.remove(news);
		assertEquals(3l, NewsDao.findAll().size());
	}

	@Test
	@Rollback
	public void testRead() {
		NewsDao.persist(news);
		assertEquals(4l, NewsDao.findActive().size());
		assertEquals(2l, NewsDao.findActiveMax(2).size());
		News result = NewsDao.findActive().get(0);
		assertEquals("New Location...", result.getTitle());
		assertEquals("A new parking location has been added.", result.getContent());
	}
	
	@After
	public void tearDown() {
	}
    
}
