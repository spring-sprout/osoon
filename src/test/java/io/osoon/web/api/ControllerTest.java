package io.osoon.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.osoon.config.properties.OSoonProperties;
import io.osoon.data.domain.User;
import io.osoon.data.repository.*;
import io.osoon.security.OSoonUserDetails;
import io.osoon.service.meeting.MeetingService;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author whiteship
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "docs/api/")
@Ignore
public class ControllerTest {

    @Autowired protected ObjectMapper objectMapper;

    @Autowired protected UserRepository userRepository;
    @Autowired protected MeetingRepository meetingRepository;
    @Autowired protected UserFileRepository userFileRepository;
    @Autowired protected OSoonProperties oSoonProperties;
    @Autowired protected MeetingLocationRepository meetingLocationRepository;
    @Autowired protected TopicRepository topicRepository;
    @Autowired protected MeetingService meetingService;

    @Autowired protected MockMvc mvc;

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
        topicRepository.deleteAll();
        meetingLocationRepository.deleteAll();
    }

    protected void login(User user) {
        OSoonUserDetails userDetails = new OSoonUserDetails(user);
        RememberMeAuthenticationToken rememberMeToken = new RememberMeAuthenticationToken("osoon-remember-me", userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(rememberMeToken);
    }
}
