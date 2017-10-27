package io.osoon.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.osoon.data.domain.Topic;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-10-27
 */
@Repository
public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {
	Page<Topic> findByNameStartingWith(String name, Pageable pageable);
}
