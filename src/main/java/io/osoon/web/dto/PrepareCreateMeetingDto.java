package io.osoon.web.dto;

import io.osoon.data.domain.Topic;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author whiteship
 */
@Getter @Setter
public class PrepareCreateMeetingDto {

    private UserDto user;

    private List<MeetingLocationDto> places;

    private List<Topic> topics;

}
