package io.osoon.web.api;

import com.fasterxml.jackson.databind.JsonNode;
import io.osoon.domain.Meeting;
import io.osoon.domain.MeetingLocation;
import io.osoon.domain.User;
import io.osoon.repository.MeetingLocationRepository;
import io.osoon.repository.MeetingRepository;
import io.osoon.repository.TopicRepository;
import io.osoon.repository.UserRepository;
import io.osoon.exception.MeetingNotFoundException;
import io.osoon.exception.UserNotFoundException;
import io.osoon.security.OSoonUserDetails;
import io.osoon.service.MeetingService;
import io.osoon.service.MeetingUpdateService;
import io.osoon.service.UserService;
import io.osoon.service.MeetingAttendService;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	@Autowired private MeetingUpdateService meetingUpdateService;

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
	public MeetingView update(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id, @RequestBody JsonNode updateBody) {
		Meeting existingMeeting = getMeeting(id);
		User user = getUser(userDetails);
		Meeting updateMeeting = meetingUpdateService.update(existingMeeting, updateBody, user);
		MeetingViewDto meetingViewDto = modelMapper.map(updateMeeting, MeetingViewDto.class);
		MeetingView meetingView = new MeetingView(meetingViewDto);
		meetingView.add(linkTo(methodOn(MeetingController.class).view(updateMeeting.getId())).withRel("meeting-view"));
		return meetingView;
	}

	@PostMapping("{id}/attend")
	public MeetingView attend(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id) {
		Meeting meeting = service.findById(id).orElseThrow(() -> new MeetingNotFoundException(id));

		meetingAttendService.attend(meeting, getUser(userDetails));

		MeetingViewDto meetingViewDto = modelMapper.map(meeting, MeetingViewDto.class);
		MeetingView meetingView = new MeetingView(meetingViewDto);
		meetingView.add(linkTo(methodOn(MeetingController.class).view(meeting.getId())).withRel("meeting-view"));

		return meetingView;
	}

	@PostMapping("{id}/leave")
	public MeetingView leave(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id) {
		Meeting meeting = service.findById(id).orElseThrow(() -> new MeetingNotFoundException(id));

		meetingAttendService.attendCancel(meeting, getUser(userDetails));

		MeetingViewDto meetingViewDto = modelMapper.map(meeting, MeetingViewDto.class);
		MeetingView meetingView = new MeetingView(meetingViewDto);
		meetingView.add(linkTo(methodOn(MeetingController.class).view(meeting.getId())).withRel("meeting-view"));

		return meetingView;
	}

	@PutMapping("{id}/changestatus/{status}")
	public MeetingView changeStatus(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id, @PathVariable String status) {
		Meeting meeting = service.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

		service.changeStatus(meeting, Meeting.MeetingStatus.valueOf(status.toUpperCase()), userDetails.getId());

		MeetingViewDto meetingViewDto = modelMapper.map(meeting, MeetingViewDto.class);
		MeetingView meetingView = new MeetingView(meetingViewDto);
		meetingView.add(linkTo(methodOn(MeetingController.class).view(meeting.getId())).withRel("meeting-view"));

		return meetingView;
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
	public Page<UserDto> attendees(@AuthenticationPrincipal OSoonUserDetails userDetails, @PathVariable long id) {
		Page<User> users = service.listAttendees(id, userDetails.getId(), PageRequest.of(0, 10));
		List<UserDto> userDtos = users.getContent().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

		return new PageImpl(userDtos, users.getPageable(), users.getTotalElements());
	}

    private User getUser(@AuthenticationPrincipal OSoonUserDetails userDetails) {
        long id = userDetails.getId();
        return userRepository.findById(id, 0).orElseThrow(() -> new UserNotFoundException(id));
    }

    private Meeting getMeeting(long id) {
		return repository.findById(id).orElseThrow(() -> new MeetingNotFoundException(id));
	}

}
