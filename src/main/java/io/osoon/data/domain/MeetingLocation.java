package io.osoon.data.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-10-24
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class MeetingLocation {

	@Id @GeneratedValue Long id;

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
	@Relationship(type = "CREATED_BY")
	User user;

	public static MeetingLocation of(String name, User user) {
		MeetingLocation meetingLocation = new MeetingLocation();
		meetingLocation.name = name;
		meetingLocation.user = user;
		return meetingLocation;
	}

}
