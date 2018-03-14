package io.osoon.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

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
@Entity
public class AttendMeeting {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnore
	@ManyToOne
	private User user;

	@JsonIgnore
	@ManyToOne
	private Meeting meeting;

	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime at;

	@Enumerated(EnumType.STRING)
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
