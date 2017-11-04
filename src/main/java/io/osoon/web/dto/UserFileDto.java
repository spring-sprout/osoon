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

    /**
     * /{meetingId}/UUID.extension
     */
    private String path;

    /**
     * /{meetingId}/thumb_UUID.extension
     */
    private String thumbnailPath;

    public UserFileDto(UserFile userFile) {
        this.name = userFile.getName();
        this.path = userFile.getPath();
        this.thumbnailPath = userFile.getThumbnailPath();
    }

}
