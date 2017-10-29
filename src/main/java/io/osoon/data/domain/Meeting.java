package io.osoon.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.id.UuidStrategy;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * @author 김제준 (dosajun@gmail.com)
 * @since 2017-09-18
 */
@NodeEntity
@Setter @Getter @ToString
@NoArgsConstructor
public class Meeting {

	@Id @GeneratedValue
    Long id;

	/**
	 * 모임명 필수값
	 */
	String title;

	/**
	 * 모임 설명
	 */
	String contents;

	/**
	 * 모임 이미지 URL
	 */
	String titleImage;

    /**
     * 온라인 / 오프라인 여부
     */
    MeetingOnOffType meetingOnOffType = MeetingOnOffType.OFFLINE;

    /**
	 * 최대 참가 가능 인원 수
	 */
	int maxAttendees;

	/**
	 * 모임 자동 승인 여부(자동 승인시 관리자 승인 필요 없음)
	 */
	boolean isAutoConfirm;

	/**
	 * 모임 장소 (오프라인 모임 일 경우에만)
	 */
	@Relationship(type = "MEET_AT")
	MeetingLocation location;

    /**
     * 온라인 모임 형태 (온라인 모임 인 경우에만)
     */
    OnlineType onlineType;

	/**
	 * 모임 시작 일시
	 */
    Date meetStartAt;

	/**
	 * 모임 종료 일시
	 */
    Date meetEndAt;

	/**
	 * 모임 만든 일시
	 */
	Date createdAt;

	/**
	 * 모임 수정 일시
	 */
	Date updatedAt;

	/**
	 * 모임 상태
	 */
	MeetingStatus meetingStatus = MeetingStatus.READY;

	/**
	 * 모임 관리자, 모임 최초 만든 사용자는 자동으로 들어가고, 추가로 관리자 추가할 수 있음. 그래서 List.
	 */
	@Relationship(type = "MANAGED_BY")
    SortedSet<User> admins = new TreeSet<>(Comparator.comparing(User::getName));

	/**
	 * 모임 주제
	 */
	@Relationship(type = "IS_ABOUT")
	List<Topic> topics = new ArrayList<>();

	public static Meeting of(String title, String contents) {
		Meeting meeting = new Meeting();
		meeting.title = title;
		meeting.contents = contents;
		meeting.meetingStatus = MeetingStatus.READY;

		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		meeting.createdAt = Date.from(utc.toInstant());
		meeting.updatedAt = Date.from(utc.toInstant());

		return meeting;
	}

    public void update() {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        this.updatedAt = Date.from(utc.toInstant());
    }

    public enum MeetingStatus {
		READY, PUBLISHED, STARTED, DONE
	}

	public enum MeetingOnOffType {
	    ONLINE, OFFLINE, BOTH
    }

    public enum OnlineType {
	    SLACK, HANGOUT, DISCORD
    }

}
