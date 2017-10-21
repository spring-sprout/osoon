package io.osoon.security;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yoon
 * @author Whiteship
 */
public class OSoonRememberMeAuthenticationFilter extends GenericFilterBean {

    private final RememberMeServices rememberMeServices;

    public OSoonRememberMeAuthenticationFilter(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
        ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth instanceof RememberMeAuthenticationToken) {
            rememberMeServices.loginSuccess((HttpServletRequest) servletRequest,
                (HttpServletResponse) servletResponse, auth);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
