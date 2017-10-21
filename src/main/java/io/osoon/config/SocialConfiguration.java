package io.osoon.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.web.SignInAdapter;

import io.osoon.security.AuthUtil;

/**
 * @author whiteship
 */
@Configuration
public class SocialConfiguration {

    @Bean
    public SocialConfigurer socialConfigurerAdapter(DataSource dataSource) {
        // https://github.com/spring-projects/spring-social/blob/master/spring-social-config/src/main/java/org/springframework/social/config/annotation/SocialConfiguration.java#L87
        return new DatabaseSocialConfigurer(dataSource);
    }

    @Bean
    public SignInAdapter authSignInAdapter(AuthUtil authUtil) {
        return (userId, connection, request) -> {
            authUtil.authenticate(connection);
            return null;
        };
    }
}
