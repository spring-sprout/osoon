package io.osoon.web.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;
import io.osoon.service.UserService;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@RestController
@RequestMapping("/samples/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired private UserService service;
	@Autowired private UserRepository repository;
	@Autowired private MeetingRepository meetingRepository;

	@PutMapping("/signUp")
	public User save(String email, String name) {
		return service.saveOne(new User(email, name));
	}

	@GetMapping("/show")
	public User show(long userId) {
		Optional<User> user = repository.findById(userId);
		return user.get();
	}

	@GetMapping("/list")
	public Page<User> list() {
		Page<User> users = repository.findAll(PageRequest.of(0, 10));
		return users;
	}

	@GetMapping("/testAll")
	public void testAll() {
		User user = repository.findAll(PageRequest.of(0, 10)).getContent().get(0);
		Meeting meeting = meetingRepository.findAll(PageRequest.of(0, 10)).getContent().get(0);

		user.attendTo(meeting);

		repository.save(user);
	}

	@GetMapping("/makeNew")
	public User makeNew() {
		User user = service.saveOne(new User(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "@gmail.com", UUID.randomUUID().toString()));
		//User user = new User("dosajun@gmail.com", "김제준");
		Meeting meeting = new Meeting("MeetUP", "right now!");
		user.make(meeting);

		repository.save(user);

		User user2 = service.saveOne(new User(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "@gmail.com", UUID.randomUUID().toString()));
		user2.attendTo(meeting);
		repository.save(user2);

		return user;
	}

}
