package io.osoon.web.dto;

import io.osoon.domain.Meeting;
import io.osoon.domain.Topic;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author whiteship
 */
@Getter @Setter
public class MeetingCreateDto {

    private UserDto user;

    private List<MeetingLocationDto> places;

    private List<Topic> topics;

    private Meeting.MeetingOnOffType[] meetingOnOffTypes;

    private Meeting.OnlineType[] onlineTypes;

}
