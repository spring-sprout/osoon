package io.osoon.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.osoon.domain.User;
import io.osoon.repository.UserRepository;

/**
 * @author whiteship
 */
public class OSoonUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public OSoonUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> existingUser = userRepository.findByEmail(username);

        if (!existingUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return new OSoonUserDetails(existingUser.get());
    }
}
