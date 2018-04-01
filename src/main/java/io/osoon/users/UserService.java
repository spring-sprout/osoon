package io.osoon.users;

import io.osoon.users.web.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerNewUser(UserSignUpDto userSignUpDto) {
        User user = new User();
        user.setEmail(userSignUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
        user.setUsername(generateRandomUsername(userSignUpDto.getEmail()));
        user.getRoles().add(new Role("ROLE_USER"));
        userRepository.save(user);
    }

    private String generateRandomUsername(String email) {
        String prefix = email.split("@")[0];
        return prefix + "#" + String.format("%04d", new Random().nextInt(10000));
    }
}
