package io.osoon.web.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.osoon.web.dto.MeetingViewDto;

/**
 * @author whiteship
 */
public class MeetingView extends ResourceSupport {

    private MeetingViewDto content;

    @JsonCreator
    public MeetingView(@JsonProperty("content") MeetingViewDto meetingViewDto) {
        this.content = meetingViewDto;
    }

    public MeetingViewDto getContent() {
        return content;
    }
}
