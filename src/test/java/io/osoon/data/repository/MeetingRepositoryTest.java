package io.osoon.data.repository;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	public void findByTitle() {
		Page<Meeting> list = repository.findByTitleContains("J", PageRequest.of(0, 10, Sort.Direction.DESC, "m.createdAt"));
		list.forEach(System.out::println);
	}
}
