package io.osoon.data.repository;

import io.osoon.data.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	@Query(value = "MATCH (user:User)-[:ATTEND]->(meeting:Meeting) WHERE id(meeting)={0} RETURN user"
		, countQuery = "MATCH (user:User)-[:ATTEND]->(meeting:Meeting) WHERE id(meeting)={0} RETURN count(*)")
	Page<User> getUsersThatJoined(long meetingId, Pageable page);

	@Query("MATCH (user:User)-[:ATTEND]->(meeting:Meeting) WHERE id(meeting)={0} and id(user)={1} RETURN count(meeting) > 0 as c")
	boolean isJoinMeeting(long meetingId, long userId);

	@Query(value = "MATCH (comment:Comment)-[:BELONG_TO]->(meeting:Meeting) WHERE id(meeting)={0} RETURN comment, meeting"
		, countQuery = "MATCH (comment:Comment)-[:BELONG_TO]->(meeting:Meeting) WHERE id(meeting)={0} RETURN count(*)")
	Page<Comment> getCommentsBelongTo(long meetingId, Pageable page);

	@Query("MATCH (n:User)-[r:MAKE]-(m:Meeting) WHERE id(n) = {0} AND id(m) = {1} RETURN n,r,m")
	Optional<MakeMeeting> getMakeMeetingFromUserIdAndMeetingId(long userId, long meetingId);

	@Query("MATCH (n:User)-[r:ATTEND]-(m:Meeting) WHERE id(n) = {0} AND id(m) = {1} RETURN n,r,m")
	Optional<AttendMeeting> getAttendMeetingFromUserIdAndMeetingId(long userId, long meetingId);
}
