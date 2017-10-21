package io.osoon.service;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.osoon.data.domain.AttendMeeting;
import io.osoon.data.domain.MakeMeeting;
import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.AttendMeetingRepository;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserRepository;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-10-18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceTest.class);

    @Autowired MeetingService service;
    @Autowired UserRepository userRepository;
    @Autowired MeetingRepository repository;

    @Test
    public void test() {
        User user = userRepository.findById(4L).get();
        Meeting meeting = repository.findById(35L).get();

        Optional<MakeMeeting> exist = repository.getMakeMeetingFromUserIdAndMeetingId(user.getId(), meeting.getId());

        logger.info("is exist? " + exist);
    }

}