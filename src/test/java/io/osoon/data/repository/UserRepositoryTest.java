package io.osoon.data.repository;

import io.osoon.data.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        assertThat(userRepository.count(), is(1l));

        // When
        User keesun = userRepository.findByName(name, 0);

        // Then
        assertNotNull(keesun);
    }

}