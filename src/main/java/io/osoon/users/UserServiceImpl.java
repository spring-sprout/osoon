package io.osoon.users;

import io.osoon.users.web.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public User registerNewUser(UserSignUpDto userSignUpDto) {
        User user = new User();
        user.setEmail(userSignUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
        user.setUsername(generateRandomUsername(userSignUpDto.getEmail()));
        User newUser = userRepository.save(user);

        Role userRole = getUserRole();
        newUser.getRoles().add(userRole);

        return newUser;
    }

    private Role getUserRole() {
        return roleRepository.findByName(Role.ROLE_USER).orElseGet(() -> {
            Role role = new Role();
            role.setName(Role.ROLE_USER);
            return roleRepository.save(role);
        });
    }

    private String generateRandomUsername(String email) {
        String prefix = email.split("@")[0];
        return prefix + "#" + String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(usernameOrEmail)
                .orElseGet(() -> userRepository.findByUsername(usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail)));

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(mapRolesToAuthorities(user.getRoles()))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

}
