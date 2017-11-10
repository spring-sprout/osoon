package io.osoon.data.repository;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.SessionFactory;
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
    @Autowired protected SessionFactory sessionFactory;

    @Before
    public void before() {
        clean();
    }

    @After
    public void after() {
        clean();
    }

    private void clean() {
        // Remove all relation first. Node can not remove when relation exist between nodes.
        sessionFactory.openSession().query("MATCH (a)-[r]-(b) DELETE r", new HashMap<>());
        sessionFactory.openSession().query("MATCH (a) DELETE a", new HashMap<>());
    }

}
