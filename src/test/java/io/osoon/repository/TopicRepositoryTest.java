package io.osoon.repository;

import io.osoon.repository.queryresult.TopicView;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-27
 */
public class TopicRepositoryTest extends RepositoryTest{

	@Test
	public void findAllWithCount() {
		Page<TopicView> allWithCount = topicRepository.findAllWithCount(PageRequest.of(0, 10));
		allWithCount.forEach(System.out::println);
	}
}