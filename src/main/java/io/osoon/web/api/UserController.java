package io.osoon.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.osoon.domain.User;
import io.osoon.repository.UserRepository;
import io.osoon.security.OSoonUserDetails;
import io.osoon.service.UserService;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@RestController
@RequestMapping("/api/user/")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired private UserService service;
	@Autowired private UserRepository repository;

	@PostMapping("signup")
	public User save(String email, String name) {
		return service.saveOne(User.of(email, name));
	}

	@GetMapping("myinfo")
	public User myinfo(@AuthenticationPrincipal OSoonUserDetails userDetails) {
		return repository.findById(userDetails.getId()).orElseThrow(NullPointerException::new);
	}

	@GetMapping("{id}")
	public User show(@PathVariable Long userId) {
		return repository.findById(userId).orElseThrow(NullPointerException::new);
	}

	@GetMapping("list")
	public Page<User> list() {
		Page<User> users = repository.findAll(PageRequest.of(0, 10));
		return users;
	}
}
