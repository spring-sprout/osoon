package io.osoon.web.api;

import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.domain.Topic;
import io.osoon.data.domain.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author whiteship
 */
public class MeetingControllerTest extends ControllerTest {

    @Test
    public void getCreateMeetingTest() throws Exception {
        // Given
        User user = userRepository.save(User.of("whiteship@email.com", "keesun"));
        assertThat(user).isNotNull();
        this.login(user);

        MeetingLocation toz = meetingLocationRepository.save(MeetingLocation.of("Toz", user));
        assertThat(toz).isNotNull();

        MeetingLocation spaceNeedle = meetingLocationRepository.save(MeetingLocation.of("SpaceNeedle", user));
        assertThat(spaceNeedle).isNotNull();

        Topic java = topicRepository.save(Topic.of("java"));
        assertThat(java).isNotNull();

        // When & Then
        mvc.perform(get("/api/meeting/create"))
                .andDo(print())
                .andExpect(jsonPath("$.user.name", is(user.getName())))
                .andExpect(jsonPath("$.places", hasSize(2)))
                .andExpect(jsonPath("$.topics", hasSize(1)));
    }

}