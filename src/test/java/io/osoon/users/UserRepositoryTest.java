package io.osoon.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    public void addUser() {
        Role role = new Role();
        role.setName("Admin");

        User user = new User();
        user.setEmail("keesun@gmail.com");
        user.setUsername("keesun#1123");

        user.addRole(role);

        userRepository.save(user);
        userRepository.flush();
    }

}