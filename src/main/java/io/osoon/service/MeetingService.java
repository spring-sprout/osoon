package io.osoon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
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
	private UserRepository userRepository;

	public void join(Meeting meeting, User user) {
		if (repository.isJoinMeeting(meeting.getId(), user.getId())) {
			logger.info("이미 참여한 모임 입니다.");
		} else {
			logger.info("참여 가능");
			user.attendTo(meeting);

			userRepository.save(user);
		}
	}
}
