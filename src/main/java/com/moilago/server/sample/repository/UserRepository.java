package com.moilago.server.sample.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moilago.server.sample.domain.User;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-09-18
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByName(String s);
}
