package io.osoon.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

import io.osoon.data.repository.UserRepository;
import io.osoon.service.UserService;

/**
 * @author whiteship
 */
@Service
public class AuthUtil {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    private static final Logger log = LoggerFactory.getLogger(AuthUtil.class);

    public void authenticate(Connection<?> connection) {
        Facebook facebook = (Facebook) connection.getApi();
        String [] fields = { "id", "name", "email", "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, fields);
        String username = userProfile.getName();
        Optional<io.osoon.data.domain.User> byEmail = userRepository.findByEmail(userProfile.getEmail());
        io.osoon.data.domain.User osoonUser;
        if (byEmail.isPresent()) {
            osoonUser = byEmail.get();
        } else {
            io.osoon.data.domain.User newUser = io.osoon.data.domain.User.of(userProfile.getEmail(), username);
            osoonUser = userService.saveOne(newUser);
        }
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(osoonUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("User {} {} {} connected.", userProfile.getFirstName(), userProfile.getLastName(), userProfile.getEmail());
    }

}