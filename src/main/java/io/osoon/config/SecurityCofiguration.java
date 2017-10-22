package io.osoon.config;

import io.osoon.config.properties.OSoonProperties;
import io.osoon.data.repository.UserRepository;
import io.osoon.security.OSoonRememberMeAuthenticationFilter;
import io.osoon.security.OSoonUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.savedrequest.NullRequestCache;

/**
 * @author whiteship
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityCofiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OSoonProperties oSoonProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * To keep readability, please do not use IDE's code organization feature for this code.
         *
         * Current format for security configuration is:
         *  .security feature() // Feature method to config
         *      .feature-specific configurations()
         *      .and() // And of the configuration for the feature
         *  .another security feature()
         *      .configuration()
         */
        http
            .authorizeRequests()
                .antMatchers("/api/session").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
            .headers()
                .frameOptions().disable() // for h2
                .and()
            .requestCache()
                .requestCache(new NullRequestCache())
                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
            .rememberMe()
                .rememberMeServices(rememberMeServices())
                .key(oSoonProperties.getRememberMeKey())
                .and()
            .csrf()
                .disable()
            .logout()
                .logoutRequestMatcher(r -> r.getMethod().equalsIgnoreCase("DELETE") && r.getRequestURI().equalsIgnoreCase("/api/session"))
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .deleteCookies(oSoonProperties.getCookieName())
                .and()
            .addFilterBefore(rememberMeAuthenticationFilter(), RememberMeAuthenticationFilter.class);
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices(oSoonProperties.getRememberMeKey(), userDetailsService());
        rememberMeServices.setCookieName(oSoonProperties.getCookieName());
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new OSoonUserDetailsService(userRepository);
    }

    @Bean
    public OSoonRememberMeAuthenticationFilter rememberMeAuthenticationFilter() {
        return new OSoonRememberMeAuthenticationFilter(rememberMeServices());
    }

}
