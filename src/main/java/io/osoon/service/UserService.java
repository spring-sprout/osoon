package io.osoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.osoon.data.domain.User;
import io.osoon.data.repository.UserRepository;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@Service
public class UserService {

	@Autowired private UserRepository repository;

	public User saveOne(User user) {
		return repository.save(user);
	}
}
