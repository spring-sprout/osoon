package io.osoon.service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
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

import io.osoon.helper.BaseDataTestHelper;
import io.osoon.domain.Meeting;
import io.osoon.repository.MeetingRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingAttendServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(MeetingAttendServiceTest.class);

    @Autowired MeetingService meetingService;
    @Autowired MeetingAttendService meetingAttendService;
	@Autowired MeetingRepository repository;

	@Autowired
    BaseDataTestHelper baseDataTestHelper;

	Meeting user1Meeting;
	Long user1MeetingId;

	@Before
	public void before() {
        baseDataTestHelper.createUsers();

		Meeting newMeeting = Meeting.of("테스트 미팅", "테스트 컨텐츠");
		newMeeting.setMaxAttendees(10);

		user1Meeting = meetingService.create(baseDataTestHelper.getUser1(), newMeeting);
		user1MeetingId = user1Meeting.getId();
	}

	@After
	public void after() {
		repository.deleteById(user1MeetingId);
	}


    @Test(expected = HttpClientErrorException.class)
	@Transactional
    public void attendFailCauseNotPublished() {
		meetingAttendService.attend(meetingService.findById(user1MeetingId).get(), baseDataTestHelper.getUser2());
	}

	@Test(expected = HttpClientErrorException.class)
	@Transactional
	public void attendFailCauseRegistNotOpen() {
		Meeting meeting = meetingService.findById(user1MeetingId).get();
		meeting.setMeetingStatus(Meeting.MeetingStatus.PUBLISHED);
		Date minPlusOpenTime = DateUtils.addMinutes(Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()), 10);
		meeting.setRegistOpenAt(minPlusOpenTime);

		meetingAttendService.attend(meeting, baseDataTestHelper.getUser2());
	}

	@Test(expected = HttpClientErrorException.class)
	@Transactional
	public void attendFailCauseRegistClose() {
		Meeting meeting = meetingService.findById(user1MeetingId).get();
		meeting.setMeetingStatus(Meeting.MeetingStatus.PUBLISHED);
		Date minPlusOpenTime = DateUtils.addMinutes(Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()), -10);
		meeting.setRegistCloseAt(minPlusOpenTime);

		meetingAttendService.attend(meeting, baseDataTestHelper.getUser2());
	}

	@Test
	@Transactional
	public void attendSuccess() {
		Meeting meeting = meetingService.findById(user1MeetingId).get();
		meeting.setMeetingStatus(Meeting.MeetingStatus.PUBLISHED);
		// setting regist open/close time
		meeting.setRegistOpenAt(DateUtils.addMinutes(Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()), -10));
		meeting.setRegistCloseAt(DateUtils.addMinutes(Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant()), 10));

		meetingAttendService.attend(meeting, baseDataTestHelper.getUser2());

		assertTrue(meeting.isAttendBy(baseDataTestHelper.getUser2()));
	}

	@Test(expected = HttpClientErrorException.class)
	public void leaveFail() {
		meetingAttendService.attendCancel(user1Meeting, baseDataTestHelper.getUser2());
	}

	@Test
	@Transactional
	public void leave() {
		attendSuccess();
		meetingAttendService.attendCancel(user1Meeting, baseDataTestHelper.getUser2());
	}

}
