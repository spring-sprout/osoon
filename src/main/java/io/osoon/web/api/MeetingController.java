package io.osoon.web.api;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.domain.User;
import io.osoon.data.repository.MeetingLocationRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.TopicRepository;
import io.osoon.data.repository.UserRepository;
import io.osoon.exception.MeetingNotFoundException;
import io.osoon.exception.UserNotFoundException;
import io.osoon.security.OSoonUserDetails;
import io.osoon.service.meeting.MeetingService;
import io.osoon.service.UserService;
import io.osoon.service.meeting.MeetingAttendService;
import io.osoon.web.dto.MeetingCreateDto;
import io.osoon.web.dto.MeetingLocationDto;
import io.osoon.web.dto.MeetingViewDto;
import io.osoon.web.dto.UserDto;
import io.osoon.web.hateoas.MeetingView;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
	@Autowired private MeetingAttendService meetingAttendService;
	@Autowired private UserService userService;
	@Autowired private UserRepository userRepository;
	@Autowired private ModelMapper modelMapper;
	@Autowired private MeetingLocationRepository locationRepository;
	@Autowired private TopicRepository topicRepository;

    /**
     * 모임 만들 때 참고할 데이터를 제공합니다.
     *
     * @param userDetails
     * @return
     */
	@GetMapping("create")
	public MeetingCreateDto createMeeting(@AuthenticationPrincipal OSoonUserDetails userDetails) {
        User user = getUser(userDetails);
        List<MeetingLocation> byUser = locationRepository.findByUser(userDetails.getUser());

		MeetingCreateDto dto = new MeetingCreateDto();
		dto.setUser(modelMapper.map(user, UserDto.class));
        dto.setPlaces(modelMapper.map(byUser, new TypeToken<List<MeetingLocationDto>>(){}.getType()));
		dto.setTopics(topicRepository.findAll());
		dto.setMeetingOnOffTypes(Meeting.MeetingOnOffType.values());
		dto.setOnlineTypes(Meeting.OnlineType.values());

		return dto;
	}

    /**
     * 모임 생성 API
     *
     * @param userDetails
     * @param meeting
     * @return
     */
	@PostMapping("create")
	public MeetingView createMeeting(@AuthenticationPrincipal OSoonUserDetails userDetails, @RequestBody Meeting meeting) {
        User user = getUser(userDetails);
        Meeting newMeeting = service.create(user, meeting);
        MeetingViewDto meetingViewDto = modelMapper.map(newMeeting, MeetingViewDto.class);
        MeetingView meetingView = new MeetingView(meetingViewDto);
        meetingView.add(linkTo(methodOn(MeetingController.class).view(newMeeting.getId())).withRel("meeting-view"));
        return meetingView;
	}

	@GetMapping("{id}")
	public MeetingViewDto view(@PathVariable long id) {
        Meeting meeting = repository.findById(id).orElseThrow(() -> new MeetingNotFoundException(id));
        return modelMapper.map(meeting, MeetingViewDto.class);
	}

	@PutMapping("{id}/update")
	public Meeting update(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id, Meeting target, MeetingLocation location) {
		return service.update(target, id, userDetails.getId());
	}

	@PostMapping("{id}/attend")
	public Meeting attend(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id) {
		User user = getUser(userDetails);
		Meeting meeting = service.findById(id).orElseThrow(NullPointerException::new);

		meetingAttendService.attend(meeting, user);

		return meeting;
	}

	@PostMapping("{id}/leave")
	public User leave(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id) {
		meetingAttendService.attendCancel(id, userDetails.getId());
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
	public Page<User> attendees(@AuthenticationPrincipal OSoonUserDetails userDetails, long meetingId) {
		return service.listAttendees(meetingId, userDetails.getId(), PageRequest.of(0, 10));
	}

	@GetMapping("list")
	public Page<Meeting> list() {
		return repository.findAll(PageRequest.of(0, 10));
	}

    private User getUser(@AuthenticationPrincipal OSoonUserDetails userDetails) {
        long id = userDetails.getId();
        return userRepository.findById(id, 0).orElseThrow(() -> new UserNotFoundException(id));
    }


}
