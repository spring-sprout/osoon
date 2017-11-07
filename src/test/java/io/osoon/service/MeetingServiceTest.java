package io.osoon.service;

import java.util.ArrayList;
import java.util.List;

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

import io.osoon.data.domain.*;
import io.osoon.data.repository.MeetingRepository;

import static org.junit.Assert.assertEquals;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceTest.class);

    @Autowired MeetingService service;
	@Autowired MeetingRepository repository;
    @Autowired TopicService topicService;
    @Autowired UserService userService;

	User user1;
	User user2;

	Meeting user1Meeting;
	Long user1MeetingId;

	@Before
	public void before() {
		user1 = userService.findByEmail("dosajun@gmail.com").get();
		user2 = userService.findByEmail("Keesun.baik@gmail.com").get();

		user1Meeting = service.create(user1, Meeting.of("테스트 미팅", "테스트 컨텐츠"));
		user1MeetingId = user1Meeting.getId();
	}

	@After
	public void after() {
		repository.deleteById(user1MeetingId);
	}

	@Test
	@Transactional
    public void makeMeeting() {
		Meeting meeting = service.create(user1, Meeting.of("테스트 미팅", "테스트 컨텐츠"));

		Meeting savedMeeting = service.findById(meeting.getId()).get();

		assertEquals(meeting.getTitle(), savedMeeting.getTitle());
	}

    @Test
	@Transactional
    public void updateMeeting() {
        Meeting target = Meeting.of("테스트 업데이트", "Spring 5에 대해 알아 봅시다");
        target.setMaxAttendees(6);
        target.setMeetingStatus(Meeting.MeetingStatus.PUBLISHED);
        target.setOnlineType(Meeting.OnlineType.DISCORD);
        List<Topic> topics = new ArrayList<>();
        //topics.add(topicService.findByName("java").get());
        //topics.add(topicService.findByName("spring boot").get());
        topics.add(topicService.findByName("java").get());

        target.setTopics(topics);
        service.update(target, user1MeetingId, user1.getId());
    }

    @Test(expected = HttpClientErrorException.class)
	@Transactional
    public void attendFailCauseNotPublished() {
		service.attend(service.findById(user1MeetingId).get(), user2);
	}

	@Test
	@Transactional
	public void attendSuccess() {
		//service.changeStatus(user1Meeting, Meeting.MeetingStatus.PUBLISHED, user1.getId());

		service.attend(service.findById(81).get(), userService.findByEmail("outsideris@gmail.com").get());
	}

	@Test(expected = HttpClientErrorException.class)
	public void leaveFail() {
		service.leave(user1MeetingId, user2.getId());
	}

	@Test
	@Transactional
	public void leave() {
		attendSuccess();

		service.leave(user1MeetingId, user2.getId());
	}

	@Test
	@Transactional
	public void changeMeetingStatus() {
		Meeting meeting = service.findById(user1MeetingId).get();
		service.changeStatus(meeting, Meeting.MeetingStatus.PUBLISHED, user1.getId());

		Meeting updatedMeeting = service.findById(user1MeetingId).get();

		assertEquals(meeting.getMeetingStatus(), updatedMeeting.getMeetingStatus());
	}
}
