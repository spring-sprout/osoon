package io.osoon;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import io.osoon.data.domain.User;
import io.osoon.service.UserService;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-11-09
 */
public abstract class BaseData {
	@Autowired protected UserService userService;

	protected User user1 = User.of("dosajun@test.com", "김제준-t");
	protected User user2 = User.of("Keesun.baik@test.com", "백기선-t");
	protected User user3 = User.of("outsideris@test.com", "변정훈-t");

	@Before
	public void newUser() {
		user1 = userService.findByEmail(user1.getEmail()).get();
		if (user1 == null) userService.saveOne(user1);
		user2 = userService.findByEmail(user2.getEmail()).get();
		if (user2 == null) userService.saveOne(user2);
		user3 = userService.findByEmail(user3.getEmail()).get();
		if (user3 == null) userService.saveOne(user3);
	}
}
