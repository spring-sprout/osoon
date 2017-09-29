package com.moilago.server.sample.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.moilago.server.sample.domain.Comment;
import com.moilago.server.sample.domain.Meeting;
import com.moilago.server.sample.domain.User;

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
}
