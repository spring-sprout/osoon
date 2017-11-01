package io.osoon.web.api;

import io.osoon.data.domain.AttendMeeting;
import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;
import io.osoon.security.OSoonUserDetails;
import io.osoon.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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
	@Autowired private MeetingRepository meetingRepository;

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

	@GetMapping("testAll")
	public void testAll() {
		User user = repository.findAll(PageRequest.of(0, 10)).getContent().get(0);
		Meeting meeting = meetingRepository.findAll(PageRequest.of(0, 10)).getContent().get(0);

		user.attendTo(meeting);

		repository.save(user);
	}

	@GetMapping("makeNew")
	public User makeNew() {
		User user = service.saveOne(User.of(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "@gmail.com", UUID.randomUUID().toString()));
		//User user = new User("dosajun@gmail.com", "김제준");
		Meeting meeting = Meeting.of("MeetUP", "right now!");
		user.create(meeting);

		repository.save(user);

		User user2 = service.saveOne(User.of(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "@gmail.com", UUID.randomUUID().toString()));
		user2.attendTo(meeting);
		repository.save(user2);

		return user;
	}

}
