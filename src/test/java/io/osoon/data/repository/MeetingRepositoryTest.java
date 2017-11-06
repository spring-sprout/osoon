package io.osoon.data.repository;

import io.osoon.data.domain.Meeting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingRepositoryTest {

    @Autowired private MeetingRepository meetingRepository;

    @Before
    public void before() {
        meetingRepository.deleteAll();
    }

    @Test
    public void findById() {
        Meeting meeting = meetingRepository.save(Meeting.of("title", "content"));
        Optional<Meeting> byId = meetingRepository.findById(meeting.getId());
        assertThat(byId.get().getId()).isNotNull();
    }

}