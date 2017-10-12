package io.osoon.web.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.osoon.data.domain.AttendMeeting;
import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.AttendMeetingRepository;
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
	@Autowired private AttendMeetingRepository attendMeetingRepository;

	@PutMapping("make")
	public Meeting make(@AuthenticationPrincipal User user, String title, String contents) {
		Meeting meeting = Meeting.of(title, contents);
		user.make(meeting);
		userRepository.save(user);
		return meeting;
	}

	@PostMapping("delete")
	public User delete(@AuthenticationPrincipal User loginUser, long meetingId) {
		Optional<User> user = userRepository.findById(loginUser.getId());
		Optional<Meeting> meeting = repository.findById(meetingId);

		User existUser = user.get();

		service.changeStatus(meeting.get(), Meeting.MeetingStatus.END, existUser);
		return existUser;
	}

	@GetMapping("show")
	public Meeting show(long id) {
		return repository.findById(id).get();
	}

	@GetMapping("list")
	public Page<Meeting> list() {
		return repository.findAll(PageRequest.of(0, 10));
	}

	@PostMapping("join")
	public User join(@AuthenticationPrincipal User loginUser, long meetingId) {
		Optional<User> user = userRepository.findById(loginUser.getId());
		Optional<Meeting> meeting = repository.findById(meetingId);

		User existUser = user.get();
		service.join(meeting.get(), user.get());

		return existUser;
	}

	@PostMapping("leave")
	public User leave(@AuthenticationPrincipal User loginUser, long meetingRelationId) {
		Optional<User> user = userRepository.findById(loginUser.getId());
		Optional<AttendMeeting> attendMeeting = attendMeetingRepository.findById(meetingRelationId);

		if (!attendMeeting.isPresent() || !attendMeeting.get().getUser().getId().equals(loginUser.getId())) {
			logger.error("취소할려는 모임에 참여 하지 않았습니다.");
			return user.get();
		}

		attendMeetingRepository.deleteById(meetingRelationId);

		return userRepository.findById(loginUser.getId()).get();
	}

	@GetMapping("attendees")
	public Page<User> attendees(long meetingId) {
		return repository.getUsersThatJoined(meetingId, PageRequest.of(0, 10));
	}
}
