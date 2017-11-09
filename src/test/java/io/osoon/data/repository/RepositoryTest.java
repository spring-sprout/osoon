package io.osoon.data.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class RepositoryTest {

    @Autowired protected MeetingRepository meetingRepository;
    @Autowired protected UserRepository userRepository;
    @Autowired protected TopicRepository topicRepository;
    @Autowired protected AttendMeetingRepository attendMeetingRepository;
    @Autowired protected MeetingLocationRepository meetingLocationRepository;

    @Before
    public void before() {
        clean();
    }

    @After
    public void after() {
        clean();
    }

    private void clean() {
        attendMeetingRepository.deleteAll();
        meetingLocationRepository.deleteAll();
        topicRepository.deleteAll();
        meetingRepository.deleteAll();
        userRepository.deleteAll();
    }

}
