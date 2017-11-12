package io.osoon.web.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author whiteship
 */
@Getter @Setter
public class UserDto {

    Long id;
    private String name;
    private String nickname;
    private String imageUrl;

}
