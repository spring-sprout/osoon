package io.osoon.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.osoon.data.domain.Topic;
import io.osoon.data.domain.queryresult.TopicView;
import io.osoon.data.repository.TopicRepository;

import static org.junit.Assert.assertEquals;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(MeetingServiceTest.class);

	@Autowired TopicService service;
	@Autowired TopicRepository repository;

	@Test
	@Transactional
	public void create() {
		String topicName = "spring mvc4";
		service.create(topicName);

		Topic topic = service.findByName(topicName).get();

		assertEquals(topicName, topic.getName());
	}

	@Test
	public void findByNameStartingWith() {
		Page<TopicView> topics = service.listByStartingName("S", PageRequest.of(0, 10));
		topics.forEach(System.out::println);
	}

}
