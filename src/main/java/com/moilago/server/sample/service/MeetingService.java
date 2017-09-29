package com.moilago.server.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moilago.server.sample.domain.Meeting;
import com.moilago.server.sample.domain.User;
import com.moilago.server.sample.repository.MeetingRepository;
import com.moilago.server.sample.repository.UserRepository;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
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
