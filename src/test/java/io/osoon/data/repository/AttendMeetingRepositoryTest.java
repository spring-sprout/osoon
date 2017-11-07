package io.osoon.data.repository;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.osoon.data.domain.AttendMeeting;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-11-06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttendMeetingRepositoryTest {
	private static final Logger logger = LoggerFactory.getLogger(AttendMeetingRepositoryTest.class);

	@Autowired AttendMeetingRepository repository;
	@Autowired MeetingRepository meetingRepository;

	@Test
	public void test() {
		Optional<AttendMeeting> count = repository.findByMeetingId(meetingRepository.findById(101L).get().getId());
		logger.info("Result count is : " + count);
	}

	@Test
	public void test2() {
		long count = repository.countByMeetingId(101L);
		logger.info("Result count is : " + count);
	}

	@Test
	public void byId() {
		long count = repository.countByMeetingTitle("Java 9 review");
		logger.info("Result is : " + count);
	}
}