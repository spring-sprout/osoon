package io.osoon.web.api;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import io.osoon.data.domain.*;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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
                .andDo(document("prepare-to-create-meeting"))
                .andExpect(jsonPath("$.user.name", is(user.getName())))
                .andExpect(jsonPath("$.places", hasSize(2)))
                .andExpect(jsonPath("$.topics", hasSize(1)))
                .andExpect(jsonPath("$.meetingOnOffTypes", hasSize(3)))
                .andExpect(jsonPath("$.onlineTypes", hasSize(3)))
        ;
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
        meetingParam.setMeetingStatus(Meeting.MeetingStatus.PUBLISHED);

        MeetingLocation meetingLocation = MeetingLocation.of("Toz", null);
        meetingLocation.setAddr("서울시 마포구 월드컵북로2길 65 5층");
        meetingParam.setLocation(meetingLocation);

        ZonedDateTime startAt = ZonedDateTime.now(ZoneOffset.UTC).plusDays(10);
        meetingParam.setMeetStartAt(Date.from(startAt.toInstant()));
        ZonedDateTime endAt = ZonedDateTime.now(ZoneOffset.UTC).plusDays(13);
        meetingParam.setMeetStartAt(Date.from(endAt.toInstant()));

        MockHttpServletRequestBuilder createMeetingRequest = post("/api/meeting/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(meetingParam));

        // When & Then
        mvc.perform(createMeetingRequest)
                .andDo(print())
                .andDo(document("create-meeting"))
                .andExpect(jsonPath("$.content.id").isNotEmpty())
                .andExpect(jsonPath("$.content.admins", hasSize(1)))
                .andExpect(jsonPath("$.content.location.name", is(meetingLocation.getName())))
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

        newMeeting.attendBy(user);
        meetingRepository.save(newMeeting);

        // When & Then
        mvc.perform(get("/api/meeting/" + newMeeting.getId()))
                .andDo(print())
                .andDo(document("view-meeting"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.admins", hasSize(1)))
                .andExpect(jsonPath("$.location.name", is(meetingLocation.getName())))
        ;
    }

}