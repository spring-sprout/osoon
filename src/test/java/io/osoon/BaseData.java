package io.osoon;

import java.util.Optional;

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
		Optional<User> byEmail1 = userService.findByEmail(user1.getEmail());
		if (byEmail1.isPresent()) user1 = byEmail1.get();
		else userService.saveOne(user1);

		Optional<User> byEmail2 = userService.findByEmail(user2.getEmail());
		if (byEmail2.isPresent()) user2 = byEmail2.get();
		else userService.saveOne(user2);


		Optional<User> byEmail3 = userService.findByEmail(user3.getEmail());
		if (byEmail3.isPresent()) user3 = byEmail3.get();
		else userService.saveOne(user3);

	}
}
