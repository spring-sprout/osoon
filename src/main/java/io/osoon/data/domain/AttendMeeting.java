package io.osoon.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.time.LocalDateTime;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-20
 */
@Setter @Getter @ToString
@NoArgsConstructor
@RelationshipEntity(type = "ATTEND")
public class AttendMeeting {
	@GraphId private Long id;
	@JsonIgnore
	@StartNode private User user;
	@EndNode private Meeting meeting;
	private LocalDateTime at;
	private AttendStatus status;

	public static AttendMeeting of(User user, Meeting meeting, AttendStatus attendStatus) {
		AttendMeeting attendMeeting = new AttendMeeting();
		attendMeeting.user = user;
		attendMeeting.meeting = meeting;
		attendMeeting.at = LocalDateTime.now();
		attendMeeting.status = AttendStatus.READY;
		attendMeeting.status = attendStatus;
		return attendMeeting;
	}

	public enum AttendStatus {
		READY, CONFIRM, HOLD
	}
}
