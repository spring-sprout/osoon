package io.osoon.data.repository;

import io.osoon.data.domain.Topic;
import io.osoon.data.domain.queryresult.TopicView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-27
 */
@Repository
public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {
	Optional<Topic> findByName(String name);

	@Query(value = "MATCH (n:Topic) WHERE n.name STARTS WITH {0} "
		+ "OPTIONAL MATCH (n)-[r:IS_ABOUT]-() WHERE n.name STARTS WITH {0} RETURN n AS topic, count(r) AS count ORDER BY count desc",
		countQuery = "MATCH (n:Topic) WHERE n.name STARTS WITH {0} RETURN count(n)")
	Page<TopicView> findByNameStartingWith(String name, Pageable pageable);

	@Query(value = "MATCH (n:Topic) OPTIONAL MATCH (n)-[r:IS_ABOUT]-() RETURN n AS topic, count(r) AS count",
		countQuery = "MATCH (n:Topic) RETURN count(n)")
	Page<TopicView> findAllWithCount(Pageable pageable);

	List<Topic> findAll();
}
