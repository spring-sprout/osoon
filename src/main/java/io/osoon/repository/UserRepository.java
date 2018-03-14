package io.osoon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.osoon.domain.User;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @author 백기선 (whiteship2000@gmail.com)
 * @since 2017-09-18
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
