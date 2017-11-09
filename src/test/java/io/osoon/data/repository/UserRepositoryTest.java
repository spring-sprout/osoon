package io.osoon.data.repository;

import io.osoon.data.domain.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author whiteship
 */
public class UserRepositoryTest extends RepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Test
    public void findByName() {
        // Given
        assertThat(userRepository.count(), is(0l));

        String name = "Keesun Baik";
        User user = createUser(name);
        userRepository.save(user);
        assertThat(userRepository.count(), is(1l));

        userRepository.save(createUser("Sophia"));
        assertThat(userRepository.count(), is(2l));

        // When
        Optional<User> keesun = userRepository.findByName(name, 0);

        // Then
        assertTrue(keesun.isPresent());
    }

    @Test
    public void findById() {
        // Given
        User user = userRepository.save(createUser("keesun"));

        // When
        Optional<User> userById = userRepository.findById(user.getId(), 0);

        // Then
        assertTrue(userById.isPresent());
    }

    @Test
    public void findById_not_exists() {
        // When
        Optional<User> userById = userRepository.findById(42L);

        // Then
        assertFalse(userById.isPresent());
    }

    private User createUser(String name) {
        User user = User.of(name + "@email.com", name);
        user.setImageUrl("https://www.gravatar.com/avatar/d3a3e1e76decd8760aaf9af6ab334264");
        return user;
    }

}