package io.osoon.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.osoon.BaseData;
import io.osoon.data.domain.User;

import static org.junit.Assert.assertEquals;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-11-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests extends BaseData {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTests.class);

	@Test
	public void findById() {
		User byId = userService.findById(user2.getId()).get();
		assertEquals(byId.getEmail(), user2.getEmail());
		logger.info(byId.toString());
	}
}
