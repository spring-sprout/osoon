package io.osoon.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.osoon.data.domain.User;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByName(String s);
}
