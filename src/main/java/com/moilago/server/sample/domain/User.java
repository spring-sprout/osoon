package com.moilago.server.sample.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 김제준 (reperion.kim@navercorp.com)
 * @since 2017-09-18
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class User {
	@GraphId Long id;
	private String email;
	private String name;
	private String password;
	private String nickname;

	private LocalDateTime time;

	@Relationship(type = "MAKE")
	Set<MakeMeeting> ownMeetings = new HashSet<>();

	@Relationship(type = "ATTEND")
	Set<AttendMeeting> attendMeetings = new HashSet<>();

	public User(String email, String name) {
		this.email = email;
		this.name = name;
		time = LocalDateTime.now();
	}

	public AttendMeeting attendTo(Meeting meeting) {
		AttendMeeting attendMeeting = new AttendMeeting(this, meeting);
		attendMeetings.add(attendMeeting);
		return attendMeeting;
	}

	public void leaveMoim(Meeting meeting) {
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
