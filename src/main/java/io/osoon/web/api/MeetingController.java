package io.osoon.web.api;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;
import io.osoon.service.MeetingService;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-19
 */
@RestController
@RequestMapping("/api/meeting/")
public class MeetingController {
	private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);
	@Autowired private MeetingRepository repository;
	@Autowired private MeetingService service;
	@Autowired private UserRepository userRepository;

	@PutMapping("make")
	public Meeting make(@AuthenticationPrincipal User user, Meeting meeting) {
		meeting.setMeetingStatus(Meeting.MeetingStatus.READY);
		user.make(meeting);
		userRepository.save(user);
		return meeting;
	}

	@GetMapping("{id}")
	public Meeting view(@PathVariable long id) {
		return repository.findById(id).get();
	}

	@PostMapping("{id}/join")
	public Meeting join(@AuthenticationPrincipal User loginUser, @PathVariable long id) {
		User user = userRepository.findById(loginUser.getId()).orElseThrow(NullPointerException::new);
		Meeting meeting = repository.findById(id).orElseThrow(NullPointerException::new);

		service.join(meeting, user);

		return meeting;
	}

	@PostMapping("{id}/leave")
	public User leave(@AuthenticationPrincipal User loginUser, @PathVariable long id, HttpServletResponse res) {
		service.leave(id, loginUser.getId());
		return loginUser;
	}

	@PutMapping("{id}/changestatus/{status}")
	public Meeting changeStatus(@AuthenticationPrincipal User loginUser, @PathVariable long id, @PathVariable String status) {
		Meeting meeting = repository.findById(id).orElseThrow(NullPointerException::new);

		service.changeStatus(meeting, Meeting.MeetingStatus.valueOf(status.toUpperCase()), loginUser.getId());
		return meeting;
	}

	/**
	 * 모임 삭제 관련해서는 정리 필요.
	 * 모임 생성자가 삭제 해버리면 참여자는 참여했던 모임에 대한 정보를 어떻게 봐야 될지 모름.
	 * @param loginUser
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}/delete")
	public User delete(@AuthenticationPrincipal User loginUser, @PathVariable long id) {
		Optional<User> user = userRepository.findById(loginUser.getId());

		return user.get();
	}

	@GetMapping("{id}/attendees")
	public Page<User> attendees(long meetingId) {
		return repository.getUsersThatJoined(meetingId, PageRequest.of(0, 10));
	}

	@GetMapping("list")
	public Page<Meeting> list() {
		return repository.findAll(PageRequest.of(0, 10));
	}
}
