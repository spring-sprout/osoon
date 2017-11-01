package io.osoon.service;

import java.util.Optional;

import io.osoon.data.domain.User;
import io.osoon.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public Optional<User> findById(long id) {
		return repository.findById(id);
	}
}
