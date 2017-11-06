package io.osoon.web.api;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author whiteship
 */
public class FileControllerTest extends ControllerTest {

    /**
     * 미팅 커버 이미지 업로드 테스트
     * API: POST /api/meeting/{id}/cover
     */
    @Test
    public void uploadCoverImage() throws Exception {
        // Given
        User user = userRepository.save(User.of("whiteship@email.com", "keesun"));
        Meeting meeting = meetingRepository.save(Meeting.of("test", "here we go"));
        assertThat(user).isNotNull();
        assertThat(meeting).isNotNull();
        assertThat(meeting.getCoverImage()).isNullOrEmpty();

        this.login(user);

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.jpg",
                "image/jpeg", "test image content".getBytes());

        String url = "/api/meeting/" + meeting.getId() + "/cover";

        // When & Then
        this.mvc.perform(fileUpload(url).file(multipartFile))
                .andDo(print())
                .andDo(document("update-cover-image"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(Matchers.is("test.jpg")))
                .andExpect(jsonPath("$.path").isNotEmpty())
                .andExpect(jsonPath("$.thumbnailPath").isNotEmpty());

        Optional<Meeting> updatedMeeting = meetingRepository.findById(meeting.getId(), 0);
        if (!updatedMeeting.isPresent()) {
            fail("meeting doesn't exist");
        }

        assertThat(updatedMeeting.get().getCoverImage()).isNotEmpty();
    }

}