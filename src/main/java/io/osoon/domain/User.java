package io.osoon.domain;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @author 백기선 (whiteship2000@gmail.com)
 * @since 2017-09-18
 */
@Entity
@Setter @Getter
@NoArgsConstructor
public class User {

    @Id
	@GeneratedValue
    private Long id;

    @Column(unique = true)
	private String email;

	private String name;

	private String password;

	@Column(unique = true)
	private String nickname;

	private String imageUrl;

	@Temporal(TemporalType.TIMESTAMP)
    private Date joinedAt;

	@OneToMany
	Set<Meeting> ownMeetings = new HashSet<>();

	@OneToMany
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
