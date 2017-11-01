package io.osoon.service;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.osoon.data.domain.*;
import io.osoon.data.repository.AttendMeetingRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceTest.class);

    @Autowired MeetingService service;
    @Autowired TopicService topicService;
    @Autowired UserService userService;

    @Test
    public void updateMeeting() {
        long originMeetingId = 104l;
        Meeting target = Meeting.of(LocalDateTime.now().toString(), "Spring 5에 대해 알아 봅시다");
        target.setMaxAttendees(6);
        target.setMeetingStatus(Meeting.MeetingStatus.PUBLISHED);
        target.setOnlineType(Meeting.OnlineType.DISCORD);
        List<Topic> topics = new ArrayList<>();
        //topics.add(topicService.findByName("java").get());
        //topics.add(topicService.findByName("spring boot").get());
        topics.add(topicService.findByName("spring").get());

        target.setTopics(topics);
        service.update(target, originMeetingId, userService.findByEmail("dosajun@gmail.com").get().getId());
    }

}