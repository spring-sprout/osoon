package io.osoon.web.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author whiteship
 */
@Getter @Setter
public class MeetingLocationDto {

    Long id;

    String addr;

    String name;

    double latitude;

    double longitude;

}
