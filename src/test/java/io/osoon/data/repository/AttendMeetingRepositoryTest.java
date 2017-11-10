package io.osoon.data.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-11-06
 */
public class AttendMeetingRepositoryTest extends RepositoryTest {
	private static final Logger logger = LoggerFactory.getLogger(AttendMeetingRepositoryTest.class);

	@Autowired AttendMeetingRepository repository;
	@Autowired MeetingRepository meetingRepository;


}