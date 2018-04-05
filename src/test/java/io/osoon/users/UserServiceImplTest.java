package io.osoon.users;

import io.osoon.users.web.UserSignUpDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void registerNewUser() {
        // Given
        UserSignUpDto signUpDto = new UserSignUpDto();
        signUpDto.setEmail("keesun@mail.com");
        signUpDto.setPassword("password");

        // When
        User user = userService.registerNewUser(signUpDto);

        // Then
        assertThat(user.getId()).isNotZero();
        assertThat(user.getUsername()).startsWith("keesun#");
        Role userRole = roleRepository.findByName(Role.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROLE_UESR doesn't exist"));
        assertThat(user.getRoles()).contains(userRole);
    }

    @Test
    public void loadByUsername() {
        // Given
        UserSignUpDto signUpDto = new UserSignUpDto();
        signUpDto.setEmail("keesun@mail.com");
        signUpDto.setPassword("password");
        User newUser = userService.registerNewUser(signUpDto);

        // When
        UserDetails userDetailsByUsername = userService.loadUserByUsername(newUser.getUsername());
        UserDetails userDetailsByEmail = userService.loadUserByUsername(newUser.getEmail());

        // Then
        assertThat(userDetailsByEmail).isEqualTo(userDetailsByUsername);
        assertThat(userDetailsByEmail.getUsername()).isEqualTo(newUser.getUsername());
    }

}