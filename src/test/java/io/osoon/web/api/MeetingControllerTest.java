package io.osoon.web.api;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.domain.Topic;
import io.osoon.data.domain.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    public void postCreateMeetingTest() throws Exception {
        // Given
        User user = userRepository.save(User.of("whiteship@email.com", "keesun"));
        assertThat(user).isNotNull();
        this.login(user);

        Meeting meetingParam = new Meeting();
        meetingParam.setTitle("test meeting");
        meetingParam.setContents("blah blah");
        meetingParam.setMeetingOnOffType(Meeting.MeetingOnOffType.OFFLINE);

        MeetingLocation meetingLocation = MeetingLocation.of("Toz", null);
        meetingLocation.setAddr("서울시 마포구 월드컵북로2길 65 5층");
        meetingParam.setLocation(meetingLocation);

        MockHttpServletRequestBuilder createMeetingRequest = post("/api/meeting/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(meetingParam));

        // When & Then
        mvc.perform(createMeetingRequest)
                .andDo(print())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.admins", hasSize(1)))
                .andExpect(jsonPath("$.location.name", is(meetingLocation.getName())))
        ;
    }

    @Test
    public void getMeeting() throws Exception {
        // Given
        User user = userRepository.save(User.of("whiteship@email.com", "keesun"));
        assertThat(user).isNotNull();
        this.login(user);

        Meeting meetingParam = new Meeting();
        meetingParam.setTitle("test meeting");
        meetingParam.setContents("blah blah");
        meetingParam.setMeetingOnOffType(Meeting.MeetingOnOffType.OFFLINE);

        MeetingLocation meetingLocation = MeetingLocation.of("Toz", null);
        meetingLocation.setAddr("서울시 마포구 월드컵북로2길 65 5층");
        meetingParam.setLocation(meetingLocation);

        Meeting newMeeting = meetingService.create(user, meetingParam);

        // When & Then
        mvc.perform(get("/api/meeting/" + newMeeting.getId()))
                .andDo(print())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.admins", hasSize(1)))
                .andExpect(jsonPath("$.location.name", is(meetingLocation.getName())))
        ;
    }

}