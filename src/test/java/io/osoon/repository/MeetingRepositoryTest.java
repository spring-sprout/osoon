package io.osoon.repository;

import io.osoon.domain.Meeting;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author whiteship
 */
public class MeetingRepositoryTest extends RepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(MeetingRepositoryTest.class);

    @Test
    public void findById() {
        Meeting meeting = meetingRepository.save(Meeting.of("title", "content"));
        Optional<Meeting> byId = meetingRepository.findById(meeting.getId());
        assertThat(byId.get().getId()).isNotNull();
    }

    @Test
    public void findById_not_exists() {
		Optional<Meeting> byId = meetingRepository.findById(160L);
		assertThat(byId.isPresent()).isFalse();
	}

	@Test
	public void findByTitle() {
		Page<Meeting> list = meetingRepository.findByTitleContainsAndMeetingStatus("테", Meeting.MeetingStatus.PUBLISHED, PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt"));
		list.forEach(System.out::println);
	}
}
