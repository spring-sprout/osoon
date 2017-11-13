package io.osoon.web.api;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import io.osoon.data.domain.Meeting;
import io.osoon.data.domain.MeetingLocation;
import io.osoon.data.domain.Topic;
import io.osoon.data.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    public void updateMeeting() throws Exception {
		// Given
		User user = userRepository.save(User.of("whiteship@email.com", "keesun"));
		assertThat(user).isNotNull();
		this.login(user);

		Meeting meetingParam = Meeting.of("test title", "test contents blah");
		Meeting newMeeting = meetingService.create(user, meetingParam);

		newMeeting.setTitle("update title");
		newMeeting.setContents("updated contents");

		MeetingLocation updateLocation = MeetingLocation.of("Green Factory", null);
		updateLocation.setAddr("서울시 마포구 월드컵북로2길 65 5층");
		newMeeting.setLocation(updateLocation);

		// @TODO 업데이트 화면이 어떻게 구성되느냐에 따라 관리자 수정 방법 변경 될 듯.. 모임 수정 화면에서 수정하지 말고 관리자는 한 뎁스 더 들어가서 수정하면  좋겠네요.
		newMeeting.setAdmins(new HashSet<>());
		User admin = new User();
		admin.setId(user.getId());
		newMeeting.addAdmin(admin);

		MockHttpServletRequestBuilder updateRequest = put("/api/meeting/" + newMeeting.getId() + "/update")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content(objectMapper.writeValueAsString(newMeeting));

		// When & Then
		mvc.perform(updateRequest)
			.andDo(print())
			.andDo(document("update-meeting"))
			.andExpect(jsonPath("$.content.id").isNotEmpty())
			.andExpect(jsonPath("$.content.admins", hasSize(1)))
			.andExpect(jsonPath("$.content.location.name", is(updateLocation.getName())))
		;
	}

	@Test
	public void attendMeeting() throws Exception {
		// Given
		User user = userRepository.save(User.of("whiteship@email.com", "keesun"));
		assertThat(user).isNotNull();

		User attender1 = userRepository.save(User.of("attender.1@email.com", "attender.1"));
		assertThat(attender1).isNotNull();
		this.login(attender1);

		Meeting meetingParam = Meeting.of("test title", "test contents blah");
		meetingParam.setMaxAttendees(10);
		meetingParam.setMeetingOnOffType(Meeting.MeetingOnOffType.OFFLINE);
		meetingParam.setMeetingStatus(Meeting.MeetingStatus.PUBLISHED);
		Meeting newMeeting = meetingService.create(user, meetingParam);

		MockHttpServletRequestBuilder createMeetingRequest = post("/api/meeting/" + newMeeting.getId() + "/attend")
			.contentType(MediaType.APPLICATION_JSON_UTF8);

		// When & Then
		mvc.perform(createMeetingRequest)
			.andDo(print())
			.andDo(document("attend-meeting"))
			.andExpect(status().isOk())
		;
	}

	@Test
	public void leaveMeeting() throws Exception {
		// Given
		User user = userRepository.save(User.of("whiteship@email.com", "keesun"));
		assertThat(user).isNotNull();

		User attender1 = userRepository.save(User.of("attender.1@email.com", "attender.1"));
		assertThat(attender1).isNotNull();
		this.login(attender1);

		Meeting meetingParam = Meeting.of("test title", "test contents blah");
		meetingParam.setMaxAttendees(10);
		meetingParam.setMeetingOnOffType(Meeting.MeetingOnOffType.OFFLINE);
		meetingParam.setMeetingStatus(Meeting.MeetingStatus.PUBLISHED);
		Meeting newMeeting = meetingService.create(user, meetingParam);

		meetingAttendService.attend(newMeeting, attender1);

		MockHttpServletRequestBuilder createMeetingRequest = post("/api/meeting/" + newMeeting.getId() + "/leave")
			.contentType(MediaType.APPLICATION_JSON_UTF8);

		// When & Then
		mvc.perform(createMeetingRequest)
			.andDo(print())
			.andDo(document("leave-meeting"))
			.andExpect(status().isOk())
		;
	}
}