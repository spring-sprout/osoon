package io.osoon.security;

import io.osoon.data.domain.User;
import io.osoon.data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        User existingUser = userRepository.findByName(username);

        if (existingUser == null) {
            throw new UsernameNotFoundException(username);
        }

        return new OSoonUserDetails(existingUser);
    }
}
