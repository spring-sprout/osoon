package io.osoon.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @author 백기선 (whiteship2000@gmail.com)
 * @since 2017-10-24
 */
@Entity
@Setter @Getter @ToString
@NoArgsConstructor
public class MeetingLocation {

	@Id
	@GeneratedValue
	Long id;

	/**
	 * 주소
	 */
	String addr;

	/**
	 * 장소 이름
	 */
	String name;

	/**
	 * 위도
	 */
	double latitude;

	/**
	 * 경도
	 */
	double longitude;

	/**
	 * 장소 등록한 사용자
	 */
	@ManyToOne
	User user;

	public static MeetingLocation of(String name, User user) {
		MeetingLocation meetingLocation = new MeetingLocation();
		meetingLocation.name = name;
		meetingLocation.user = user;
		return meetingLocation;
	}

}
