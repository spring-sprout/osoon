package io.osoon.web.api;

import javax.servlet.http.HttpSession;

import io.osoon.security.OSoonUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.osoon.data.domain.User;
import io.osoon.security.UserView;

/**
 * @author whiteship
 */
@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.GET)
    public UserView session(@AuthenticationPrincipal OSoonUserDetails userDetails) {
        String name = userDetails == null ? null : userDetails.getName();
        return new UserView(name);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void logout(HttpSession session) {
        session.invalidate();
    }

}