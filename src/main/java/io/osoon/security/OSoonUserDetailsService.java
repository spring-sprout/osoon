package io.osoon.security;

import io.osoon.data.domain.User;
import io.osoon.data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

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
