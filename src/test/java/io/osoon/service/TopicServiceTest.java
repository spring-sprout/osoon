package io.osoon.service;

import io.osoon.domain.Topic;
import io.osoon.repository.queryresult.TopicView;
import io.osoon.repository.TopicRepository;

import org.junit.Before;
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

	Topic topic1 = new Topic("Java");
	Topic topic2 = new Topic("spring");
	Topic topic3 = new Topic("spring mvc");
	Topic topic4 = new Topic("Java 8");
	Topic topic5 = new Topic("spring boot");

	@Before
	public void before() {
		service.findByName(topic1.getName()).ifPresent(topic -> repository.delete(topic));
		service.findByName(topic2.getName()).ifPresent(topic -> repository.delete(topic));
		service.findByName(topic3.getName()).ifPresent(topic -> repository.delete(topic));
		service.findByName(topic4.getName()).ifPresent(topic -> repository.delete(topic));
		service.findByName(topic5.getName()).ifPresent(topic -> repository.delete(topic));

		createTopics();
	}

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

		assertEquals(topics.getTotalElements(), 3);
		topics.forEach(System.out::println);
	}


	public void createTopics() {
		service.create(topic1.getName());
		service.create(topic2.getName());
		service.create(topic3.getName());
		service.create(topic4.getName());
		service.create(topic5.getName());
	}
}
