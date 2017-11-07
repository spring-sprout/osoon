package io.osoon.data.repository;

import java.util.Optional;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.osoon.data.domain.AttendMeeting;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-12
 */
@Repository
public interface AttendMeetingRepository extends PagingAndSortingRepository<AttendMeeting, Long> {

	@Query("MATCH ()-[r:ATTEND]-(m:Meeting) WHERE ID(m) = {0} RETURN COUNT (r)")
	long countByMeetingId(long meeting);

	Optional<AttendMeeting> findByMeetingId(Long id);

	long countByMeetingTitle(String title);
}
