package io.osoon.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDateTime;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-29
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class Comment {
	@GraphId Long id;
	@Relationship(type = "WRITE")
	private User user;
	@Relationship(type = "BELONG_TO")
	private Meeting meeting;

	private String contents;
	private LocalDateTime writeAt;

	public static Comment of(User user, Meeting meeting, String contents) {
		Comment comment = new Comment();
		comment.user = user;
		comment.meeting = meeting;
		comment.contents = contents;
		comment.writeAt = LocalDateTime.now();
		return comment;

	}
}
