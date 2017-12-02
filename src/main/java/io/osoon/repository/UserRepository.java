package io.osoon.repository;

import java.util.Optional;

import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import io.osoon.domain.User;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

	Optional<User> findByName(String name, @Depth int depth);

	Optional<User> findByEmail(String email);
}
