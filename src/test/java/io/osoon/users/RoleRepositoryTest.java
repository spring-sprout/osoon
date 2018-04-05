package io.osoon.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired RoleRepository roleRepository;

    @Test
    public void findByName() {
        // Given
        String roleName = Role.ROLE_USER;
        Role role = new Role();
        role.setName(roleName);
        Role newRole = roleRepository.save(role);

        // When & Then
        assertThat(roleRepository.findByName(Role.ROLE_USER)).hasValue(newRole);
    }
}