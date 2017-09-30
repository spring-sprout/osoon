package io.osoon.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.NullRequestCache;

/**
 * @author whiteship
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityCofiguration extends WebSecurityConfigurerAdapter {

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
            .csrf()
                .disable();
    }

}
