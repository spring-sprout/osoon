package io.osoon.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.id.UuidStrategy;

import java.time.LocalDateTime;

/**
 * @TODO 특별한 기능 넣지 않을 꺼면 삭제 예정.
 *
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-20
 */
@Setter @Getter @ToString
@NoArgsConstructor
@RelationshipEntity(type = "MAKE")
public class MakeMeeting {

	@Id @GeneratedValue
	private Long relationshipId;

	@JsonIgnore
	@StartNode
	private User user;

	@EndNode
	private Meeting meeting;

	@Property
	private LocalDateTime at;

	public MakeMeeting(User user, Meeting meeting) {
		this.user = user;
		this.meeting = meeting;
		this.at = LocalDateTime.now();
	}
}
