package io.osoon.users;

import io.osoon.users.web.UserSignUpDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User registerNewUser(UserSignUpDto userSignUpDto);
}
