package io.osoon.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    public void addUser() {
        User user = new User();
        user.setEmail("keesun@gmail.com");
        user.setUsername("keesun#1123");

        User newUser = userRepository.saveAndFlush(user);

        assertThat(newUser.getId()).isNotZero();
    }

    @Test
    public void findByEmail_findByUsername() {
        // Given
        String username = "keesun#1123";
        String email = "keesun@gmail.com";

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        User newUser = userRepository.saveAndFlush(user);

        // When & Then
        assertThat(userRepository.findByEmail(email)).hasValue(newUser);
        assertThat(userRepository.findByUsername(username)).hasValue(newUser);
    }

}