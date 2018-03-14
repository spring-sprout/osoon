package io.osoon.domain;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
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
public class Meeting {

	@Id
	@GeneratedValue
    Long id;

	/**
	 * 모임명 필수값
	 */
	String title;

	/**
	 * 모임 설명
	 */
	@Lob @Basic(fetch = FetchType.LAZY)
	String contents;

	/**
	 * 모임 이미지 URL
	 */
	String coverImage;

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
	@ManyToOne
	MeetingLocation location;

    /**
     * 온라인 모임 형태 (온라인 모임 인 경우에만)
     */
    @Enumerated(EnumType.STRING)
    OnlineType onlineType;

	/**
	 * 모임 시작 일시
	 */
	@Temporal(TemporalType.TIMESTAMP)
    Date meetStartAt;

	/**
	 * 모임 종료 일시
	 */
	@Temporal(TemporalType.TIMESTAMP)
    Date meetEndAt;

	/**
	 * 모임 만든 일시
	 */
	@Temporal(TemporalType.TIMESTAMP)
	Date createdAt;

	/**
	 * 모임 수정 일시
	 */
	@Temporal(TemporalType.TIMESTAMP)
	Date updatedAt;

	/**
	 * 모임 신청 시작 시간
	 */
	@Temporal(TemporalType.TIMESTAMP)
	Date registOpenAt;

	/**
	 * 모임 신청 종료 시간
	 */
	@Temporal(TemporalType.TIMESTAMP)
	Date registCloseAt;

	/**
	 * 모임 상태
	 */
	@Enumerated(EnumType.STRING)
	MeetingStatus meetingStatus = MeetingStatus.DRAFT;

	/**
	 * 모임 관리자, 모임 최초 만든 사용자는 자동으로 들어가고, 추가로 관리자 추가할 수 있음. 그래서 List.
	 */
	@ManyToMany
    Set<User> admins = new HashSet<>();

	/**
	 * 모임 주제
	 */
	@ManyToMany
	Set<Topic> topics = new HashSet<>();

	/**
	 * 모임 참여자
	 */
	@OneToMany
	Set<AttendMeeting> attendees = new HashSet<>();

	public static Meeting of(String title, String contents) {
		Meeting meeting = new Meeting();
		meeting.title = title;
		meeting.contents = contents;
		meeting.meetingStatus = MeetingStatus.DRAFT;

		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		meeting.createdAt = Date.from(utc.toInstant());
		meeting.updatedAt = Date.from(utc.toInstant());

		return meeting;
	}

    public void update() {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        this.updatedAt = Date.from(utc.toInstant());
    }

	public void addAdmin(User user) {
		this.admins.add(user);
	}

	public void attendBy(User user) {
		AttendMeeting.AttendStatus attendStatus = AttendMeeting.AttendStatus.ENROLLED;
		if (isAutoConfirm()) {
			attendStatus = AttendMeeting.AttendStatus.CONFIRM;
		}

		attendees.add(AttendMeeting.of(user, this, attendStatus));
	}

	public void attendCancel(User user) {
		attendees.stream().filter(attendMeeting -> attendMeeting.getUser().equals(user)).findFirst().ifPresent(attendMeeting -> attendees.remove(attendMeeting));
	}

	public boolean isAttendBy(User user) {
		return attendees.stream().filter(attendMeeting -> attendMeeting.getUser().equals(user)).findAny().isPresent();
	}

	public boolean isAdmin(User user) {
		return admins.stream().filter(admin -> admin.equals(user)).findAny().isPresent();
	}

	public boolean onRegistTime() {
		Date now = Date.from(ZonedDateTime.now(ZoneOffset.UTC).toInstant());
		Optional<Date> registOpenAt = Optional.ofNullable(getRegistOpenAt());
		Optional<Date> registCloseAt = Optional.ofNullable(getRegistCloseAt());

		if (registOpenAt.isPresent() && now.before(registOpenAt.get())) {
			return false;
		}

		if (registCloseAt.isPresent() && now.after(registCloseAt.get())) {
			return false;
		}

		return true;
	}

	public enum MeetingStatus {
		DRAFT, PUBLISHED, REGIST_CLOSED, DONE
	}

	public enum MeetingOnOffType {
	    ONLINE, OFFLINE, BOTH
    }

    public enum OnlineType {
	    SLACK, HANGOUT, DISCORD
    }

}
