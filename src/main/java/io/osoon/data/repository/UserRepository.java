package io.osoon.data.repository;

import io.osoon.data.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	@Query("MATCH (n:User) WHERE n.name = {0} RETURN n")
	User findByName(String s);

	@Query("MATCH (n:User) WHERE n.email = {0} RETURN n")
	Optional<User> findByEmail(String email);
}
