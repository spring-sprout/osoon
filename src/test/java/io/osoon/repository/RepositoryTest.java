package io.osoon.repository;

import io.osoon.helper.DatabaseTestHelper;
import io.osoon.repository.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Ignore
public class RepositoryTest {

    @Autowired protected MeetingRepository meetingRepository;
    @Autowired protected UserRepository userRepository;
    @Autowired protected TopicRepository topicRepository;
    @Autowired protected AttendMeetingRepository attendMeetingRepository;
    @Autowired protected MeetingLocationRepository meetingLocationRepository;

    @Autowired private DatabaseTestHelper databaseTestHelper;

    @Before
    public void before() {
        databaseTestHelper.removeAllData();
    }

    @After
    public void after() {
        databaseTestHelper.removeAllData();
    }

}
