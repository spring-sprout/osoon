package io.osoon.web.dto;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.Topic;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author whiteship
 */
@Getter @Setter
public class AfterCreateMeetingDto {

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
    String coverImage;

    /**
     * 모임 자동 승인 여부(자동 승인시 관리자 승인 필요 없음)
     */
    boolean isAutoConfirm;

    /**
     * 모임 장소 (오프라인 모임 일 경우에만)
     */
    MeetingLocationDto location;

    /**
     * 온라인 모임 형태 (온라인 모임 인 경우에만)
     */
    Meeting.OnlineType onlineType;

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
    Meeting.MeetingStatus meetingStatus;

    /**
     * 모임 관리자, 모임 최초 만든 사용자는 자동으로 들어가고, 추가로 관리자 추가할 수 있음. 그래서 List.
     */
    Set<UserDto> admins;

    /**
     * 모임 주제
     */
    List<Topic> topics;

}
