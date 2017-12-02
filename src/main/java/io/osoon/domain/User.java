package io.osoon.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
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
	Set<Meeting> ownMeetings = new HashSet<>();

	@Relationship(type = "ATTEND")
	Set<AttendMeeting> attendMeetings = new HashSet<>();

	public static User of(String email, String name) {
		User user = new User();
		user.email = email;
		user.name = name;
		user.nickname = name;
		user.password = email;

		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		user.joinedAt = Date.from(utc.toInstant());

		return user;
	}

	public Meeting create(Meeting meeting) {
		ownMeetings.add(meeting);
		return meeting;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User user = (User)o;
		return Objects.equals(id, user.id);
	}

	@Override public int hashCode() {
		return Objects.hash(id);
	}
}
