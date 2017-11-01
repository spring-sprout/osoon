package io.osoon.data.repository;

import io.osoon.data.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-19
 */
@Repository
public interface MeetingRepository extends PagingAndSortingRepository<Meeting, Long> {
	@Query(value = "MATCH (u:User)-[:ATTEND]->(m:Meeting) WHERE id(m)={0} RETURN u"
		, countQuery = "MATCH (u:User)-[:ATTEND]->(m:Meeting) WHERE id(m)={0} RETURN count(*)")
	Page<User> getUsersThatJoined(long meetingId, Pageable page);

	@Query("MATCH (u:User)-[:ATTEND]->(m:Meeting) WHERE id(m)={0} and id(u)={1} RETURN count(m) > 0 as c")
	boolean isJoinMeeting(long meetingId, long userId);

	@Query("MATCH (u:User)-[:MAKE]->(m:Meeting) WHERE id(m)={0} and id(u)={1} RETURN count(m) > 0 as c")
	boolean isOwner(long meetingId, long userId);

	@Query("MATCH (u:User)-[r:ATTEND]-(m:Meeting) WHERE id(u) = {0} AND id(m) = {1} RETURN u,r,m")
	Optional<AttendMeeting> getAttendMeetingFromUserIdAndMeetingId(long userId, long meetingId);

}
