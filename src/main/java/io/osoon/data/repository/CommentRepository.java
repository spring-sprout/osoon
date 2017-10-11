package io.osoon.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import io.osoon.data.domain.Comment;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-19
 */
@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
	@Query(value = "MATCH (u:User)-[r1:WRITE]-(c:Comment)-[r2:BELONG_TO]->(m:Meeting) WHERE id(m)={0} RETURN c, r1, r2, m, u"
		, countQuery = "MATCH (c:Comment)-[:BELONG_TO]->(m:Meeting) WHERE id(m)={0} RETURN count(*)")
	Page<Comment> getCommentsBelongTo(long meetingId, Pageable page);
}
