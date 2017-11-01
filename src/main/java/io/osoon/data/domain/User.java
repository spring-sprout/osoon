package io.osoon.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.neo4j.ogm.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@NodeEntity
@Setter @Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    private Long id;

    @Index(unique = true)
	private String email;

	private String name;

	private String password;

	@Index(unique = true)
	private String nickname;

	private String imageUrl;

    private Date joinedAt;

	@Relationship(type = "MAKE")
	Set<MakeMeeting> ownMeetings = new HashSet<>();

	@Relationship(type = "ATTEND")
	Set<AttendMeeting> attendMeetings = new HashSet<>();

	public static User of(String email, String name) {
		User user = new User();
		user.email = email;
		user.name = name;

		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		user.joinedAt = Date.from(utc.toInstant());

		return user;
	}

	public AttendMeeting attendTo(Meeting meeting) {
		AttendMeeting.AttendStatus attendStatus = AttendMeeting.AttendStatus.READY;
		if (meeting.isAutoConfirm()) {
			attendStatus = AttendMeeting.AttendStatus.CONFIRM;
		}

		AttendMeeting attendMeeting = AttendMeeting.of(this, meeting, attendStatus);
		attendMeetings.add(attendMeeting);
		return attendMeeting;
	}

	public MakeMeeting create(Meeting meeting) {
		MakeMeeting makeMeeting = new MakeMeeting(this, meeting);
		ownMeetings.add(makeMeeting);
		return makeMeeting;
	}
}
