package io.osoon.data.repository;

import io.osoon.data.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.driver.Driver;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Before
    public void before() {
        userRepository.deleteAll();
    }

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

    private User createUser(String name) {
        User user = User.of(name + "@email.com", name);
        user.setImageUrl("https://www.gravatar.com/avatar/d3a3e1e76decd8760aaf9af6ab334264");
        return user;
    }

    @After
    public void after() {
        userRepository.deleteAll();
    }

}