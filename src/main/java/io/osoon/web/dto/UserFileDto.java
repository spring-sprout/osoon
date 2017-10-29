package io.osoon.web.dto;

import io.osoon.data.domain.UserFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author whiteship
 */
@Getter @Setter
@NoArgsConstructor
public class UserFileDto {

    private String name;

    private String path;

    public UserFileDto(UserFile userFile) {
        this.name = userFile.getName();
        this.path = userFile.getPath();
    }

}
