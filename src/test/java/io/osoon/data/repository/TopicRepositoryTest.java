package io.osoon.data.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import io.osoon.data.domain.Topic;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-10-27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicRepositoryTest {
	@Autowired TopicRepository repository;

	@Test
	public void test() {
		Page<Topic> topics = repository.findByNameStartingWith("S", PageRequest.of(0, 10));
		topics.forEach(System.out::println);
	}
}