package io.osoon.configs;

import io.osoon.users.UserService;
import io.osoon.users.security.LoggingAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
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
                "/access-denied",
                "/api/session", // API that can tell who is this.
        };

        // @formatter:off
        http.authorizeRequests()
                .mvcMatchers(permitAllMatchers).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
            .httpBasic()
                .realmName("dev.osoon.io")
                .and()
            .formLogin()
                .loginPage("/login").failureUrl("/loginfail").permitAll()
                .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
            .csrf()
                .disable();
        // @formatter:on
    }
}
