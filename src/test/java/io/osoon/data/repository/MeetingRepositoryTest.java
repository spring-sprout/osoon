package io.osoon.data.repository;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import io.osoon.data.domain.Meeting;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingRepositoryTest {
	private static final Logger logger = LoggerFactory.getLogger(MeetingRepositoryTest.class);
	@Autowired private MeetingRepository repository;

    @Before
    public void before() {
        repository.deleteAll();
    }

    @Test
    public void findById() {
        Meeting meeting = repository.save(Meeting.of("title", "content"));
        Optional<Meeting> byId = repository.findById(meeting.getId());
        assertThat(byId.get().getId()).isNotNull();
    }

    @Test
    public void findById_exists() {
		Optional<Meeting> byId = repository.findById(160L);
		assertThat(byId.get().getId()).isNotNull();
		logger.info(byId.toString());
	}

	@Test
	public void findByTitle() {
		Page<Meeting> list = repository.findByTitleContainsAndMeetingStatus("테", Meeting.MeetingStatus.PUBLISHED, PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt"));
		list.forEach(System.out::println);
	}
}
