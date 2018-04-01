package io.osoon.configs;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        // @formatter:off
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .mvcMatchers("/**/*.css", "/**/*.js");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] permitAllMatchers = new String[]{
                "/", // the first page.
                "/signup", // sign up page.
                "/api/session", // API that can tell who is this.
        };

        // @formatter:off
        http.authorizeRequests()
                .mvcMatchers(permitAllMatchers).permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
            .formLogin()
                .loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
            .logout().permitAll();
        // @formatter:on
    }
}
