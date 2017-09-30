package io.osoon.web.api;

import io.osoon.security.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * @author whiteship
 */
@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.GET)
    public UserView session(Principal user) {
        String name = user == null ? null : user.getName();
        return new UserView(name);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void logout(HttpSession session) {
        session.invalidate();
    }

}