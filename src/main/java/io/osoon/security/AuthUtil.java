package io.osoon.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

import io.osoon.repository.UserRepository;

/**
 * @author whiteship
 */
@Service
public class AuthUtil {

    @Autowired
    UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(AuthUtil.class);

    public void authenticate(Connection<?> connection) {
        Facebook facebook = (Facebook) connection.getApi();
        String [] fields = { "id", "name", "email", "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, fields);
        String username = userProfile.getName();

        log.info("User Profile by facebook {} {} {}", userProfile.getFirstName(), userProfile.getLastName(), userProfile.getEmail());

        Optional<io.osoon.domain.User> byEmail = userRepository.findByEmail(userProfile.getEmail());
        io.osoon.domain.User osoonUser;

        if (byEmail.isPresent()) {
            osoonUser = byEmail.get();
        } else {
            io.osoon.domain.User newUser = io.osoon.domain.User.of(userProfile.getEmail(), username);
            newUser.setImageUrl(connection.getImageUrl());
            osoonUser = userRepository.save(newUser);
        }

        OSoonUserDetails userDetails = new OSoonUserDetails(osoonUser);
        RememberMeAuthenticationToken rememberMeToken = new RememberMeAuthenticationToken("osoon-remember-me", userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(rememberMeToken);

        log.info("User {} {} {} connected.", userProfile.getFirstName(), userProfile.getLastName(), userProfile.getEmail());
    }

}