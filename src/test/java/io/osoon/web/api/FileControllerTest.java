package io.osoon.web.api;

import io.osoon.config.properties.OSoonProperties;
import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.User;
import io.osoon.data.repository.MeetingRepository;
import io.osoon.data.repository.UserFileRepository;
import io.osoon.data.repository.UserRepository;
import io.osoon.security.OSoonUserDetails;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired private UserRepository userRepository;
    @Autowired private MeetingRepository meetingRepository;
    @Autowired private UserFileRepository userFileRepository;
    @Autowired private OSoonProperties oSoonProperties;

    @Autowired private MockMvc mvc;

    @Before
    public void before() {
        cleanUp();
    }

    @After
    public void after() {
        cleanUp();
    }

    private void cleanUp() {
        Path path = Paths.get(oSoonProperties.getUploadFileRootPath());
        if (path.toFile().exists()) {
            boolean result = FileUtils.deleteQuietly(path.toFile());
            assertThat(result).isTrue();
        }

        userRepository.deleteAll();
        meetingRepository.deleteAll();
        userFileRepository.deleteAll();
    }

    @Test
    public void uploadCoverImage() throws Exception {
        // Given
        User user = userRepository.save(User.of("whiteship@email.com", "keesun"));
        Meeting meeting = meetingRepository.save(Meeting.of("test", "here we go"));
        assertThat(user).isNotNull();
        assertThat(meeting).isNotNull();
        assertThat(meeting.getTitleImage()).isNullOrEmpty();

        OSoonUserDetails userDetails = new OSoonUserDetails(user);
        RememberMeAuthenticationToken rememberMeToken = new RememberMeAuthenticationToken("osoon-remember-me", userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(rememberMeToken);

        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.jpg",
                "image/jpeg", "test image content".getBytes());

        String url = "/api/meeting/" + meeting.getId() + "/cover";

        // When & Then
        this.mvc.perform(fileUpload(url).file(multipartFile))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(Matchers.is("test.jpg")))
                .andExpect(jsonPath("$.path").isNotEmpty());

        Optional<Meeting> updatedMeeting = meetingRepository.findById(meeting.getId(), 0);
        if (!updatedMeeting.isPresent()) {
            fail("meeting doesn't exist");
        }

        assertThat(updatedMeeting.get().getTitleImage()).isNotEmpty();
    }

}