package io.osoon.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalDateTime;
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
	@GraphId Long id;
	@Index(unique = true)
	private String email;
	private String name;
	private String password;
	@Index(unique = true)
	private String nickname;
	private String imageUrl;

	private LocalDateTime time;

	@Relationship(type = "MAKE")
	Set<MakeMeeting> ownMeetings = new HashSet<>();

	@Relationship(type = "ATTEND")
	Set<AttendMeeting> attendMeetings = new HashSet<>();

	public static User of(String email, String name) {
		User user = new User();
		user.email = email;
		user.name = name;
		user.time = LocalDateTime.now();

		return user;
	}

	public AttendMeeting attendTo(Meeting meeting, AttendMeeting.AttendStatus attendStatus) {
		AttendMeeting attendMeeting = AttendMeeting.of(this, meeting, attendStatus);
		attendMeetings.add(attendMeeting);
		return attendMeeting;
	}

	public void leaveMoim(AttendMeeting meeting) {
		attendMeetings.remove(meeting);
	}

	public MakeMeeting make(Meeting meeting) {
		MakeMeeting makeMeeting = new MakeMeeting(this, meeting);
		ownMeetings.add(makeMeeting);
		return makeMeeting;
	}

	public void deleteMeeting(Meeting meeting) {
		ownMeetings.remove(meeting);
	}
}
