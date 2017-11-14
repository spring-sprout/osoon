package io.osoon.data.domain;

import java.time.LocalDateTime;

import org.neo4j.ogm.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-20
 */
@Setter @Getter
@NoArgsConstructor
@RelationshipEntity(type = "ATTEND")
public class AttendMeeting {

	@Id @GeneratedValue private Long id;

	@JsonIgnore
	@StartNode private User user;

	@JsonIgnore
	@EndNode private Meeting meeting;

	private LocalDateTime at;

	private AttendStatus status = AttendStatus.ENROLLED;

	public static AttendMeeting of(User user, Meeting meeting, AttendStatus attendStatus) {
		AttendMeeting attendMeeting = new AttendMeeting();
		attendMeeting.user = user;
		attendMeeting.meeting = meeting;
		attendMeeting.at = LocalDateTime.now();
		attendMeeting.status = attendStatus;
		return attendMeeting;
	}

	public enum AttendStatus {
		ENROLLED, CONFIRM, HOLD
	}

}
