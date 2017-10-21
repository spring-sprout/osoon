package io.osoon.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.time.LocalDateTime;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class Meeting {
	@GraphId Long id;
	String title;
	String contents;
	String titleImage;
	int maxAttendees;

	LocalDateTime meetAt;
	/** 모임 주소 **/
	String locationAddr;
	/** 모임 별칭 **/
	String locationName;
	double latitude;
	double longitude;

	LocalDateTime attendStartAt;
	LocalDateTime attendEndAt;

	MeetingStatus meetingStatus = MeetingStatus.DURING;

	public static Meeting of(String title, String contents) {
		Meeting meeting = new Meeting();
		meeting.title = title;
		meeting.contents = contents;
		meeting.meetingStatus = MeetingStatus.DURING;
		return meeting;
	}

	public enum MeetingStatus {
		READY, DURING, CLOSURE, END
	}
}
