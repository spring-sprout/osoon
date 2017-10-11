package io.osoon.data.domain;

import java.time.LocalDateTime;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-20
 */
@Setter @Getter @ToString
@NoArgsConstructor
@RelationshipEntity(type = "ATTEND")
public class AttendMeeting {
	@GraphId private Long relationshipId;
	@JsonIgnore
	@StartNode private User user;
	@EndNode private Meeting meeting;
	private LocalDateTime at;

	public AttendMeeting(User user, Meeting meeting) {
		this.user = user;
		this.meeting = meeting;
		this.at = LocalDateTime.now();
	}
}
