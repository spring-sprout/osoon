package io.osoon.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.osoon.helper.BaseDataTestHelper;
import io.osoon.domain.User;

import static org.junit.Assert.assertEquals;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-11-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTests.class);

	@Autowired UserService userService;

	@Autowired
    BaseDataTestHelper baseDataTestHelper;

	@Test
	public void findById() {
		User byId = userService.findById(baseDataTestHelper.getUser2().getId()).get();
		assertEquals(byId.getEmail(), baseDataTestHelper.getUser2().getEmail());
		logger.info(byId.toString());
	}
}
