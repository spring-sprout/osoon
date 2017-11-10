package io.osoon.web.dto;

import java.time.LocalDateTime;

import io.osoon.data.domain.AttendMeeting;
import lombok.Getter;
import lombok.Setter;

/**
 * @author whiteship
 */
@Getter @Setter
public class AttendMeetingDto {

    private long id;

    private long userid;

    private String userNickname;

    private String userImageUrl;

    private LocalDateTime at;

    private AttendMeeting.AttendStatus status;

}
