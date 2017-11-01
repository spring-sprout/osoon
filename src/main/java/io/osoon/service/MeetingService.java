package io.osoon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import io.osoon.data.domain.*;
import io.osoon.data.repository.AttendMeetingRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.TopicRepository;
import io.osoon.data.repository.UserRepository;

import org.neo4j.ogm.session.Neo4jSession;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import static java.util.stream.Collectors.toList;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-21
 */
@Service
public class MeetingService {
	private Logger logger = LoggerFactory.getLogger(MeetingService.class);

	@Autowired private MeetingRepository repository;
	@Autowired private AttendMeetingRepository attendMeetingRepository;
	@Autowired private UserService userService;
	@Autowired private TopicService topicService;

	public Meeting create(User user, Meeting meeting) {
		meeting.setMeetingStatus(Meeting.MeetingStatus.READY);
		meeting.setTopics(initTopics(meeting.getTopics()));

		user.create(meeting);
		userService.saveOne(user);

		return meeting;
	}

	public void join(Meeting meeting, User user) {
		if (!Meeting.MeetingStatus.PUBLISHED.equals(meeting.getMeetingStatus())) {
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "참여 불가능한 모입니다.");
		}

		if (repository.isJoinMeeting(meeting.getId(), user.getId())) {
			throw new HttpClientErrorException(HttpStatus.CONFLICT, "이미 참여한 모임입니다.");
		}

		user.attendTo(meeting);

		userService.saveOne(user);
	}

	public void leave(long meetingId, long userId) {
		attendMeetingRepository.delete(repository.getAttendMeetingFromUserIdAndMeetingId(userId, meetingId).orElseThrow(NullPointerException::new));
	}

	/**
	 * 모임 생성자만 모임 상태 변경 가능
	 * @param meeting
	 * @param meetingStatus
	 * @param ownerId
	 */
	public void changeStatus(Meeting meeting, Meeting.MeetingStatus meetingStatus, Long ownerId) {
		repository.getMakeMeetingFromUserIdAndMeetingId(ownerId, meeting.getId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "변경 가능한 모임이 없거나 모임 생성자가 아닙니다."));

		meeting.setMeetingStatus(meetingStatus);
		repository.save(meeting);
	}

	public Optional<Meeting> findById(long meetingId) {
		return repository.findById(meetingId);
	}

	@Transactional
	public Meeting update(Meeting target, long originId, Long ownerId) {
		repository.getMakeMeetingFromUserIdAndMeetingId(ownerId, originId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "변경 가능한 모임이 없거나 모임 생성자가 아닙니다."));
		target.setId(originId);
		target.setTopics(initTopics(target.getTopics()));

		return repository.save(target);
	}

	@Transactional
	public void save(Meeting meeting) {
		repository.save(meeting);
	}

	private List<Topic> initTopics(List<Topic> source) {
		List<Topic> topics = new ArrayList<>();
		for (Topic topic : source) {
			topicService.findById(topic.getId()).ifPresent(target -> topics.add(target));
		}
		return topics;
	}
}
