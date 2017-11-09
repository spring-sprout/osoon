package io.osoon.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import io.osoon.BaseData;
import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.service.meeting.MeetingAttendService;
import io.osoon.service.meeting.MeetingService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingAttendServiceTest extends BaseData {
    private static final Logger logger = LoggerFactory.getLogger(MeetingAttendServiceTest.class);

    @Autowired MeetingService service;
    @Autowired MeetingAttendService meetingAttendService;
	@Autowired MeetingRepository repository;

	Meeting user1Meeting;
	Long user1MeetingId;

	@Before
	public void before() {
		Meeting newMeeting = Meeting.of("테스트 미팅", "테스트 컨텐츠");
		newMeeting.setMaxAttendees(10);
		user1Meeting = service.create(user1, newMeeting);
		user1MeetingId = user1Meeting.getId();
	}

	@After
	public void after() {
		repository.deleteById(user1MeetingId);
	}


    @Test(expected = HttpClientErrorException.class)
	@Transactional
    public void attendFailCauseNotPublished() {
		meetingAttendService.attend(service.findById(user1MeetingId).get(), user2);
	}

	@Test
	@Transactional
	public void attendSuccess() {
		service.changeStatus(user1Meeting, Meeting.MeetingStatus.PUBLISHED, user1.getId());

		meetingAttendService.attend(user1Meeting, user2);

		assertTrue(user1Meeting.isAttendBy(user2));
	}

	@Test(expected = HttpClientErrorException.class)
	public void leaveFail() {
		meetingAttendService.attendCancel(user1MeetingId, user2.getId());
	}

	@Test
	@Transactional
	public void leave() {
		attendSuccess();

		meetingAttendService.attendCancel(user1MeetingId, user2.getId());
	}

}
