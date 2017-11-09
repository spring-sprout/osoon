package io.osoon.data.repository;

import io.osoon.data.domain.AttendMeeting;
import io.osoon.data.domain.Meeting;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-11-06
 */
public class AttendMeetingRepositoryTest extends RepositoryTest {
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
		Meeting meeting = meetingRepository.findById(101L).get();

		long count = repository.countByMeetingId(101L);
		logger.info("Result count is : " + count);
	}

	@Test
	public void byId() {
		long count = repository.countByMeetingTitle("Java 9 review");
		logger.info("Result is : " + count);
	}
}