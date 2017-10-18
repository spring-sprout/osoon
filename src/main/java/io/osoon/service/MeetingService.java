package io.osoon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.AttendMeetingRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-21
 */
@Service
public class MeetingService {
	private Logger logger = LoggerFactory.getLogger(MeetingService.class);

	@Autowired
	private MeetingRepository repository;
	@Autowired
	private AttendMeetingRepository attendMeetingRepository;
	@Autowired
	private UserRepository userRepository;

	public void join(Meeting meeting, User user) {
		if (!Meeting.MeetingStatus.DURING.equals(meeting.getMeetingStatus())) {
			logger.info("참여할 수 없는 모임입니다.");
			return;
		}

		if (repository.isJoinMeeting(meeting.getId(), user.getId())) {
			logger.info("이미 참여한 모임 입니다.");
			return;
		}

		logger.info("참여 가능");
		user.attendTo(meeting);
		userRepository.save(user);
	}

	public void leave(long meetingId, long userId) {
		attendMeetingRepository.delete(repository.getAttendMeetingFromUserIdAndMeetingId(userId, meetingId).orElseThrow(NullPointerException::new));
	}

	/**
	 * 모임 생성자만 모임 상태 변경 가능
	 * @param meeting
	 * @param meetingStatus
	 * @param userId
	 */
	public void changeStatus(Meeting meeting, Meeting.MeetingStatus meetingStatus, Long userId) {
		repository.getMakeMeetingFromUserIdAndMeetingId(userId, meeting.getId()).orElseThrow(NullPointerException::new);

		meeting.setMeetingStatus(meetingStatus);
		repository.save(meeting);
	}
}
