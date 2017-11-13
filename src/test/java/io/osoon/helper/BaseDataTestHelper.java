package io.osoon.helper;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Before;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.osoon.data.domain.User;
import io.osoon.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-11-09
 */
@Component
public class BaseDataTestHelper {

	@Autowired protected UserService userService;

	private User user1 = User.of("dosajun@test.com", "김제준-t");
    private User user2 = User.of("Keesun.baik@test.com", "백기선-t");
    private User user3 = User.of("outsideris@test.com", "변정훈-t");

	public void createUsers() {
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

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public User getUser3() {
        return user3;
    }
}
