package io.osoon.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;

/**
 * @author whiteship
 */
public class AuthUtil {

    private static final Logger log = LoggerFactory.getLogger(AuthUtil.class);

    public static void authenticate(Connection<?> connection) {
        Facebook facebook = (Facebook) connection.getApi();
        String [] fields = { "id", "name", "email", "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, fields);
        String username = userProfile.getName();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("User {} {} connected.", userProfile.getFirstName(), userProfile.getLastName());
    }
}