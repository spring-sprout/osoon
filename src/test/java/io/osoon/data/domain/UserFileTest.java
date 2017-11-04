package io.osoon.data.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author whiteship
 */
public class UserFileTest {

    @Test
    public void thumbNailPath() {
        UserFile userFile = new UserFile();
        userFile.setPath("33/aaaa.jpg");
        String thumbnailPath = userFile.getThumbnailPath();
        assertThat(thumbnailPath).isEqualToIgnoringCase("33/thumb_aaaa.jpg");
    }

}