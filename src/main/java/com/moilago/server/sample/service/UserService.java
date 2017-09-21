package com.moilago.server.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moilago.server.sample.domain.User;
import com.moilago.server.sample.repository.UserRepository;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-09-18
 */
@Service
public class UserService {

	@Autowired private UserRepository repository;

	public User saveOne(User user) {
		return repository.save(user);
	}
}
