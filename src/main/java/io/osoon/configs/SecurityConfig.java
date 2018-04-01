package io.osoon.configs;

import io.osoon.users.OsoonUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OsoonUserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

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
            .httpBasic()
                .and()
            .formLogin()
                .loginPage("/login").failureUrl("/loginfail").permitAll()
                .and()
            .csrf().disable()
            .logout().permitAll();
        // @formatter:on
    }
}
