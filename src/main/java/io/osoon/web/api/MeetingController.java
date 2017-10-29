package io.osoon.web.api;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.domain.User;
import io.osoon.data.domain.UserFile;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.exception.MeetingNotFoundException;
import io.osoon.exception.UserNotFoundException;
import io.osoon.security.OSoonUserDetails;
import io.osoon.service.MeetingService;
import io.osoon.service.UserService;
import io.osoon.web.dto.UserFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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
	@Autowired private UserService userService;

	@PostMapping("create")
	public Meeting create(@AuthenticationPrincipal OSoonUserDetails userDetails, Meeting meeting) {
		return service.create(userService.findById(userDetails.getId()).get(), meeting);
	}

	@GetMapping("{id}")
	public Meeting view(@PathVariable long id) {
		return repository.findById(id).get();
	}

	@PutMapping("{id}/update")
	public Meeting update(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id, Meeting target, MeetingLocation location) {
		return service.update(target, id, userDetails.getId());
	}

	@PostMapping("{id}/join")
	public Meeting join(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id) {
		User user = userService.findById(userDetails.getId()).orElseThrow(NullPointerException::new);
		Meeting meeting = service.findById(id).orElseThrow(NullPointerException::new);

		service.join(meeting, user);

		return meeting;
	}

	@PostMapping("{id}/leave")
	public User leave(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id) {
		service.leave(id, userDetails.getId());
		return userService.findById(userDetails.getId()).get();
	}

	@PutMapping("{id}/changestatus/{status}")
	public Meeting changeStatus(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id, @PathVariable String status) {
		Meeting meeting = service.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

		service.changeStatus(meeting, Meeting.MeetingStatus.valueOf(status.toUpperCase()), userDetails.getId());
		return meeting;
	}

	/**
	 * 모임 삭제 관련해서는 정리 필요.
	 * 모임 생성자가 삭제 해버리면 참여자는 참여했던 모임에 대한 정보를 어떻게 봐야 될지 모름.
	 * @param userDetails
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}/delete")
	public User delete(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id) {
		Optional<User> user = userService.findById(userDetails.getId());

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
